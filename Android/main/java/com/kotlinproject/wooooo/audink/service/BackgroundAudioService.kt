package com.kotlinproject.wooooo.audink.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.kotlinproject.wooooo.audink.tool.MusicPlayer

class BackgroundAudioService : Service() {
    private val binder = BackgroundAudioBinder()

    private val musicPlayer = MusicPlayer()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    inner class BackgroundAudioBinder : Binder() {
        /** 播放状态 */
        var isPlaying
            get() = musicPlayer.isPlaying
            set(value) {
                if (value) {
                    musicPlayer.play()
                } else {
                    musicPlayer.pause()
                }
            }

        /** 播放位置 */
        var position
            get() = musicPlayer.position
            set(value) {
                musicPlayer.position = value
            }

        /** 播放时长 */
        val duration
            get() = musicPlayer.duration


        /** 加载音频 */
        fun loadAudio(path: String) {
            musicPlayer.loadMusic(path)
        }

        /** 设置播放完成监听 */
        fun setOnCompletionListener(listener: MediaPlayer.OnCompletionListener?) {
            musicPlayer.setOnCompletionListener(listener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayer.close()
    }
}