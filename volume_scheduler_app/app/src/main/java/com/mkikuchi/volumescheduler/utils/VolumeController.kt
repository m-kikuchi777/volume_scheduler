package com.mkikuchi.volumescheduler.utils

import android.content.Context
import android.media.AudioManager
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mkikuchi.volumescheduler.R

/**
 * ボリュームのコントロールをバックグラウンドで行うクラス。
 */
class VolumeController(private val appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    companion object {
        const val CHANNEL_ID = "volumeScheduleSuccess"
        const val NOTIFICATION_ID = 2434
    }

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

        val builder = NotificationCompat.Builder(appContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(appContext.applicationInfo.name)
            .setContentText(appContext.getString(R.string.notification_text))

        with(NotificationManagerCompat.from(appContext)) {
            notify(NOTIFICATION_ID, builder.build())
        }

        return Result.success()
    }
}