package com.kotlinproject.wooooo.audink.activity

import android.os.Bundle
import android.os.IBinder
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.adapter.ShelfBookAdapter
import com.kotlinproject.wooooo.audink.model.BookInfo
import com.kotlinproject.wooooo.audink.utils.AppCache
import kotlinx.android.synthetic.main.activity_classification_more_book.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ClassMoreBookActivity : BaseActivity() {

    private val className by lazy { intent.getStringExtra(CLASS_NAME) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classification_more_book)
    }

    override fun onBgmServiceBind(service: IBinder?) {
        super.onBgmServiceBind(service)

        // 更改标题
        toolbar.title = className

        // 联网获取书籍
        AppCache.asyncRecommendMoreBook(this, className) {list, isSuccess ->
            if (isSuccess) {
                initView(list)
            } else {
                Toast.makeText(this,"获取推荐列表失败",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView(bookList: List<BookInfo>) {
        recycler_more_book.layoutManager = LinearLayoutManager(this)
        recycler_more_book.adapter = ShelfBookAdapter(this,bookList)
    }

    companion object {
        const val CLASS_NAME = "class_name"
    }
}
