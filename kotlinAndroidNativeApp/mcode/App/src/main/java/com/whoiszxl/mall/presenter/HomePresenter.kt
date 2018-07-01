package com.whoiszxl.mall.presenter

import com.whoiszxl.base.ext.execute
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.mall.data.protocol.Banner
import com.whoiszxl.mall.presenter.view.HomeView
import com.whoiszxl.mall.service.HomeService
import javax.inject.Inject

class HomePresenter @Inject constructor():BasePresenter<HomeView>() {

    @Inject
    lateinit var homeService: HomeService

    fun getBanner(){
        print("开始执行了getBanner")

        homeService.getBanner()
                .execute(object : BaseSubscriber<MutableList<Banner>?>(mView){
                    override fun onNext(t: MutableList<Banner>?) {
                        mView.onBannerResult(t)
                    }
                },lifecycleProvider)
    }

}