package com.kotlinproject.wooooo.audink.tool

import java.io.File
import java.util.*

interface PageArticleCutter {
    fun load(text: String)
    fun load(file: File)

    fun prePage()
    fun nextPage()
    fun getCuttedText():Vector<String>
    fun getCurrentPage(): Int
    fun getTotalPages():Int
}