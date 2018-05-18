package com.kotlinproject.wooooo.audink.model

import com.google.gson.annotations.SerializedName

data class ResponseBooks(
    @SerializedName("books")
    var books: List<BookInfo> = emptyList()
)