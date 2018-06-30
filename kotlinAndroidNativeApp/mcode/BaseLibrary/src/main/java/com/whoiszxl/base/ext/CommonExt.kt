package com.whoiszxl.base.ext

import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.kennyc.view.MultiStateView
import com.trello.rxlifecycle.LifecycleProvider
import com.whoiszxl.base.R
import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.base.rx.BaseFunc
import com.whoiszxl.base.rx.BaseFuncBoolean
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.base.utils.GlideUtils
import com.whoiszxl.base.widgets.DefaultTextWatcher
import org.jetbrains.anko.find
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * 扩展Observable执行
 */
fun <T> Observable<T>.execute(subscriber: BaseSubscriber<T>, lifecycleProvider: LifecycleProvider<*>) {
    this.observeOn(AndroidSchedulers.mainThread())
            .compose(lifecycleProvider.bindToLifecycle())
            .subscribeOn(Schedulers.io())
            .subscribe(subscriber)
}

/**
 * 扩展数据转换
 */
fun <T> Observable<BaseResp<T>>.convert():Observable<T>{
    return this.flatMap(BaseFunc())
}

/**
 * 扩展Boolean类型数据转换
 */
fun <T> Observable<BaseResp<T>>.convertBoolean():Observable<Boolean>{
    return this.flatMap(BaseFuncBoolean())
}

/**
 * 扩展点击事件
 */
fun View.onClick(listener: View.OnClickListener) {
    this.setOnClickListener(listener)
}

/**
 * 扩展点击事件，参数为方法
 */
fun View.onClick(method: () -> Unit) {
    this.setOnClickListener { method() }
}

/**
 * 扩展Button可用性
 */
fun Button.enable(et:EditText, method: () -> Boolean){

    val btn = this
    et.addTextChangedListener(object : DefaultTextWatcher(){
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}

/**
 * ImageView加载网络图片
 */
fun ImageView.loadUrl(url: String) {
    GlideUtils.loadUrlImage(context, url, this)
}

/*
    多状态视图开始加载
 */
fun MultiStateView.startLoading(){
    viewState = MultiStateView.VIEW_STATE_LOADING
    val loadingView = getView(MultiStateView.VIEW_STATE_LOADING)
    val animBackground = loadingView!!.find<View>(R.id.loading_anim_view).background
    (animBackground as AnimationDrawable).start()
}

/*
    扩展视图可见性
 */
fun View.setVisible(visible:Boolean){
    this.visibility = if (visible) View.VISIBLE else View.GONE
}
