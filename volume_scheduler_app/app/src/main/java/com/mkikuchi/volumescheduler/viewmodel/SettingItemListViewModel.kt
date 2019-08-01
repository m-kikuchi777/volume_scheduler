package com.mkikuchi.volumescheduler.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.mkikuchi.volumescheduler.data.SettingItem
import com.mkikuchi.volumescheduler.repository.SettingItemRepository
import com.mkikuchi.volumescheduler.room.SettingItemDatabase
import com.mkikuchi.volumescheduler.room.SettingItemEntity
import com.xwray.groupie.Section
import kotlinx.coroutines.launch

/**
 * 設定値を表示するためのViewModel。
 */
class SettingItemListViewModel(application: Application) : AndroidViewModel(application) {

    // 設定値を操作するためのリポジトリ。
    private val repository: SettingItemRepository
    val settingItemList: LiveData<List<SettingItemEntity>>
    val settingItemSection: LiveData<Section>

    init {
        // 設定値を取得するためのRepositoryを生成する。
        val database = SettingItemDatabase.getDatabase(application.applicationContext)
        repository = SettingItemRepository(database.getSettingItemDao())
        settingItemList = repository.settingItemList

        settingItemSection = Transformations.switchMap(settingItemList) {

            val section = Section()
            for (settingItemEntity in it) {
                section.add(SettingItem(settingItemEntity))
            }

            MutableLiveData(section)
        }
    }

    /**
     * アイテムを削除する。
     */
    fun delete(settingItemEntity: SettingItemEntity) = viewModelScope.launch {
        repository.deleteSetting(settingItemEntity)
    }
}