package com.kotlinproject.wooooo.audink.adapter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.activity.BookInfoActivity
import com.kotlinproject.wooooo.audink.model.BookInfo
import com.kotlinproject.wooooo.audink.utils.AppCache
import com.kotlinproject.wooooo.audink.utils.getAudinkBookFiles

class ShelfBookAdapter(
    private val activity: Activity,
    bookList: List<BookInfo>,
    private val notifyPreferChange: (ShelfBookAdapter.(resId: Int, position: Int) -> Unit)? = null
) : RecyclerView.Adapter<ShelfBookAdapter.ViewHolder>() {

    private val itemList = bookList.toMutableList()

    // 被选中的项目，用于切换时更改选中标记
    private var selectedItem: View? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.layout_shelf_book_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = itemList[position]
        holder?.imageCover?.let {
            Glide.with(activity).load(item.imagePath).into(it)
        }
        holder?.txtTitle?.text = item.bookName
        holder?.txtAuthor?.text = item.author
        holder?.txtUploader?.text = item.uploader
        holder?.linearShelfItem?.setOnClickListener {
            activity.startActivity(Intent(activity, BookInfoActivity::class.java).putExtra(BookInfoActivity.BOOK_ID, item.bookId))
        }

        holder?.imageMore?.setOnClickListener {
            val popup = PopupMenu(activity, it)
            val inflater = popup.menuInflater
            if (item.bookId in AppCache.localUserPreferBookList().map { it.bookId }) {

                inflater.inflate(R.menu.menu_book_list_cancel_prefer, popup.menu)
            } else {

                inflater.inflate(R.menu.menu_book_list_prefer, popup.menu)
            }
            popup.setOnMenuItemClickListener { view ->
                when (view.itemId) {
                    R.id.menu_item_prefer        -> AppCache.asyncPreferBook(activity, item.bookId) {
                        Toast.makeText(activity, if (it) "收藏成功" else "收藏失败", Toast.LENGTH_SHORT).show()
                        if (it) notifyPreferChange?.invoke(this, view.itemId, position)
                    }
                    R.id.menu_item_cancel_prefer -> AppCache.asyncCancelPreferBook(activity, item.bookId) {
                        Toast.makeText(activity, if (it) "取消收藏成功" else "取消收藏失败", Toast.LENGTH_SHORT).show()
                        if (it) notifyPreferChange?.invoke(this, view.itemId, position)
                    }
                }
                false
            }
            popup.show()
        }
        if (AppCache.playingBook?.let { it.bookId == item.bookId } == true) replaceSelectItem(holder?.viewPlaying)
    }

    override fun getItemCount(): Int = itemList.size

    fun removeItemAt(position: Int) {
        itemList.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(book: BookInfo) {
        itemList.add(book)
        notifyDataSetChanged()
    }

    private fun replaceSelectItem(newItem: View?) {
        selectedItem?.let { it.visibility = View.INVISIBLE }
        newItem?.visibility = View.VISIBLE
        selectedItem = newItem
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val linearShelfItem: LinearLayout = view.findViewById(R.id.linear_shelf_item)
        val imageCover: ImageView = view.findViewById(R.id.image_shelf_cover)
        val txtTitle: TextView = view.findViewById(R.id.txt_shelf_title)
        val txtAuthor: TextView = view.findViewById(R.id.txt_shelf_author)
        val txtUploader: TextView = view.findViewById(R.id.txt_shelf_uploader)
        val viewPlaying: View = view.findViewById(R.id.view_shelf_playing)
        val imageMore: ImageView = view.findViewById(R.id.image_shelf_more)
    }
}