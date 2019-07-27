package com.mkikuchi.volumescheduler.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.mkikuchi.volumescheduler.data.SettingItem
import com.mkikuchi.volumescheduler.repository.SettingItemRepository
import com.mkikuchi.volumescheduler.room.SettingItemDatabase
import com.mkikuchi.volumescheduler.room.SettingItemEntity
import com.xwray.groupie.Section

/**
 * 設定値を表示するためのViewModel。
 */
class SettingItemListViewModel(application: Application) : AndroidViewModel(application) {

    // 設定値を操作するためのリポジトリ。
    val settingItemList: LiveData<List<SettingItemEntity>>
    val settingItemSection: LiveData<Section>

    init {
        // 設定値を取得するためのRepositoryを生成する。
        val database = SettingItemDatabase.getDatabase(application.applicationContext)
        settingItemList = SettingItemRepository(database.getSettingItemDao()).settingItemList

        settingItemSection = Transformations.switchMap(settingItemList) {

            val section = Section()
            for (settingItemEntity in it) {
                section.add(settingItemEntity.toConvertSettingItem())
            }

            MutableLiveData(section)
        }
    }
}