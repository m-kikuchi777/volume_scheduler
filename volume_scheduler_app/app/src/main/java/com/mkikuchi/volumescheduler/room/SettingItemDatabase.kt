package com.mkikuchi.volumescheduler.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * 設定値を保存するアイテムのデータベース。
 */
@Database(entities = [SettingItemEntity::class], version = 1)
abstract class SettingItemDatabase : RoomDatabase() {
    abstract fun getSettingItemDao(): SettingItemDao

    companion object {
        // インスタンス
        @Volatile
        private var INSTANCE: SettingItemDatabase? = null

        fun getDatabase(context: Context): SettingItemDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                // INSTANCEがnullの場合はRoomDatabaseを生成する。
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SettingItemDatabase::class.java,
                    "setting_item_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}