package com.kotlinproject.wooooo.audink.utils

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

fun SQLiteDatabase.mQuery(
    table: String,
    columns: Array<String>? = null,
    selection: String? = null,
    selectionArgs: Array<String>? = null,
    groupBy: String? = null,
    having: String? = null,
    orderBy: String? = null
): Cursor = this.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)

inline operator fun <reified T> Cursor.get(columnName: String): T {
    return when (T::class.java) {
        Int::class.java, java.lang.Integer::class.java
        -> this.getInt(getColumnIndex(columnName)) as T
        Long::class.java, java.lang.Long::class.java
        -> this.getLong(getColumnIndex(columnName)) as T
        Float::class.java, java.lang.Float::class.java
        -> this.getFloat(getColumnIndex(columnName)) as T
        Double::class.java, java.lang.Double::class.java
        -> this.getDouble(getColumnIndex(columnName)) as T
        String::class.java
        -> this.getString(getColumnIndex(columnName)) as T
        Short::class.java, java.lang.Short::class.java
        -> this.getShort(getColumnIndex(columnName)) as T
        ByteArray::class.java
        -> this.getBlob(getColumnIndex(columnName)) as T
        else
        -> throw ClassCastException("类型不匹配：${T::class.java}")
    }
}