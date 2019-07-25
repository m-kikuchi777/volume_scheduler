package com.mkikuchi.volumescheduler

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_layout.*

/**
 * 設定済みのアイテムを表示するアイテム。
 */
class SettingItem(
    private val time: String,
    private val dayOfTheWeek: String
) : Item() {

    override fun getLayout() = R.layout.item_layout

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.timeTextView.text = time
        viewHolder.dayOfTheWeekTextView.text = dayOfTheWeek
    }
}