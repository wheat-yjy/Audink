package com.kotlinproject.wooooo.audink.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.kotlinproject.wooooo.audink.database.AudinkDbHelper
import com.kotlinproject.wooooo.audink.model.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlin.concurrent.thread

object AppCache {
    private val TAG = "AppCache"

    /** 静态方法对外 */
    private const val UNLOGIN_UID = -1
//    private const val HOME_PAGE = "http://120.24.70.191:8080/audinkapp"
    private const val HOME_PAGE = "http://192.168.0.125:8080/audink"

    private lateinit var dbHelper: AudinkDbHelper
    private val wb get() = dbHelper.writableDatabase
    private val okHttpClient = OkHttpClient()

    /** 用户是否登录 */
    val isUserLogin get() = uid != UNLOGIN_UID

    /** 用户uid */
    var uid = UNLOGIN_UID
        private set

    /** 用户名 */
    var username = "Audink User"
        private set

    /** 正在播放的文章 */
    var playingArticle: ArticleInfo? = null

    /** 正在播放的书籍 */
    var playingBook: BookInfo? = null

    /** 初始配置 */
    fun start(context: Context) {
        // 数据库
        dbHelper = AudinkDbHelper(context, "AudinkStore.db", null, 1)
    }

    /** 用户收藏书列表 */
    fun localUserPreferBookList(): List<BookInfo> {
        // 从数据库中获取
        val cursor = wb.mQuery(
            AudinkDbHelper.USER_PREFER,
            arrayOf(AudinkDbHelper.BOOK_ID),
            "${AudinkDbHelper.UID} = ?",
            arrayOf(uid.toString())
        )

        val result = mutableListOf<BookInfo>()

        while (cursor.moveToNext()) {
            openBookFromJson(cursor[AudinkDbHelper.BOOK_ID])?.let { result.add(it) }
        }

        return result
    }

    /** 联网用户收藏书列表 */
    fun asyncUserPreferBookList(activity: Activity, block: (list: List<BookInfo>, isSuccess: Boolean) -> Unit) {
        // 非登录用户不做联网操作
        if (!isUserLogin) return

        thread {
            post<JsonResponse<ResponseBooks>>(
                "$HOME_PAGE/app/user/collect/all",
                "userId" to uid.toString()
            ) {
                if (it?.status == 200) {
                    // 用户的喜欢记录更新
                    wb.delete(AudinkDbHelper.USER_PREFER, "${AudinkDbHelper.UID} = ?", arrayOf(uid.toString()))
                    for (book in it.data.books) {
                        wb.insert(AudinkDbHelper.USER_PREFER, null, ContentValues().apply {
                            put(AudinkDbHelper.UID, uid)
                            put(AudinkDbHelper.BOOK_ID, book.bookId)
                        })
                    }
                    // 返回
                    activity.runOnUiThread { block(it.data.books, true) }
                } else {
                    activity.runOnUiThread { block(emptyList(), false) }
                }
            }
        }
    }

    /** 某一本书的详细信息 */
    fun localDetailBookInfo(bookId: Int): BookInfo {
        return openBookFromJson(bookId) ?: BookInfo()
    }

    /** 联网获取某一本书的详细信息 */
    fun asyncDetailBookInfo(activity: Activity, bookId: Int, block: (book: BookInfo, isSuccess: Boolean) -> Unit) {
        thread {
            Log.i(TAG, "bookId: $bookId")
            post<JsonResponse<BookInfo>>(
                "$HOME_PAGE/app/all/book",
                "userId" to uid.toString(),
                "bookId" to bookId.toString()
            ) { body ->
                if (body?.status == 200) {
                    // 保存到本地
                    saveBookToJson(bookId, body.data)
                    // 保存文章信息到本地
                    body.data.chapter?.forEach {
                        saveArticleToJson(it.articleId, it)
                    }
                    // 返回
                    activity.runOnUiThread { block(body.data, true) }
                } else {
                    activity.runOnUiThread { block(BookInfo(), false) }
                }
            }
        }
    }

    /** 联网获取商城页面  */
    fun asyncBookStore(activity: Activity, block: (storeInfo: BookStoreInfo, isSuccess: Boolean) -> Unit) {
        thread {
            post<JsonResponse<BookStoreInfo>>(
                "$HOME_PAGE/app/all/homepage"
            ) {
                if (it?.status == 200) {
                    activity.runOnUiThread { block(it.data, true) }
                } else {
                    activity.runOnUiThread { block(BookStoreInfo(), false) }
                }
            }
        }
    }

    /** 联网获取某分类更多书籍 */
    fun asyncRecommendMoreBook(activity: Activity, className: String, block: (list: List<BookInfo>, isSuccess: Boolean) -> Unit) {
        thread {
            post<JsonResponse<List<BookInfo>>>(
                "$HOME_PAGE/app/all/classify",
                "userId" to uid.toString(),
                "classify" to className
            ) {
                if (it?.status == 200) {
                    activity.runOnUiThread { block(it.data,true) }
                } else {
                    activity.runOnUiThread { block(emptyList(),false) }
                }
            }
        }
    }

