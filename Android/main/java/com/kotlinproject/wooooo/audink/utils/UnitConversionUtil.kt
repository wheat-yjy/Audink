package com.kotlinproject.wooooo.audink.utils

import android.content.Context

/**
 * 根据手机分辨率从DP转成PX
 * @param context
 * @param dpValue
 * @return
 */
fun dip2px(context: Context, dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 将sp值转换为px值，保证文字大小不变
 * @param spValue
 * @return
 */
fun sp2px(context: Context, spValue: Float): Int {
    val fontScale = context.resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率PX(像素)转成DP
 * @param context
 * @param pxValue
 * @return
 */
fun px2dip(context: Context, pxValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 将px值转换为sp值，保证文字大小不变
 * @param pxValue
 * @return
 */

fun px2sp(context: Context, pxValue: Float): Int {
    val fontScale = context.resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}
