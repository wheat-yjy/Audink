package com.kotlinproject.wooooo.audink.model

data class JsonResponse<T>(
    var status: Int = 0,
    var message: String = "",
    var data: T = Any() as T
)