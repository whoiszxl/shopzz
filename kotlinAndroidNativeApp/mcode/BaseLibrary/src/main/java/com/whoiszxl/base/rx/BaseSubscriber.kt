package com.whoiszxl.base.rx

import android.util.Log
import com.whoiszxl.base.presenter.view.BaseView
import rx.Subscriber

open class BaseSubscriber<T> (val baseView:BaseView): Subscriber<T>() {
    override fun onNext(t: T) {
    }

    override fun onCompleted() {
        baseView.hideLoading()
    }

    override fun onError(e: Throwable?) {
        //關鍵啊，不然排除錯誤太難了
        Log.e("接口調用回調錯誤", e.toString())
        baseView.hideLoading()
        if(e is BaseException) {
            baseView.onError(e.msg)
        }
    }

}