package com.kotlinproject.wooooo.audink.tool

import android.media.MediaPlayer
import android.util.Log

/**
 * Created by wooooo on 2018/3/31.
 * 音乐播放类
 */
class MusicPlayer {
    private val mMediaPlayer = MediaPlayer()

    /** 音频总时长 */
    val duration
        get() = mMediaPlayer.duration

    /** 音频现在的播放点 */
    var position
        get() = mMediaPlayer.currentPosition
        set(value) {
            mMediaPlayer.seekTo(value)
        }

    /** 播放状态 */
    val isPlaying
        get() = mMediaPlayer.isPlaying

    /** 加载音乐 */
    fun loadMusic(path: String) {
        mMediaPlayer.reset()
        mMediaPlayer.setDataSource(path)
        mMediaPlayer.prepare()
    }

    /** 播放结束监听 */
    fun setOnCompletionListener(listener: MediaPlayer.OnCompletionListener?) {
        mMediaPlayer.setOnCompletionListener(listener)
    }

    /** 播放 */
    fun play() {
        if (!mMediaPlayer.isPlaying) {
            mMediaPlayer.start()
        }
    }

    /** 暂停 */
    fun pause() {
        if (mMediaPlayer.isPlaying) {
            mMediaPlayer.pause()
        }
    }

    /** 停止 */
    fun stop() {
        if (mMediaPlayer.isPlaying) {
            mMediaPlayer.stop()
        }
    }

    /** 关闭 */
    fun close() {
        mMediaPlayer.stop()
        mMediaPlayer.release()
    }
}