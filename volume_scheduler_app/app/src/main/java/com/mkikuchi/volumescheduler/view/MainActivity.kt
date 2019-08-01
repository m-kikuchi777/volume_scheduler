package com.mkikuchi.volumescheduler.view

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SimpleAdapter
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.mkikuchi.volumescheduler.R
import com.mkikuchi.volumescheduler.data.SettingItem
import com.mkikuchi.volumescheduler.data.SwipeToDeleteCallback
import com.mkikuchi.volumescheduler.room.SettingItemEntity
import com.mkikuchi.volumescheduler.utils.VolumeController
import com.mkikuchi.volumescheduler.viewmodel.SettingItemListViewModel
import com.xwray.groupie.GroupAdapter
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
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(this) {

            private lateinit var deleteItem: SettingItem
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)

                // 削除確認ダイアログを表示する。
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(R.string.delete_confirm_message)
                    .setPositiveButton("OK", positiveAction)
                    .setNegativeButton("Cancel", negativeAction)
                    .show()
                // スワイプされたアイテムを取得する。
                deleteItem = adapter.getItem(viewHolder.adapterPosition) as SettingItem
            }

            // 削除確認ダイアログのOKボタンタップ時の動作
            val positiveAction = DialogInterface.OnClickListener { _, _ ->
                // アイテムを削除する。
                viewModel.delete(deleteItem.settingItemEntity)
            }

            // 削除確認ダイアログのCancelボタンタップ時の動作
            val negativeAction = DialogInterface.OnClickListener { _, _ ->
                // スワイプしたアイテムを戻す。
                adapter.notifyDataSetChanged()
            }
        }
        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(settingItemRecyclerView)

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
