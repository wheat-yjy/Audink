package com.kotlinproject.wooooo.audink.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.activity.BookInfoActivity
import com.kotlinproject.wooooo.audink.adapter.BookRecommendGroupAdapter
import com.kotlinproject.wooooo.audink.adapter.ShelfBookAdapter
import com.kotlinproject.wooooo.audink.model.ArticleInfo
import com.kotlinproject.wooooo.audink.model.BookStoreInfo
import com.kotlinproject.wooooo.audink.utils.AppCache
import kotlinx.android.synthetic.main.fragment_book_store.*
import kotlinx.android.synthetic.main.fragment_my_shelf.*


class BookStoreFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var listenerBookStore: OnBookStoreFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book_store, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipe_book_store.setOnRefreshListener(this)
        swipe_book_store.isRefreshing = true
        onRefresh()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBookStoreFragmentInteractionListener) {
            listenerBookStore = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnBookStoreFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerBookStore = null
    }

    override fun onRefresh() {
        AppCache.asyncBookStore(activity) { storeInfo, isSuccess ->
            if (isSuccess) {
                initView(storeInfo)
            } else {
                Toast.makeText(this.context,"刷新失败",Toast.LENGTH_SHORT).show()
            }
            swipe_book_store.isRefreshing = false
        }
    }

    private fun initView(storeInfo: BookStoreInfo) {
        Log.i("initView", storeInfo.toString())
        // 大横幅
        viewpager_book_store_banner.adapter = object : PagerAdapter() {
            private val imageViewList = storeInfo.bannerRecommend.zip(storeInfo.recommend).map {(path, book) ->
                ImageView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    setOnClickListener {
                        startActivity(Intent(this.context,BookInfoActivity::class.java).putExtra(BookInfoActivity.BOOK_ID,book.bookId))
                    }
                    Glide.with(this@BookStoreFragment).load(path).into(this)
                }
            }

            override fun isViewFromObject(view: View?, `object`: Any?): Boolean = view == `object`

            override fun getCount(): Int = imageViewList.size

            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
                container?.removeView(imageViewList[position])
            }

            override fun instantiateItem(container: ViewGroup?, position: Int): Any {
                container?.addView(imageViewList[position])
                return imageViewList[position]
            }
        }

        linear_book_store_contain.removeAllViews()

        // 推荐组
        storeInfo.classification?.forEach {
            it.bookList?.let { it1 ->
                BookRecommendGroupAdapter(
                    this.context,
                    linear_book_store_contain,
                    it.className
                ).addBook(it1)
            }
        }
    }

    interface OnBookStoreFragmentInteractionListener {
        fun onNewArticleSelected(article:ArticleInfo)
    }
}
