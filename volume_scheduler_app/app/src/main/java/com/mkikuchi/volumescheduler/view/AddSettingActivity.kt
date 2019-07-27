package com.mkikuchi.volumescheduler.view

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.mkikuchi.volumescheduler.R
import com.mkikuchi.volumescheduler.viewmodel.AddSettingViewModel
import kotlinx.android.synthetic.main.activity_add_setting.*
import kotlinx.android.synthetic.main.item_layout.*

class AddSettingActivity : AppCompatActivity() {

    private lateinit var viewModel: AddSettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_setting)
        title = getString(R.string.add_setting_title)

        viewModel = ViewModelProviders.of(this).get(AddSettingViewModel::class.java)

        addSettingButton.setOnClickListener {
            val errorMessage = viewModel.validate(
                settingTimeTextView.text.toString(),
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
                    .setPositiveButton("OK") { dialog, which ->
                        finish()
                    }
                    .show()
            }
        }
    }
}
