package com.mkikuchi.volumescheduler.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.*
import com.mkikuchi.volumescheduler.R
import com.mkikuchi.volumescheduler.data.SettingItem
import com.mkikuchi.volumescheduler.utils.VolumeController
import com.mkikuchi.volumescheduler.viewmodel.SettingItemListViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingItemListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = ""

        viewModel = ViewModelProviders.of(this).get(SettingItemListViewModel::class.java)

        val adapter = GroupAdapter<ViewHolder>()
        settingItemRecyclerView.adapter = adapter

        viewModel.settingItemSection.observe(this, Observer {
            adapter.clear()
            adapter.add(it)
        })


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
            R.id.addSetting ->
                startActivity(Intent(this, AddSettingActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}
