package com.kotlinproject.wooooo.audink.activity

import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.adapter.PlayBarAdapter
import com.kotlinproject.wooooo.audink.fragment.BookInfoArticleListFragment
import com.kotlinproject.wooooo.audink.fragment.BookInfoSummaryFragment
import com.kotlinproject.wooooo.audink.model.ArticleInfo
import com.kotlinproject.wooooo.audink.model.BookInfo
import com.kotlinproject.wooooo.audink.service.BackgroundAudioService
import com.kotlinproject.wooooo.audink.utils.*
import kotlinx.android.synthetic.main.activity_book_info.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.io.File

class BookInfoActivity : BaseActivity(), BookInfoArticleListFragment.OnArticleListInteractionListener {
    private val TAG = "BookInfoActivity"

    private val bookId by lazy { intent.getIntExtra(BOOK_ID, -1) }
    private lateinit var bookInfo: BookInfo

    private lateinit var audioBinder: BackgroundAudioService.BackgroundAudioBinder
    private lateinit var playBarAdapter: PlayBarAdapter

    override fun playArticle(article: ArticleInfo) {
        AppCache.playingArticle = article
        AppCache.playingBook = bookInfo

        Log.i(TAG, "playArticle: $article")

        val audioPath = getAudinkArticleFiles(article.articleId).audioPath
        val fileExists = File(audioPath).exists()
        if (fileExists) {
            audioBinder.loadAudio(audioPath)
            audioBinder.isPlaying = true
        } else {
            audioBinder.isPlaying = false
        }

        playBarAdapter.refreshBarState()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_info)
    }

    override fun onBgmServiceBind(service: IBinder?) {
        super.onBgmServiceBind(service)
        audioBinder = service as BackgroundAudioService.BackgroundAudioBinder
        playBarAdapter = PlayBarAdapter(this, audioBinder)
        playBarAdapter.refreshBarState()

        AppCache.asyncDetailBookInfo(this, bookId) { book, isSuccess ->
            if (isSuccess) {
                initView(book)
            } else {
                Toast.makeText(this, "获取书籍信息失败", Toast.LENGTH_SHORT).show()
                initView(AppCache.localDetailBookInfo(bookId))
            }
        }
    }

    private fun initView(bookInfo: BookInfo) {
        this.bookInfo = bookInfo
        toolbar.title = "书籍详情"
        txt_book_info_name.text = bookInfo.bookName
        txt_book_info_author.text = bookInfo.author
        txt_book_info_uploader.text = bookInfo.uploader
        Glide.with(this).load(bookInfo.imagePath).into(image_book_info_cover)

        viewpager_book_info.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            private val titles = listOf("详情", "章节")
            private val fragments = listOf(
                BookInfoSummaryFragment.newInstance(bookInfo.summary),
                BookInfoArticleListFragment.newInstance(bookId)
            )

            override fun getItem(position: Int): Fragment = fragments[position]
            override fun getCount(): Int = fragments.count()
            override fun getPageTitle(position: Int): CharSequence = titles[position]
        }

        tab_book_info.setupWithViewPager(viewpager_book_info)

        fun v(bool: Boolean): Int = if (bool) View.VISIBLE else View.GONE

        // 按钮可见性设置
        val b = bookId in AppCache.localUserPreferBookList().map { it.bookId }
        txt_book_info_prefer.visibility = v(!b)
        txt_book_info_preferred.visibility = v(b)

        // 收藏
        txt_book_info_prefer.setOnClickListener {
            AppCache.asyncPreferBook(this, bookId) {
                if (it) {
                    txt_book_info_prefer.visibility = v(!it)
                    txt_book_info_preferred.visibility = v(it)
                } else {
                    Toast.makeText(this, "收藏失败", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 取消收藏
        txt_book_info_preferred.setOnClickListener {
            AppCache.asyncCancelPreferBook(this, bookId) {
                if (it) {
                    txt_book_info_preferred.visibility = v(!it)
                    txt_book_info_prefer.visibility = v(it)
                } else {
                    Toast.makeText(this, "取消收藏失败", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 全部下载
        txt_book_info_download_all.setOnClickListener {
            (bookInfo.chapter?: emptyList())
                .filter {
                    !File(getAudinkArticleFiles(it.articleId).audioPath).exists() &&
                    !DownloadUtil.isArticleDownloading(it.articleId)
                }.forEach { DownloadUtil.download(it) }
        }
    }

    companion object {
        const val BOOK_ID = "book_id"
    }
}
