package com.mkikuchi.volumescheduler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val volumeController = PeriodicWorkRequest
            .Builder(
                VolumeController::class.java,
                15, TimeUnit.MINUTES)
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(this).enqueue(volumeController)
        WorkManager.getInstance(this).cancelAllWork()
    }
}
