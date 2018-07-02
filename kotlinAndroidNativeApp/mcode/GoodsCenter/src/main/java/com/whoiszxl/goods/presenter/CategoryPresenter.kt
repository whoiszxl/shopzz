package com.whoiszxl.goods.presenter

import com.whoiszxl.base.ext.execute
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.goods.data.protocol.Category
import com.whoiszxl.goods.presenter.view.CategoryView
import com.whoiszxl.goods.service.CategoryService
import javax.inject.Inject

/**
 * 商品分类 Presenter
 */
class CategoryPresenter @Inject constructor() : BasePresenter<CategoryView>() {

    @Inject
    lateinit var categoryService: CategoryService


    /*
        获取商品分类列表
     */
    fun getCategory(parentId:Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        categoryService.getCategory(parentId).execute(object : BaseSubscriber<MutableList<Category>?>(mView) {
            override fun onNext(t: MutableList<Category>?) {
                mView.onGetCategoryResult(t)
            }
        }, lifecycleProvider)

    }

}