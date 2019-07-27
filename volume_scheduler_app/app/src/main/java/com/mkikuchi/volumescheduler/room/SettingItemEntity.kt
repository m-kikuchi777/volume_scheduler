package com.mkikuchi.volumescheduler.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mkikuchi.volumescheduler.data.SettingItem
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
) {
    fun toConvertSettingItem(): SettingItem {
        var dayOfWeek = ""
        if (sunday) {
            dayOfWeek += "日、"
        }
        if (monday) {
            dayOfWeek += "月、"
        }
        if (tuesday) {
            dayOfWeek += "火、"
        }
        if (wednesday) {
            dayOfWeek += "水、"
        }
        if (thursday) {
            dayOfWeek += "木、"
        }
        if (friday) {
            dayOfWeek += "金、"
        }
        if (saturday) {
            dayOfWeek += "土、"
        }

        // 最後についている"、"を取り除く
        dayOfWeek = dayOfWeek.dropLast(1)

        return SettingItem(time, dayOfWeek)
    }
}