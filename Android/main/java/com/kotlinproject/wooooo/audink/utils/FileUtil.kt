package com.kotlinproject.wooooo.audink.utils

import android.content.Context
import android.os.Environment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kotlinproject.wooooo.audink.model.ArticleInfo
import com.kotlinproject.wooooo.audink.model.BookInfo
import com.kotlinproject.wooooo.audink.model.JsonResponse
import java.io.*

fun getAudinkRootDictionary(): File {
    val file = File(Environment.getExternalStorageDirectory().path + "/AudinkDownload/")
    if (!file.exists()) {
        file.mkdirs()
    }
    return file
}

fun getAudinkArticleFiles(articleId: Int): AudinkArticleFilePackage {
    val file = getAudinkRootDictionary().path + "/Article/$articleId/"
    return AudinkArticleFilePackage(file + "text.txt", file + "audio.mp3")
}

fun getAudinkBookFiles(bookId: Int): AudinkBookFilePackage {
    val file = getAudinkRootDictionary().path + "/Book/$bookId/"
    return AudinkBookFilePackage(file + "info.txt", file + "cover.jpg")
}

fun openTxtFile(file: File): String {
    val builder = StringBuilder()
    if (!file.exists()) return builder.toString()
    file.readLines().forEach { builder.append(it).append("\n") }
    return builder.toString()
}

fun openTxtFile(path: String): String {
    return openTxtFile(File(path))
}

fun saveTxtFile(file: File, txt: String) {
    file.parentFile.mkdirs()
    if (!file.exists()) file.createNewFile()
    file.writeText(txt)
}

fun saveTxtFile(path: String, txt: String) {
    saveTxtFile(File(path), txt)
}

fun openBookFromJson(bookId: Int): BookInfo? {
    val json = openTxtFile(getAudinkBookFiles(bookId).infoPath)
    return if (json.isBlank()) null
    else fileGson.fromJson(json)
}

fun saveBookToJson(bookId: Int, bookInfo: BookInfo) {
    val json = fileGson.toJson(bookInfo)
    saveTxtFile(getAudinkBookFiles(bookId).infoPath, json)
}

fun openArticleFromJson(articleId: Int): ArticleInfo? {
    val json = openTxtFile(getAudinkArticleFiles(articleId).infoPath)
    return if (json.isBlank()) null
    else fileGson.fromJson(json)
}

fun saveArticleToJson(articleId: Int, articleInfo: ArticleInfo) {
    val json = fileGson.toJson(articleInfo)
    saveTxtFile(getAudinkArticleFiles(articleId).infoPath,json)
}

inline fun <reified T> Gson.fromJson(json: String): T = this.fromJson<T>(json, T::class.java)
inline fun <reified T> Gson.fromResponse(json: String) : T = this.fromJson(json, object :TypeToken<T>(){}.type)
inline fun <reified T> typeToken() = object :TypeToken<T>(){}.type

data class AudinkArticleFilePackage(val infoPath: String, val audioPath: String)
data class AudinkBookFilePackage(val infoPath: String, val coverPath: String)

private val fileGson = Gson()