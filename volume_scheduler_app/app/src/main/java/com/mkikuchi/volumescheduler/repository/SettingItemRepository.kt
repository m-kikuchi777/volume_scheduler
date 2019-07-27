package com.mkikuchi.volumescheduler.repository

import com.mkikuchi.volumescheduler.room.SettingItemDao
import com.mkikuchi.volumescheduler.room.SettingItemEntity

/**
 * 永続化された設定値を操作するためのリポジトリ。
 */
class SettingItemRepository(private val settingItemDao: SettingItemDao) {

    // 保存されているアイテム
    val settingItemList = settingItemDao.getAllItem()

    /**
     * 設定を保存する。
     */
    suspend fun insetSetting(settingItem: SettingItemEntity) {
        settingItemDao.insert(settingItem)
    }

    /**
     * 設定を削除する。
     */
    suspend fun deleteSetting(settingItem: SettingItemEntity) {
        settingItemDao.delete(settingItem)
    }
}