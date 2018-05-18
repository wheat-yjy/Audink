package com.kotlinproject.wooooo.audink.activity

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.IBinder
import android.view.Menu
import android.view.MotionEvent
import android.widget.Toast
import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.model.ArticleInfo
import com.kotlinproject.wooooo.audink.utils.openArticleFromJson
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ReadActivity : BaseActivity() {

    private var toolbarShow = true

    private var articleInfo: ArticleInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
    }

    override fun onBgmServiceBind(service: IBinder?) {
        super.onBgmServiceBind(service)
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_read,menu)
        val viewAbstract = menu?.findItem(R.id.menu_item_abstract)
        viewAbstract?.setOnMenuItemClickListener {
            AlertDialog.Builder(this)
                .setTitle("文本摘要")
                .setMessage(articleInfo?.articleAbstract ?: "暂无内容")
                .create()
                .show()
            true
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun initView() {

        val articleId = intent.getIntExtra(ARTICLE_ID, 0)

        articleInfo = openArticleFromJson(articleId)

        articleInfo?.articleName?.let { toolbar.title = it }

        articleInfo?.content?.let { view_page.loadText(it) }

        // 翻页和标题栏显示或隐藏
        view_page.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                when {
                    motionEvent.x < view.width / 3     -> {
                        view_page.prePage()
                        setToolbarVisible(false)
                        Toast.makeText(this, "${view_page.currentPage} / ${view_page.totalPage}", Toast.LENGTH_SHORT).show()
                    }
                    motionEvent.x > view.width * 2 / 3 -> {
                        view_page.nextPage()
                        setToolbarVisible(false)
                        Toast.makeText(this, "${view_page.currentPage} / ${view_page.totalPage}", Toast.LENGTH_SHORT).show()
                    }
                    else                               -> {
                        setToolbarVisible(!toolbarShow)
                    }
                }
            }

            true
        }
    }

    private fun setToolbarVisible(isVisible: Boolean) {
        appbar.animate()
            .translationY(if (!isVisible) -appbar.height.toFloat() else 0f)
            .setDuration(200L)
        toolbarShow = isVisible
    }

    companion object {
        const val ARTICLE_ID = "article_id"
    }
}
