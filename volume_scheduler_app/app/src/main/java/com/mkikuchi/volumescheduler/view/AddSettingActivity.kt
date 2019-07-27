package com.mkikuchi.volumescheduler.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mkikuchi.volumescheduler.R
import kotlinx.android.synthetic.main.activity_add_setting.*

class AddSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_setting)
        setTitle(R.string.add_setting_title)

        addSettingButton.setOnClickListener {
            finish()
        }
    }
}
