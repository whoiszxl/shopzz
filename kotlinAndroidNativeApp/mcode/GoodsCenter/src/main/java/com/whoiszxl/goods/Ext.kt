package com.whoiszxl.goods

import android.widget.EditText
import org.jetbrains.anko.find
import ren.qinc.numberbutton.NumberButton
import ren.qinc.numberbutton.R

/*
    三方控件扩展
 */
fun NumberButton.getEditText():EditText{
    return find(R.id.text_count)
}
