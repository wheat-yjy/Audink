package com.kotlinproject.wooooo.audink.tool

import android.graphics.Canvas

class PagePainter(var pageAdapter:PagePaintAdapter ) {
    fun drawText(canvas: Canvas,articleText:Iterable<String>) {
        var y = pageAdapter.marginHeight + pageAdapter.lineSpacePx
        for (line in articleText) {
            y += if (line.isBlank()) {
                2 * pageAdapter.lineSpacePx
            } else {
                canvas.drawText(line, pageAdapter.marginWidth.toFloat(), y.toFloat(), pageAdapter.painter)
                pageAdapter.lineSpacePx+ pageAdapter.fontSizePx
            }
        }
    }
}