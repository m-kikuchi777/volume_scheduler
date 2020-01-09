package com.mkikuchi.volumescheduler.view

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.mkikuchi.volumescheduler.R
import com.mkikuchi.volumescheduler.viewmodel.AddSettingViewModel
import kotlinx.android.synthetic.main.activity_add_setting.*

class AddSettingActivity : AppCompatActivity() {

    private lateinit var viewModel: AddSettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_setting)
        title = getString(R.string.add_setting_title)

        viewModel = ViewModelProviders.of(this).get(AddSettingViewModel::class.java)

        var hour = 8
        var minute = 0
        settingTimeTextView.setOnClickListener {
            // 時刻を設定するダイアログを表示する。
            TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, h, m ->
                    // 設定された時刻をテキストビューにセットする。
                    settingTimeTextView.text = String.format("%d:%02d", h, m)
                    hour = h
                    minute = m
                },
                hour,
                minute,
                true
            ).show()
        }

        addSettingButton.setOnClickListener {
            val errorMessage = viewModel.validate(
                isSunday = sundayCheckBox.isChecked,
                isMonday = mondayCheckBox.isChecked,
                isTuesday = tuesdayCheckBox.isChecked,
                isWednesday = wednesdayCheckBok.isChecked,
                isThursday = thursdayCheckBox.isChecked,
                isFriday = fridayCheckBox.isChecked,
                isSaturday = saturdayCheckBox.isChecked
            )

            if (errorMessage != null) {
                AlertDialog.Builder(this)
                    .setMessage(errorMessage)
                    .setPositiveButton("OK", null)
                    .show()
            } else {
                AlertDialog.Builder(this)
                    .setMessage("設定しました。")
                    .setPositiveButton("OK") { _, _ ->
                        // 保存する。
                        viewModel.insert(
                            settingTimeTextView.text.toString(),
                            isSunday = sundayCheckBox.isChecked,
                            isMonday = mondayCheckBox.isChecked,
                            isTuesday = tuesdayCheckBox.isChecked,
                            isWednesday = wednesdayCheckBok.isChecked,
                            isThursday = thursdayCheckBox.isChecked,
                            isFriday = fridayCheckBox.isChecked,
                            isSaturday = saturdayCheckBox.isChecked)
                        finish()
                    }
                    .show()
            }
        }
    }
}
