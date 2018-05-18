package com.kotlinproject.wooooo.audink.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.utils.AppCache
import kotlinx.android.synthetic.main.fragment_book_info_summary.*

class BookInfoSummaryFragment : Fragment() {
    private var contentText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { contentText = it.getString(ARG_PARAM1) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book_info_summary, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        txt_summary.text = contentText
    }

    companion object {

        private const val ARG_PARAM1 = "content_text"

        @JvmStatic
        fun newInstance(summary: String) = BookInfoSummaryFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, summary)
            }
        }
    }
}
