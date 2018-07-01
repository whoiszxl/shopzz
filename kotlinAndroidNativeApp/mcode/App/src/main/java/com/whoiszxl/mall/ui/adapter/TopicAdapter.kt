package com.whoiszxl.mall.ui.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.whoiszxl.base.ext.loadUrl
import com.whoiszxl.mall.R
import kotlinx.android.synthetic.main.layout_topic_item.view.*

/*
    话题数据
 */
class TopicAdapter(private val context: Context, private val list: List<String>) : PagerAdapter() {

    override fun destroyItem(parent: ViewGroup, paramInt: Int, paramObject: Any) {
        parent.removeView(paramObject as View)
    }

    override fun getCount(): Int {
        return this.list.size
    }

    override fun instantiateItem(parent: ViewGroup, position: Int): Any {
        //将需要遍历的item布局加载
        val rooView = LayoutInflater.from(this.context).inflate(R.layout.layout_topic_item, null)
        //通过扩展的loadUrl方法加载列表中的图片
        rooView.mTopicIv.loadUrl(list[position])
        //当前的布局加载到父布局里
        parent.addView(rooView)
        return rooView
    }

    override fun isViewFromObject(paramView: View, paramObject: Any): Boolean {
        return paramView === paramObject
    }
}
