package com.mkikuchi.volumescheduler.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * 設定値を取得するためのDAO。
 */
@Dao
interface SettingItemDao {

    @Query("SELECT * FROM setting_item")
    fun getAllItem(): LiveData<List<SettingItemEntity>>

    @Insert
    suspend fun insert(settingItemEntity: SettingItemEntity)

    @Delete
    suspend fun delete(settingItemEntity: SettingItemEntity)
}