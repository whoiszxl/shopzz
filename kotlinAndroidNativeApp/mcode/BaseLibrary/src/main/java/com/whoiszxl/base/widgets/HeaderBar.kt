package com.whoiszxl.base.widgets

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.whoiszxl.base.R
import com.whoiszxl.base.ext.onClick

import kotlinx.android.synthetic.main.layout_header_bar.view.*

class HeaderBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var isShowBack = true
    private var titleText:String? = null
    private var rightText:String? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar)
        isShowBack = typedArray.getBoolean(R.styleable.HeaderBar_isShowBack,true)
        titleText = typedArray.getString(R.styleable.HeaderBar_titleText)
        rightText = typedArray.getString(R.styleable.HeaderBar_rightText)

        initView()
        typedArray.recycle()
    }

    /**
     * 出事啊bar视图
     */
    private fun initView() {
        View.inflate(context, R.layout.layout_header_bar, this)
        mLeftIv.visibility = if (isShowBack) View.VISIBLE else View.GONE
        //标题不为空，设置值
        titleText?.let {
            mTitleTv.text = it
        }
        /**
         * 右侧文字不为空，设置值
         */
        rightText?.let {
            mRightTv.text = it
            mRightTv.visibility = View.VISIBLE
        }

        /**
         * 返回图标默认实现（关闭Activity）
         */
        mLeftIv.onClick {
            if(context is Activity) {
                (context as Activity).finish()
            }
        }
    }

    /**
     * 获取左侧视图
     */
    fun getLeftView(): ImageView {
        return mLeftIv
    }

    /**
     * 获取右侧视图
     */
    fun getRightView():TextView{
        return mRightTv
    }

    /**
     * 获取右侧文字
     */
    fun getRightText():String{
        return mRightTv.text.toString()
    }
}