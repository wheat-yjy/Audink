package com.kotlinproject.wooooo.audink.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.model.ArticleInfo
import com.kotlinproject.wooooo.audink.utils.AppCache
import com.kotlinproject.wooooo.audink.utils.DownloadUtil
import com.kotlinproject.wooooo.audink.utils.getAudinkArticleFiles
import java.io.File

class ArticleListAdapter(
    private val activity: Activity,
    private val itemList: List<ArticleInfo>,
    private val onPlayListener: (article: ArticleInfo) -> Unit
) : RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    // 被选中的项目，用于切换时更改选中标记
    private var selectedItem: View? = null

    // 下载列表
    private val downloadingList = itemList
        .filter { it.articleId in DownloadUtil.getDownloadingList().map { it.articleId } }
        .toMutableList()

    // 循环标志
    private var loopFlag = false

    // 下载状态查询循环
    private val downloadingStatusLoop = Runnable {
        while (loopFlag) {
            Log.i("ArticleListAdapter", "Thread.sleep(1000)")
            Thread.sleep(1000)
            activity.runOnUiThread {
                // 下载完成的部分移出下载队列
                val removeList = downloadingList.filter { !DownloadUtil.isArticleDownloading(it.articleId) }
                downloadingList.removeAll(removeList)
                // 新加入下载的部分添加进下载队列
                downloadingList.addAll(DownloadUtil.getDownloadingList().filter { it.articleId in itemList.map { it.articleId } })
                for (dlItem in downloadingList) {
                    notifyItemChanged(itemList.indexOfFirst { it.articleId == dlItem.articleId })
                }
                for (dlItem in removeList) {
                    notifyItemChanged(itemList.indexOfFirst { it.articleId == dlItem.articleId })
                }
            }
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.layout_book_article_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = itemList[position]
        holder?.apply {
            txtIndex.text = (position + 1).toString()
            txtTitle.text = item.articleName
            imgDownload.setOnClickListener {
                imgDownload.visibility = View.GONE
                txtDownloading.visibility = View.VISIBLE
                DownloadUtil.download(item)
                downloadingList += item
            }
            imgPlay.setOnClickListener {
                replaceSelectItem(viewPlayMark)
                onPlayListener(item)
            }
            // 按钮可见性
            val canPlay = File(getAudinkArticleFiles(item.articleId).audioPath).exists()
            val isDownloading = item.articleId in downloadingList.map { it.articleId }
            when {
                isDownloading -> {
                    txtDownloading.visibility = View.VISIBLE
                    imgDownload.visibility = View.GONE
                    imgPlay.visibility = View.GONE
                    // 设置下载进度
                    txtDownloading.text = "已下载 ${DownloadUtil.getDownloadingProgress(item.articleId)}%..."
                }
                canPlay       -> {
                    imgPlay.visibility = View.VISIBLE
                    imgDownload.visibility = View.GONE
                    txtDownloading.visibility = View.GONE
                }
                else          -> {
                    imgDownload.visibility = View.VISIBLE
                    txtDownloading.visibility = View.GONE
                    imgPlay.visibility = View.GONE
                }
            }
            // 标志可见性
            if (AppCache.playingArticle?.let { it.articleId == item.articleId } == true) replaceSelectItem(viewPlayMark)
        }
    }

    private fun replaceSelectItem(newItem: View?) {
        selectedItem?.let { it.visibility = View.INVISIBLE }
        newItem?.visibility = View.VISIBLE
        selectedItem = newItem
    }

    fun startLoop() {
        if (loopFlag) return
        loopFlag = true
        Thread(downloadingStatusLoop).start()
    }

    fun stopLoop() {
        loopFlag = false
        Log.i("ArticleListAdapter", "stopLoop()")
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtIndex: TextView = view.findViewById(R.id.txt_article_item_index)
        val txtTitle: TextView = view.findViewById(R.id.txt_article_item_title)
        val imgDownload: ImageView = view.findViewById(R.id.image_article_item_download)
        val imgPlay: ImageView = view.findViewById(R.id.image_article_item_play)
        val txtDownloading: TextView = view.findViewById(R.id.txt_article_item_downloading)
        val viewPlayMark: View = view.findViewById(R.id.view_article_item_playing_mark)
    }
}