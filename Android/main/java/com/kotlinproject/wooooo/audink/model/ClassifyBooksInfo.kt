package com.kotlinproject.wooooo.audink.model

import com.google.gson.annotations.SerializedName

data class ClassifyBooksInfo(
    @SerializedName("classify")
    var className: String = "unknown",
    @SerializedName("books")
    var bookList: List<BookInfo>? = null
)