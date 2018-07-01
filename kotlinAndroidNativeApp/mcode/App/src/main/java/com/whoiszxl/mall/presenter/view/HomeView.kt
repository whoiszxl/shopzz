package com.whoiszxl.mall.presenter.view

import com.whoiszxl.base.presenter.view.BaseView
import com.whoiszxl.mall.data.protocol.Banner

interface HomeView:BaseView {

    fun onBannerResult(t:MutableList<Banner>?)
}