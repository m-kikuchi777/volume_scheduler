package com.mkikuchi.volumescheduler.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * 設定値を保存するためのエンティティ
 */
@Entity(tableName = "setting_item")
data class SettingItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val time: String,
    val sunday: Boolean,
    val monday: Boolean,
    val tuesday: Boolean,
    val wednesday: Boolean,
    val thursday: Boolean,
    val friday: Boolean,
    val saturday: Boolean
)