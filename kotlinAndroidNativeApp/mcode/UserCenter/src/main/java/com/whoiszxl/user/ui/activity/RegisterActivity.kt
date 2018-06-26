package com.whoiszxl.user.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.whoiszxl.user.R
import kotlinx.android.synthetic.main.activity_register.mRegisterBtn
import org.jetbrains.anko.longToast

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mRegisterBtn.setOnClickListener {
            longToast("hello world")
        }
    }
}
