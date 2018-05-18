package com.kotlinproject.wooooo.audink.utils

import android.app.Application
import android.util.SparseArray
import com.kotlinproject.wooooo.audink.model.ArticleInfo
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import kotlin.concurrent.thread

object DownloadUtil {
    private lateinit var application: Application
    private val appContext get() = application.applicationContext

    private val okHttpClient = OkHttpClient()

    private val downloadArticles = mutableMapOf<Int, ArticleInfo>()
    private val downloadProgress = mutableMapOf<Int, Int>()

    /** 初始配置 */
    fun start(application: Application) {
        this.application = application
    }

    /** 正在下载的文章列表 */
    fun getDownloadingList(): List<ArticleInfo> {
        return downloadArticles.map { it.value }
    }

    /** 文章的下载进度 */
    fun getDownloadingProgress(articleId: Int): Int {
        return downloadProgress.getOrDefault(articleId, -1)
    }

    /** 请求下载文章 */
    fun download(articleInfo: ArticleInfo) {
        thread {
            downloadArticles[articleInfo.articleId] = articleInfo
            val saveFile = File(getAudinkArticleFiles(articleInfo.articleId).audioPath + ".temp")

            val request = try {
                Request.Builder().url(articleInfo.audioPath).build()
            } catch (e: Exception) {
                e.printStackTrace()
                downloadArticles.remove(articleInfo.articleId)
                return@thread
            }
            okHttpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) downloadArticles.remove(articleInfo.articleId)
                else response.body()?.byteStream()?.use { bs ->
                    // 创建文件先
                    saveFile.parentFile.mkdirs()
                    if (!saveFile.exists()) saveFile.createNewFile()

                    // 开始下载
                    val totalLen = response.body()?.contentLength() ?: 0
                    var tempLen = 0
                    var sumLen = 0
                    val bytes = ByteArray(1024)
                    saveFile.outputStream().use { fos ->
                        while (bs.let { tempLen = it.read(bytes); tempLen != -1 }){
                            fos.write(bytes, 0, tempLen)
                            sumLen += tempLen
                            downloadProgress[articleInfo.articleId] = sumLen * 100 / totalLen.toInt()
                        }
                        fos.flush()
                    }
                }
            }

            // 下载完成
            saveFile.renameTo(File(getAudinkArticleFiles(articleInfo.articleId).audioPath))
            downloadArticles.remove(articleInfo.articleId)
        }
    }

    /** 是否在下载列表中 */
    fun isArticleDownloading(articleId: Int): Boolean {
        return downloadArticles.containsKey(articleId)
    }
}