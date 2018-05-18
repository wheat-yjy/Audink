package com.kotlinproject.wooooo.audink.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.kotlinproject.wooooo.audink.model.ArticleInfo

class DownloadService : Service() {
    private val binder = DownloadBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    inner class DownloadBinder : Binder() {
        /** 查询 article_id 是否在下载列队中 */
        fun checkArticleDownloading(articleId: Int): Boolean {
            return false
        }

        /** 获取全部下载列表 */
        fun getDownloadingList(): List<ArticleInfo> {
            return emptyList()
        }

        /** 获取进度 */
        fun getProcess(articleId: Int): Int {
            return 100
        }

        /** 添加下载 */
        fun newDownload(articleInfo: ArticleInfo) {

        }
    }
}
