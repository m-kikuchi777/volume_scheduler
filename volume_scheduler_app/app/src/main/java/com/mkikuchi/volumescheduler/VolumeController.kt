package com.mkikuchi.volumescheduler

import android.content.Context
import android.media.AudioManager
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * ボリュームのコントロールをバックグラウンドで行うクラス。
 */
class VolumeController(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    /**
     * タスクを実行する。
     */
    override fun doWork(): Result {
        // 音量を調節するためのAudioManagerを取得する。
        val audioManager = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        // 音量をゼロにする。
        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            0,
            AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE
        )

        return Result.success()
    }
}