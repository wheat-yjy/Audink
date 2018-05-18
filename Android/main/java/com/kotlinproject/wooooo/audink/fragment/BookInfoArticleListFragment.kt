package com.kotlinproject.wooooo.audink.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.adapter.ArticleListAdapter
import com.kotlinproject.wooooo.audink.model.ArticleInfo
import com.kotlinproject.wooooo.audink.utils.AppCache
import kotlinx.android.synthetic.main.fragment_book_info_article_list.*

class BookInfoArticleListFragment : Fragment() {
    private var bookId = -1
    private var listener: OnArticleListInteractionListener? = null
    private lateinit var articleListAdapter:ArticleListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bookId = it.getInt(BOOK_ID) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book_info_article_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView(AppCache.localDetailBookInfo(bookId).chapter ?: emptyList())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnArticleListInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnArticleListInteractionListener")
        }
    }

    private fun initView(articleList:List<ArticleInfo>) {
        articleListAdapter = ArticleListAdapter(this.activity, articleList) {
            listener?.playArticle(it)
        }
        articleListAdapter.startLoop()
        recycler_article_list.layoutManager = LinearLayoutManager(this.context)
        recycler_article_list.adapter = articleListAdapter
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroy() {
        super.onDestroy()
        articleListAdapter.stopLoop()
    }

    interface OnArticleListInteractionListener {
        fun playArticle(article: ArticleInfo)
    }

    companion object {

        private const val BOOK_ID = "bookId"

        @JvmStatic
        fun newInstance(bookId: Int) = BookInfoArticleListFragment().apply {
            arguments = Bundle().apply { putInt(BOOK_ID, bookId) }
        }
    }
}
