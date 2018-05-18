package com.kotlinproject.wooooo.audink.model

import com.google.gson.annotations.SerializedName
import com.kotlinproject.wooooo.audink.utils.AppCache

data class BookStoreInfo(
    @SerializedName("imageUrl")
    var bannerRecommend: List<String> = emptyList(),
    @SerializedName("recommend")
    var recommend: List<BookInfo> = emptyList(),
    @SerializedName("classifyBooks")
    var classification: List<ClassifyBooksInfo>? = null
)