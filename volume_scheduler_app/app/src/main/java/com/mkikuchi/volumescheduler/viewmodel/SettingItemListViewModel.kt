package com.mkikuchi.volumescheduler.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.mkikuchi.volumescheduler.data.SettingItem
import com.mkikuchi.volumescheduler.repository.SettingItemRepository
import com.mkikuchi.volumescheduler.room.SettingItemDatabase
import com.mkikuchi.volumescheduler.room.SettingItemEntity
import com.mkikuchi.volumescheduler.utils.VolumeController
import com.soywiz.klock.DateTime
import com.soywiz.klock.hours
import com.xwray.groupie.Section
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * 設定値を表示するためのViewModel。
 */
class SettingItemListViewModel(application: Application) : AndroidViewModel(application) {

    // 設定値を操作するためのリポジトリ。
    private val repository: SettingItemRepository
    private val settingItemList: LiveData<List<SettingItemEntity>>
    val settingItemSection: LiveData<Section>
    private val workManager: WorkManager

    init {
        // 設定値を取得するためのRepositoryを生成する。
        val database = SettingItemDatabase.getDatabase(application.applicationContext)
        repository = SettingItemRepository(database.getSettingItemDao())
        settingItemList = repository.settingItemList

        settingItemSection = Transformations.switchMap(settingItemList) {

            val section = Section()
            for (settingItemEntity in it) {
                setVolumeController(settingItemEntity)
                section.add(SettingItem(settingItemEntity))
            }

            MutableLiveData(section)
        }

        workManager = WorkManager.getInstance(application)
    }

    /**
     * VolumeControllerを設定する。
     */
    private fun setVolumeController(settingItemEntity: SettingItemEntity) {

        // 初期遅延時間(分)を取得する。
        val initialDelayMinutes = getInitialDelayTime(settingItemEntity.time)

        // ボリュームコントロールを行うタイミングをスケジュールする。
        val volumeController = PeriodicWorkRequest
            .Builder(
                VolumeController::class.java,
                24, TimeUnit.HOURS)
            .setInitialDelay(initialDelayMinutes, TimeUnit.MINUTES)
            .addTag(settingItemEntity.id.toString())
            .build()
        // ワークマネージャーを実行する。
        workManager.enqueueUniquePeriodicWork(
            settingItemEntity.id.toString(),
            ExistingPeriodicWorkPolicy.KEEP,
            volumeController)
    }

    /**
     * 最初の遅延タイムを取得する。
     */
    private fun getInitialDelayTime(time: String): Long {
        val nowDate = DateTime.nowLocal() + 9.hours

        val times = time.split(':')
        val hour = times[0].toInt()
        val minute = times[1].toInt()

        val tmpDate = DateTime.createClamped(
            nowDate.yearInt,
            nowDate.month1,
            nowDate.dayOfMonth,
            hour,
            minute).localUnadjusted

        val diffMinutes = (nowDate - tmpDate).minutes.toLong()

        return if (diffMinutes > 0) {
            24 * 60 - diffMinutes
        } else {
            -1 * diffMinutes
        }
    }

    /**
     * アイテムを削除する。
     */
    fun delete(settingItemEntity: SettingItemEntity) = viewModelScope.launch {
        repository.deleteSetting(settingItemEntity)
        workManager.cancelAllWorkByTag(settingItemEntity.id.toString())
    }
}