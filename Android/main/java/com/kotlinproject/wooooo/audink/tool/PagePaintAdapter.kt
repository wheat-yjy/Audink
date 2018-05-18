package com.kotlinproject.wooooo.audink.tool

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.WindowManager
import com.kotlinproject.wooooo.audink.utils.dip2px

class PagePaintAdapter(
    val context: Context,
    val fontSizePx: Int
) {
    private val windowService = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val screenMetrics = DisplayMetrics().apply { windowService.defaultDisplay.getMetrics(this) }
    val screenWidth = screenMetrics.widthPixels
    val screenHeight = screenMetrics.heightPixels
    /** 行间距 */
    val lineSpacePx = fontSizePx / 3
    val marginWidth = dip2px(context, 15f)
    val marginHeight = dip2px(context, 25f)
    val visibleWidth = screenWidth - 2 * marginWidth
    val visibleHeight = screenHeight - 2 * marginHeight
    /** 每页行数 */
    val pageLineCount = visibleHeight / (fontSizePx + lineSpacePx)
    val painter = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = fontSizePx.toFloat()
        color = Color.BLACK
    }
}