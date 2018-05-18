package com.kotlinproject.wooooo.audink.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.activity.BookInfoActivity
import com.kotlinproject.wooooo.audink.activity.ReadActivity
import com.kotlinproject.wooooo.audink.service.BackgroundAudioService
import com.kotlinproject.wooooo.audink.utils.AppCache
import com.kotlinproject.wooooo.audink.utils.getAudinkArticleFiles
import com.kotlinproject.wooooo.audink.utils.getAudinkBookFiles
import kotlinx.android.synthetic.main.layout_player_bar.*

class PlayBarAdapter(val activity: Activity, private val audioBinder: BackgroundAudioService.BackgroundAudioBinder) {
    private val viewPlayBar = activity.findViewById(R.id.view_play_bar) as View
    private val checkButtonPlaying = activity.findViewById(R.id.check_audio_play) as CheckBox
    private val imageBook = activity.findViewById(R.id.image_play_book) as ImageView
    private val txtBarTitle = activity.findViewById(R.id.txt_player_bar_title) as TextView
    private val imageCover = activity.findViewById(R.id.image_play_bar_cover) as ImageView

    init {
        // 音频播放和暂停
        checkButtonPlaying.setOnCheckedChangeListener { _, b ->
            if (AppCache.playingArticle != null) {
                audioBinder.isPlaying = b
            } else {
                checkButtonPlaying.isChecked = false
            }
        }

        // 书本
        imageBook.setOnClickListener {
            AppCache.playingBook?.let {
                if (activity !is BookInfoActivity) {
                    activity.startActivity(Intent(activity, BookInfoActivity::class.java).putExtra(BookInfoActivity.BOOK_ID, it.bookId))
                }
            }
        }

        // 进入阅读页面
        viewPlayBar.setOnClickListener {
            AppCache.playingArticle?.let {
                activity.startActivity(Intent(activity, ReadActivity::class.java).putExtra(ReadActivity.ARTICLE_ID, it.articleId))
            }
        }
    }

    fun refreshBarState() {
        try {
            checkButtonPlaying.isChecked = audioBinder.isPlaying
        } catch (e: UninitializedPropertyAccessException) {
            e.printStackTrace()
        }
        val playingArticle = AppCache.playingArticle
        playingArticle?.run { txtBarTitle.text = playingArticle.articleName }
        Glide.with(activity).load(AppCache.playingBook?.imagePath).into(imageCover)
    }
}