    /** 联网获取搜索结果 */
    fun asyncSearch(activity: Activity, query: String?, block: (list: List<BookInfo>, isSuccess: Boolean) -> Unit) {
        thread {
            post<JsonResponse<List<BookInfo>>>(
                "$HOME_PAGE/app/all/search",
                "bookname" to query.toString()
            ) {
                if (it?.status == 200) {
                    activity.runOnUiThread { block(it.data,true) }
                } else {
                    activity.runOnUiThread { block(emptyList(),false) }
                }
            }
        }
    }

    /** 联网收藏 */
    fun asyncPreferBook(activity: Activity, bookId: Int, block: (isSuccess: Boolean) -> Unit) {
        if (!isUserLogin) {
            block(false)
            return
        }

        thread {
            post<JsonResponse<DataEmpty>>(
                "$HOME_PAGE/app/user/collect/add",
                "userId" to uid.toString(),
                "bookId" to bookId.toString()
            ) {
                if (it?.status == 200) {
                    wb.insert(AudinkDbHelper.USER_PREFER, null, ContentValues().apply {
                        put(AudinkDbHelper.UID, uid)
                        put(AudinkDbHelper.BOOK_ID, bookId)
                    })
                    activity.runOnUiThread { block(true) }
                } else {
                    activity.runOnUiThread { block(false) }
                }
            }
        }
    }

    /** 取消收藏 */
    fun asyncCancelPreferBook(activity: Activity, bookId: Int, block: (isSuccess: Boolean) -> Unit) {
        if (!isUserLogin) {
            block(false)
            return
        }

        thread {
            post<JsonResponse<DataEmpty>>(
                "$HOME_PAGE/app/user/collection/delete",
                "userId" to uid.toString(),
                "bookId" to bookId.toString()
            ) {
                if (it?.status == 200) {
                    wb.delete(
                        AudinkDbHelper.USER_PREFER,
                        "${AudinkDbHelper.UID} = ? and ${AudinkDbHelper.BOOK_ID} = ?",
                        arrayOf(uid.toString(), bookId.toString())
                    )
                    activity.runOnUiThread { block(true) }
                } else {
                    activity.runOnUiThread { block(false) }
                }
            }
        }
    }

    /** 登录 */
    fun asyncLogin(activity: Activity, username: String, password: String, block: (message: String, isSuccess: Boolean) -> Unit) {
        thread {
            post<JsonResponse<UserId>>(
                "$HOME_PAGE/app/all/login",
                "username" to username,
                "password" to password
            ) { body ->
                if (body?.status == 200) {
                    loginEvent(body.data.uid, username, password)
                    activity.runOnUiThread { block(body.message, true) }
                } else {
                    activity.runOnUiThread { block(body?.message?:"处理请求时发生错误", false) }
                }
            }
        }
    }

    /** 注册 */
    fun asyncRegister(activity: Activity, username: String, password: String, block: (message: String, isSuccess: Boolean) -> Unit) {
        thread {
            post<JsonResponse<UserId>>(
                "$HOME_PAGE/app/all/register",
                "username" to username,
                "password" to password
            ) { body ->
                if (body?.status == 200) {
                    // 注册成功
                    loginEvent(body.data.uid, username, password)
                    // activity回调事件
                    activity.runOnUiThread { block(body.message, true) }
                } else {
                    // 注册失败
                    activity.runOnUiThread { block(body?.message?:"处理请求时发生错误", false) }
                }
            }
        }
    }

    /** 自动登录 */
    fun autoLogin(activity: Activity, block: (message: String, isSuccess: Boolean) -> Unit) {
        val wd = dbHelper.writableDatabase
        val cursor = wd.mQuery(AudinkDbHelper.AUTO_LOGIN)
        if (cursor.moveToFirst()) {
            // 找到登录记录，自动登录
            asyncLogin(activity, cursor["username"], cursor["password"], block)
        } else {
            uid = -1
            username = "local"
            playingArticle = null
            playingBook = null
            block("使用本地帐号登录", false)
        }
    }

    private inline fun <reified T> post(url: String, vararg puts: Pair<String, String>, block: (body: T?) -> Unit) {
        Log.i(TAG, "post to $url")
        val formBuilder = FormBody.Builder()
        puts.forEach { formBuilder.add(it.first, it.second) }
        val form = formBuilder.build()
        val request = Request.Builder().url(url).post(form).build()
        Log.i(TAG, ": $request")
        okHttpClient.newCall(request).execute().use {
            try {
                val json = it.body()?.string() ?: ""
                Log.i(TAG, json)
                if (!it.isSuccessful) throw IOException("Unexpected code $it")
                val body = Gson().fromResponse<T>(json)
                block(body)
            } catch (e: Exception) {
                e.printStackTrace()
                block(null)
//                val json = """{"status":417,"message":"$e","data":{}}"""
//                block(Gson().fromResponse(json))
            }
        }
    }

    private fun loginEvent(mUid: Int, username: String, password: String) {
        uid = mUid
        this.username = username
        playingArticle = null
        playingBook = null

        // 数据库中存入登录信息
        val wd = dbHelper.writableDatabase
        wd.delete(AudinkDbHelper.AUTO_LOGIN, null, null)
        wd.insert(AudinkDbHelper.AUTO_LOGIN, null, ContentValues().apply {
            put(AudinkDbHelper.USERNAME, username)
            put(AudinkDbHelper.PASSWORD, password)
        })
    }
}
