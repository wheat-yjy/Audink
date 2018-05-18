package com.kotlinproject.wooooo.audink.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.adapter.ShelfBookAdapter
import com.kotlinproject.wooooo.audink.model.ArticleInfo
import com.kotlinproject.wooooo.audink.model.BookInfo
import com.kotlinproject.wooooo.audink.utils.AppCache
import kotlinx.android.synthetic.main.fragment_my_shelf.*


class MyShelfFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var listener: OnMyShelfFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_shelf, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipe_my_shelf.setOnRefreshListener(this)
        initView(AppCache.localUserPreferBookList())
        onRefresh()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMyShelfFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnBookStoreFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onRefresh() {
        swipe_my_shelf.isRefreshing = true
        AppCache.asyncUserPreferBookList(this.activity) { list, isSuccess ->
            if (isSuccess) {
                initView(list)
            } else {
                Toast.makeText(this.context, "刷新失败",Toast.LENGTH_SHORT).show()
            }
            swipe_my_shelf.isRefreshing = false
        }
    }

    private fun initView(bookList: List<BookInfo>) {
        // 如果list是空，显示空布局
        fun v(b:Boolean) = if (b) View.VISIBLE else View.GONE
        swipe_my_shelf.visibility = v(bookList.isNotEmpty())
        linear_bookshelf_empty.visibility = v(bookList.isEmpty())

        // 加载列表
        recycler_bookshelf.layoutManager = LinearLayoutManager(this.activity)
        recycler_bookshelf.adapter = ShelfBookAdapter(this.activity, bookList){resId, position ->
            when (resId) {
                R.id.menu_item_cancel_prefer -> {
                    removeItemAt(position)
                    if (itemCount == 0) {
                        swipe_my_shelf.visibility = v(false)
                        linear_bookshelf_empty.visibility = v(true)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    interface OnMyShelfFragmentInteractionListener {
        fun onNewArticleSelected(article: ArticleInfo)
    }
}
