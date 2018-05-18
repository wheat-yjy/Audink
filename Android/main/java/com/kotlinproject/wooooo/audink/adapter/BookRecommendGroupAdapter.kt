package com.kotlinproject.wooooo.audink.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.activity.BookInfoActivity
import com.kotlinproject.wooooo.audink.activity.ClassMoreBookActivity
import com.kotlinproject.wooooo.audink.model.BookInfo

/** 听书商城推荐组适配器 */
class BookRecommendGroupAdapter(
    val context: Context,
    val container: LinearLayout,
    val groupName:String
) {
    private val groupView = LayoutInflater
        .from(context)
        .inflate(R.layout.layout_book_store_recommend_group, container, false)
        .apply { container.addView(this) }

    private val groupViewContainer: LinearLayout = groupView
        .findViewById(R.id.linear_recom_group_contain)

    init {
        // 设置组标题
        val groupTitle: TextView = groupView.findViewById(R.id.txt_recom_group_title)
        groupTitle.text = groupName

        // 更多监听
        val txtMore: TextView = groupView.findViewById(R.id.txt_recom_group_more)
        txtMore.setOnClickListener {
            context.startActivity(Intent(context, ClassMoreBookActivity::class.java).putExtra(ClassMoreBookActivity.CLASS_NAME, groupName))
        }
    }

    fun addBook(book: BookInfo) {
        val item = LayoutInflater
            .from(context)
            .inflate(R.layout.layout_book_store_recommend_item, groupViewContainer, false)

        val viewItem:LinearLayout = item.findViewById(R.id.linear_book_store_recommend_item)
        val cover: ImageView = item.findViewById(R.id.image_book_store_recommend_item)
        val txt: TextView = item.findViewById(R.id.txt_book_store_recommend_item)

        // 打开书籍详情
        viewItem.setOnClickListener {
            context.startActivity(Intent(context, BookInfoActivity::class.java).putExtra(BookInfoActivity.BOOK_ID, book.bookId))
        }
        Glide.with(context).load(book.imagePath).into(cover)
        txt.text = book.bookName

        groupViewContainer.addView(item)
    }

    fun addBook(bookList: Collection<BookInfo>) {
        bookList.forEach { addBook(it) }
    }
}