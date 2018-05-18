package com.kotlinproject.wooooo.audink.model

import com.google.gson.annotations.SerializedName

data class BookInfo(
    @SerializedName("bookId")
    var bookId: Int = 0,
    @SerializedName("bookname")
    var bookName: String = "unknown",
    @SerializedName("author")
    var author: String = "unknown",
    @SerializedName("summary")
    var summary: String = "",
    @SerializedName("uploader")
    var uploader: String = "",
    @SerializedName("imageUrl")
    var imagePath: String = "",
    @SerializedName("chapters")
    var chapter: List<ArticleInfo>? = null,
    @SerializedName("classify")
    var classify: String = "unknown"
)