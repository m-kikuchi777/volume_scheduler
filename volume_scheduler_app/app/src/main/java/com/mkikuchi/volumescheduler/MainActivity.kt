package com.mkikuchi.volumescheduler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.work.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = GroupAdapter<ViewHolder>()
        val section = Section()
        section.add(SettingItem("8:00", "月火水"))
        adapter.add(section)

        settingItemRecyclerView.adapter = adapter

        val volumeController = PeriodicWorkRequest
            .Builder(
                VolumeController::class.java,
                15, TimeUnit.MINUTES)
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(this).enqueue(volumeController)
        WorkManager.getInstance(this).cancelAllWork()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.addSetting -> Log.d("test", "taped")
        }
        return super.onOptionsItemSelected(item)
    }
}
