package com.mkikuchi.volumescheduler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mkikuchi.volumescheduler.repository.SettingItemRepository
import com.mkikuchi.volumescheduler.room.SettingItemDatabase
import com.mkikuchi.volumescheduler.room.SettingItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * アイテムを追加する画面のViewModel。
 */
class AddSettingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SettingItemRepository

    init {
        val database = SettingItemDatabase.getDatabase(application.applicationContext)
        repository = SettingItemRepository(database.getSettingItemDao())
    }

    /**
     * 入力をチェックする。
     */
    fun validate(
        time: String,
        isSunday: Boolean,
        isMonday: Boolean,
        isTuesday: Boolean,
        isWednesday: Boolean,
        isThursday: Boolean,
        isFriday: Boolean,
        isSaturday: Boolean) : String? {

        return if (isSunday || isMonday || isTuesday || isWednesday || isThursday || isFriday || isSaturday ) {
            insert(time, isSunday, isMonday, isTuesday, isWednesday, isThursday, isFriday, isSaturday)
            null
        } else {
            return "曜日は一つ以上選択してください。"
        }
    }

    /**
     * アイテムを追加する。
     */
    private fun insert(
        time: String,
        isSunday: Boolean,
        isMonday: Boolean,
        isTuesday: Boolean,
        isWednesday: Boolean,
        isThursday: Boolean,
        isFriday: Boolean,
        isSaturday: Boolean
    ) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.insetSetting(
                SettingItemEntity(
                    time = time,
                    sunday = isSunday,
                    monday = isMonday,
                    tuesday = isTuesday,
                    wednesday = isWednesday,
                    thursday = isThursday,
                    friday = isFriday,
                    saturday = isSaturday
                )
            )
        }
    }
}