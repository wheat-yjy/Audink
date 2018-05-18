package com.kotlinproject.wooooo.audink.tool

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import com.kotlinproject.wooooo.audink.utils.dip2px
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.io.UnsupportedEncodingException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.Charset
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class PageFormater(
    private val context: Context,
    // 屏幕宽高
    private val mWidth: Int,
    private val mHeight: Int,
    // 字体大小
    var mFontSize: Int = 0
) {
    /** 间隔宽度 */
    private var marginWidth: Int = dip2px(context, 15f)
    /** 间隔高度 */
    private var marginHeight: Int = dip2px(context, 15f)
    /** 字体大小 */
    private var mNumFontSize = dip2px(context, 16f)
    /** 行间距 */
    private var mLineSpace: Int = mFontSize / 5 * 2
    /** 字节长度 */
    private var mbBufferLen: Int = 0
    /** MappedByteBuffer：高效的文件内存映射 */
    private var mbBuff: MappedByteBuffer? = null
    /** 页首的位置 */
    private var curBeginPos = 0
    /** 页尾的位置 */
    private var curEndPos = 0
    /** 文字区域宽度 */
    private val mVisibleWidth: Int = mHeight - marginHeight * 2 - mNumFontSize * 2 - mLineSpace * 2
    /** 文字区域高度 */
    private val mVisibleHeight: Int = mWidth - marginWidth * 2
    /** 每页行数 */
    private var mPageLineCount: Int = mVisibleHeight / (mFontSize + mLineSpace)

    private var tempBeginPos: Int = 0
    private var tempEndPos: Int = 0
    private var currentChapter: Int = 0
    private var tempChapter: Int = 0
    private var mLines: Vector<String> = Vector()

    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = mFontSize.toFloat()
        color = Color.BLACK
    }
    private var mTitlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = mNumFontSize.toFloat()
        color = 0x837B63
    }

    private val decimalFormat = DecimalFormat("#0.00")
    private val dateFormat = SimpleDateFormat("HH:mm")
    private var timeLen = mTitlePaint.measureText("00:00").toInt()
    private var percentLen = mTitlePaint.measureText("00.00%").toInt()
    private var rectF: Rect = Rect(0, 0, mWidth, mHeight)

    private var chapterSize = 0
    private var currentPage = 1

    private var charset = "UTF-8"

    var mBookPageBg: Bitmap? = null
    var time: String = dateFormat.format(Date())

    /**
     * 打开书籍文件
     *
     * @param chapter  阅读章节
     * @param position 阅读位置
     * @return 0：文件不存在或打开失败  1：打开成功
     */
    fun openBook(bookPath: String): Int {
        try {
            val file = File(bookPath)
            val length = file.length()
            if (length > 10) {
                mbBufferLen = length.toInt()
                // 创建文件通道，映射为MappedByteBuffer
                mbBuff = RandomAccessFile(file, "r")
                    .channel
                    .map(FileChannel.MapMode.READ_ONLY, 0, length)
                curBeginPos = 0
                curEndPos = 0
                mLines.clear()
                return 1
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return 0
    }

    /**
     * 绘制阅读页面
     *
     * @param canvas
     */
    @Synchronized
    fun onDraw(canvas: Canvas) {
        if (mLines.size == 0) {
            curEndPos = curBeginPos
            mLines = pageDown()
        }
        if (mLines.size > 0) {
            var y = marginHeight + (mLineSpace shl 1)
            // 绘制背景
            if (mBookPageBg != null) {
                canvas.drawBitmap(mBookPageBg!!, null, rectF, null)
            } else {
                canvas.drawColor(Color.WHITE)
            }
            y += mLineSpace + mNumFontSize
            // 绘制阅读页面文字
            for (line in mLines) {
                y += mLineSpace
                if (line.endsWith("@")) {
                    canvas.drawText(line.substring(0, line.length - 1), marginWidth.toFloat(), y.toFloat(), mPaint)
                    y += mLineSpace
                } else {
                    canvas.drawText(line, marginWidth.toFloat(), y.toFloat(), mPaint)
                }
                y += mFontSize
            }
            // 绘制提示内容
            val percent = currentChapter.toFloat() * 100 / chapterSize
            canvas.drawText(decimalFormat.format(percent.toDouble()) + "%", ((mWidth - percentLen) / 2).toFloat(),
                (mHeight - marginHeight).toFloat(), mTitlePaint)

            val mTime = dateFormat.format(Date())
            canvas.drawText(mTime, (mWidth - marginWidth - timeLen).toFloat(), (mHeight - marginHeight).toFloat(), mTitlePaint)

            // todo 保存阅读进度
//            SettingManager.getInstance().saveReadProgress(bookId, currentChapter, curBeginPos, curEndPos)
        }
    }

    /**
     * 指针移到上一页页首
     */
    private fun pageUp() {
        var strParagraph = ""
        val lines = Vector<String>() // 页面行
        var paraSpace = 0
        mPageLineCount = mVisibleHeight / (mFontSize + mLineSpace)
        while (lines.size < mPageLineCount && curBeginPos > 0) {
            val paraLines = Vector<String>() // 段落行
            val parabuffer = readParagraphBack(curBeginPos) // 1.读取上一个段落

            curBeginPos -= parabuffer.size // 2.变换起始位置指针
            try {
                strParagraph = String(parabuffer, Charset.forName(charset))
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            strParagraph = strParagraph.replace("\r\n".toRegex(), "  ")
            strParagraph = strParagraph.replace("\n".toRegex(), " ")

            while (strParagraph.isNotEmpty()) { // 3.逐行添加到lines
                val paintSize = mPaint.breakText(strParagraph, true, mVisibleWidth.toFloat(), null)
                paraLines.add(strParagraph.substring(0, paintSize))
                strParagraph = strParagraph.substring(paintSize)
            }
            lines.addAll(0, paraLines)

            while (lines.size > mPageLineCount) { // 4.如果段落添加完，但是超出一页，则超出部分需删减
                try {
                    curBeginPos += lines[0].toByteArray(charset(charset)).size // 5.删减行数同时起始位置指针也要跟着偏移
                    lines.removeAt(0)
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

            }
            curEndPos = curBeginPos // 6.最后结束指针指向下一段的开始处
            paraSpace += mLineSpace
            mPageLineCount = (mVisibleHeight - paraSpace) / (mFontSize + mLineSpace) // 添加段落间距，实时更新容纳行数
        }
    }

    /**
     * 根据起始位置指针，读取一页内容
     *
     * @return
     */
    private fun pageDown(): Vector<String> {
        var strParagraph = ""
        val lines = Vector<String>()
        var paraSpace = 0
        mPageLineCount = mVisibleHeight / (mFontSize + mLineSpace)
        while (lines.size < mPageLineCount && curEndPos < mbBufferLen) {
            val parabuffer = readParagraphForward(curEndPos)
            curEndPos += parabuffer.size
            try {
                strParagraph = String(parabuffer, Charset.forName(charset))
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            strParagraph = strParagraph.replace("\r\n".toRegex(), "  ")
                .replace("\n".toRegex(), " ") // 段落中的换行符去掉，绘制的时候再换行

            while (strParagraph.isNotEmpty()) {
                val paintSize = mPaint.breakText(strParagraph, true, mVisibleWidth.toFloat(), null)
                lines.add(strParagraph.substring(0, paintSize))
                strParagraph = strParagraph.substring(paintSize)
                if (lines.size >= mPageLineCount) {
                    break
                }
            }
            lines[lines.size - 1] = lines[lines.size - 1] + "@"
            if (strParagraph.isNotEmpty()) {
                try {
                    curEndPos -= strParagraph.toByteArray(charset(charset)).size
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

            }
            paraSpace += mLineSpace
            mPageLineCount = (mVisibleHeight - paraSpace) / (mFontSize + mLineSpace)
        }
        return lines
    }

    /**
     * 获取最后一页的内容。比较繁琐，待优化
     *
     * @return
     */
    fun pageLast(): Vector<String> {
        var strParagraph = ""
        val lines = Vector<String>()
        currentPage = 0
        while (curEndPos < mbBufferLen) {
            var paraSpace = 0
            mPageLineCount = mVisibleHeight / (mFontSize + mLineSpace)
            curBeginPos = curEndPos
            while (lines.size < mPageLineCount && curEndPos < mbBufferLen) {
                val parabuffer = readParagraphForward(curEndPos)
                curEndPos += parabuffer.size
                try {
                    strParagraph = String(parabuffer, Charset.forName(charset))
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

                strParagraph = strParagraph.replace("\r\n".toRegex(), "  ")
                strParagraph = strParagraph.replace("\n".toRegex(), " ") // 段落中的换行符去掉，绘制的时候再换行

                while (strParagraph.isNotEmpty()) {
                    val paintSize = mPaint.breakText(strParagraph, true, mVisibleWidth.toFloat(), null)
                    lines.add(strParagraph.substring(0, paintSize))
                    strParagraph = strParagraph.substring(paintSize)
                    if (lines.size >= mPageLineCount) {
                        break
                    }
                }
                lines[lines.size - 1] = lines[lines.size - 1] + "@"

                if (strParagraph.isNotEmpty()) {
                    try {
                        curEndPos -= strParagraph.toByteArray(charset(charset)).size
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }

                }
                paraSpace += mLineSpace
                mPageLineCount = (mVisibleHeight - paraSpace) / (mFontSize + mLineSpace)
            }
            if (curEndPos < mbBufferLen) {
                lines.clear()
            }
            currentPage++
        }
        //SettingManager.getInstance().saveReadProgress(bookId, currentChapter, curBeginPos, curEndPos);
        return lines
    }

    /**
     * 读取下一段落
     *
     * @param curEndPos 当前页结束位置指针
     * @return
     */
    private fun readParagraphForward(curEndPos: Int): ByteArray {
        var b0: Byte
        var i = curEndPos
        while (i < mbBufferLen) {
            b0 = mbBuff!!.get(i++)
            if (b0.toInt() == 0x0a) {
                break
            }
        }
        val nParaSize = i - curEndPos
        val buf = ByteArray(nParaSize)
        i = 0
        while (i < nParaSize) {
            buf[i] = mbBuff!!.get(curEndPos + i)
            i++
        }
        return buf
    }

    /**
     * 读取上一段落
     *
     * @param curBeginPos 当前页起始位置指针
     * @return
     */
    private fun readParagraphBack(curBeginPos: Int): ByteArray {
        var b0: Byte
        var i = curBeginPos - 1
        while (i > 0) {
            b0 = mbBuff!!.get(i)
            if (b0.toInt() == 0x0a && i != curBeginPos - 1) {
                i++
                break
            }
            i--
        }
        val nParaSize = curBeginPos - i
        val buf = ByteArray(nParaSize)
        for (j in 0 until nParaSize) {
            buf[j] = mbBuff!!.get(i + j)
        }
        return buf
    }

    fun hasNextPage(): Boolean {
        return curEndPos < mbBufferLen
    }

    fun hasPrePage(): Boolean {
        return curBeginPos > 0
    }

    /**
     * 跳转下一页
     */
    fun nextPage(): Boolean {
        if (!hasNextPage()) { // 最后一章的结束页
            return false
        } else {
            tempChapter = currentChapter
            tempBeginPos = curBeginPos
            tempEndPos = curEndPos
            curBeginPos = curEndPos // 起始指针移到结束位置
            mLines.clear()
            mLines = pageDown() // 读取一页内容
        }
        return true
    }

    /**
     * 跳转上一页
     */
    fun prePage(): Boolean {
        if (!hasPrePage()) { // 第一章第一页
            return false
        } else {
            // 保存当前页的值
            tempChapter = currentChapter
            tempBeginPos = curBeginPos
            tempEndPos = curEndPos
            mLines.clear()
            pageUp() // 起始指针移到上一页开始处
            mLines = pageDown() // 读取一页内容
        }
        return true
    }

    /**
     * 获取当前阅读位置
     *
     * @return index 0：起始位置 1：结束位置
     */
    fun getPosition(): IntArray {
        return intArrayOf(currentChapter, curBeginPos, curEndPos)
    }

    fun getHeadLineStr(): String {
        return if (mLines.size > 1) {
            mLines[0]
        } else ""
    }

    /**
     * 设置字体大小
     *
     * @param fontsize 单位：px
     */
    fun setTextFont(fontsize: Int) {
        mFontSize = fontsize
        mLineSpace = mFontSize / 5 * 2
        mPaint.textSize = mFontSize.toFloat()
        mPageLineCount = mVisibleHeight / (mFontSize + mLineSpace)
        curEndPos = curBeginPos
        nextPage()
    }

    /**
     * 设置字体颜色
     *
     * @param textColor
     * @param titleColor
     */
    fun setTextColor(textColor: Int, titleColor: Int) {
        mPaint.color = textColor
        mTitlePaint.color = titleColor
    }

    /**
     * 根据百分比，跳到目标位置
     *
     * @param persent
     */
    fun setPercent(persent: Int) {
        val a = (mbBufferLen * persent).toFloat() / 100
        curEndPos = a.toInt()
        if (curEndPos == 0) {
            nextPage()
        } else {
            nextPage()
            prePage()
            nextPage()
        }
    }

    fun recycle() {
        if (mBookPageBg != null && !mBookPageBg!!.isRecycled) {
            mBookPageBg!!.recycle()
            mBookPageBg = null
        }
    }
}