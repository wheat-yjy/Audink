package com.kotlinproject.wooooo.audink.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AudinkDbHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createAutoLogin = """
create table $AUTO_LOGIN (
    $USERNAME text primary key,
    $PASSWORD text
)
        """.trimIndent()

        val createUserPrefer = """
create table $USER_PREFER (
    $UID     integer,
    $BOOK_ID integer
)
        """.trimIndent()

        val createBookArticle = """
create table $BOOK_CHAPTER (
    $BOOK_ID     integer,
    $ARTICLE_ID  integer
)
        """.trimIndent()

        db?.apply {
            execSQL(createAutoLogin)
            execSQL(createUserPrefer)
            execSQL(createBookArticle)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    companion object {
        const val AUTO_LOGIN = "auto_login"
        const val USER_PREFER = "user_prefer"
        const val BOOK_CHAPTER = "book_article"

        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val UID = "uid"
        const val ARTICLE_ID = "article_id"
        const val BOOK_ID = "book_id"
    }
}