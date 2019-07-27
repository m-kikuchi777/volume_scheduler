package com.mkikuchi.volumescheduler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mkikuchi.volumescheduler.repository.SettingItemRepository
import com.mkikuchi.volumescheduler.room.SettingItemDatabase
import com.mkikuchi.volumescheduler.room.SettingItemEntity

/**
 * 設定値を表示するためのViewModel。
 */
class SettingItemListViewModel(application: Application) : AndroidViewModel(application) {

    // 設定値を操作するためのリポジトリ。
    val settingItemList: LiveData<List<SettingItemEntity>>

    init {
        // 設定値を取得するためのRepositoryを生成する。
        val database = SettingItemDatabase.getDatabase(application.applicationContext)
        settingItemList = SettingItemRepository(database.getSettingItemDao()).settingItemList
    }
}