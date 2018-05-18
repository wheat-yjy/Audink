package com.kotlinproject.wooooo.audink.tool

import com.kotlinproject.wooooo.audink.utils.px2sp
import java.io.File
import java.text.BreakIterator
import java.util.*

class PageTextCutter(var pageAdapter: PagePaintAdapter) : PageArticleCutter {

    private var text: String = ""

    private var pageStartPos = 0
    private val pageStarts = mutableListOf<Int>()

    private var page = 1 // 比index大1

    @Deprecated("")
    override fun load(file: File) {
    }

    override fun load(text: String) {
        this.text = text.replace("\r\n", "\n")
        // 试试在加载的时候就把分页切好
        pageStarts.clear()
        pageStarts.add(0)

        var lastPos = 0
        pageStartPos = 0

        // 拼命翻页直到最后一页，新页首=上一个则表示翻到最后一页
        while (true) {
            innerNextPage()
            if (pageStartPos == lastPos) {
                break
            } else {
                pageStarts.add(pageStartPos)
                lastPos = pageStartPos
            }
        }
        pageStartPos = 0
    }

    /** 前一页 */
    override fun prePage() {
        val currentIndex = page - 1
        if (currentIndex > 0) {
            pageStartPos = pageStarts[currentIndex - 1]
            page--
        }
        // 若已经是最开始就不动了
//        if (pageStartPos <= 0) {
//            pageStartPos = 0
//            return
//        }
//        // 不是最开始，每次往前搜索一个换行符
//        var currentText = text.substring(0, pageStartPos)
//        val maxY = pageAdapter.visibleHeight
//        var currentY = 0
//        ot@ while (true) {
//            val paraBrk = currentText.indexOfLast { it == '\n' }.let { if (it < 0) 0 else it }
//            val subParaTemp = currentText.substring(paraBrk)
//            var subPara = subParaTemp.replace("\n", "")
//            if (subParaTemp.startsWith("\n")) currentText = currentText.substring(0, paraBrk)
//            val decrementList = mutableListOf<Pair<Int, Int>>()
//            decrementList += 1 to 2 * pageAdapter.lineSpacePx
//            while (subPara.isNotBlank()) {
//                val sentenceBrk = pageAdapter.painter.breakText(subPara, true, pageAdapter.visibleWidth.toFloat(), null)
//                decrementList += sentenceBrk to (pageAdapter.fontSizePx + pageAdapter.lineSpacePx)
//                subPara = subPara.substring(sentenceBrk)
//            }
//            for ((dePos, deY) in decrementList.reversed()) {
//                if (currentY + deY >= maxY) break@ot
//                currentY += deY
//                pageStartPos -= dePos
//            }
//        }
//        if (pageStartPos < 0) pageStartPos = 0
    }

    /** 后一页 */
    override fun nextPage() {
        val currentIndex = page - 1
        if (currentIndex < pageStarts.count() - 1) {
            pageStartPos = pageStarts[currentIndex + 1]
            page++
        }
    }

    private fun innerNextPage() {
        var cuttingText = text.substring(pageStartPos)

        var currentY = 0
        val maxY = pageAdapter.visibleHeight
        var increment = 0
        while (true) {
            val brk = cuttingText.indexOf('\n').let {
                val t = pageAdapter.painter.breakText(cuttingText, true, pageAdapter.visibleWidth.toFloat(), null)
                if (it in 0..(t - 1)) {
                    val tempNewY = currentY + pageAdapter.fontSizePx + pageAdapter.lineSpacePx * 3
                    if (tempNewY >= maxY) -1 else {
                        currentY = tempNewY
                        it + 1
                    }
                } else {
                    val tempNewY = currentY + pageAdapter.fontSizePx + pageAdapter.lineSpacePx
                    if (tempNewY >= maxY) -1 else {
                        currentY = tempNewY
                        t
                    }
                }
            }
            if (brk < 0) break
            increment += brk
            cuttingText = cuttingText.substring(brk)
            if (cuttingText.isBlank()) break
        }
        // 若不是最后一页则更新页码
        if (pageStartPos + increment < text.length) {
            pageStartPos += increment
        }
    }

    override fun getCuttedText(): Vector<String> {
        val vec = Vector<String>()
        var cuttingText = text.substring(pageStartPos)
        var currentY = 0
        val maxY = pageAdapter.visibleHeight
        while (true) {
            var newLine = false
            val brk = cuttingText.indexOf('\n').let {
                val t = pageAdapter.painter.breakText(cuttingText, true, pageAdapter.visibleWidth.toFloat(), null)
                if (it in 0..(t - 1)) {
                    val tempNewY = currentY + pageAdapter.fontSizePx + pageAdapter.lineSpacePx * 3
                    if (tempNewY >= maxY) -1 else {
                        currentY = tempNewY
                        newLine = true
                        it + 1
                    }
                } else {
                    val tempNewY = currentY + pageAdapter.fontSizePx + pageAdapter.lineSpacePx
                    if (tempNewY >= maxY) -1 else {
                        currentY = tempNewY
                        t
                    }
                }
            }
            if (brk < 0) break
            vec += cuttingText.substring(0, brk).replace("\n", "")
            if (newLine) vec += ""
            cuttingText = cuttingText.substring(brk)
            if (cuttingText.isBlank()) break
        }
        return vec
    }


    override fun getTotalPages(): Int = pageStarts.count()

    override fun getCurrentPage(): Int = page
}