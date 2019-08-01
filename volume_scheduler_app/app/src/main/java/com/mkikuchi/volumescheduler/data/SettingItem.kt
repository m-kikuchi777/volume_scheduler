package com.mkikuchi.volumescheduler.data

import androidx.recyclerview.widget.ItemTouchHelper
import com.mkikuchi.volumescheduler.R
import com.mkikuchi.volumescheduler.room.SettingItemEntity
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_layout.*

/**
 * 設定済みのアイテムを表示するアイテム。
 */
class SettingItem(
    val settingItemEntity: SettingItemEntity
) : Item() {

    // 設定済みアイテムのレイアウト
    override fun getLayout() = R.layout.item_layout

    // 設定済みアイテムに値を設定する。
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.timeTextView.text = settingItemEntity.time
        viewHolder.dayOfTheWeekTextView.text = settingItemEntity.dayOfWeek
    }

    // 設定済みアイテムをスワイプする向き。
    override fun getSwipeDirs(): Int {
        return ItemTouchHelper.LEFT
    }
}