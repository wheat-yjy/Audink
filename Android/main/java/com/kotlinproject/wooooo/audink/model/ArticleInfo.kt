package com.kotlinproject.wooooo.audink.model

import com.google.gson.annotations.SerializedName

data class ArticleInfo(
    @SerializedName("chapterId")
    var articleId: Int = 0,
    @SerializedName("chaptername")
    var articleName: String = "unknown",
    @SerializedName("audioUrl")
    var audioPath: String? = null,
    @SerializedName("content")
    var content: String? = null,
    @SerializedName("lrc")
    var lrc: String? = null,
    @SerializedName("bookId")
    var bookId: Int = -1,
    @SerializedName("abst")
    var articleAbstract : String? = null
)