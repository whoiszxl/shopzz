package com.whoiszxl.goods.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kennyc.view.MultiStateView
import com.whoiszxl.base.ext.setVisible
import com.whoiszxl.base.ext.startLoading
import com.whoiszxl.base.ui.adapter.BaseRecyclerViewAdapter
import com.whoiszxl.base.ui.fragment.BaseMvpFragment
import com.whoiszxl.goods.R
import com.whoiszxl.goods.common.GoodsConstant
import com.whoiszxl.goods.data.protocol.Category
import com.whoiszxl.goods.injection.component.DaggerCategoryComponent
import com.whoiszxl.goods.injection.module.CategoryModule
import com.whoiszxl.goods.presenter.CategoryPresenter
import com.whoiszxl.goods.presenter.view.CategoryView
import com.whoiszxl.goods.ui.activity.GoodsActivity
import com.whoiszxl.goods.ui.adapter.SecondCategoryAdapter
import com.whoiszxl.goods.ui.adapter.TopCategoryAdapter
import kotlinx.android.synthetic.main.fragment_category.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * 商品分类 Fragment
 */
class CategoryFragment : BaseMvpFragment<CategoryPresenter>(), CategoryView {

    /**
     * 一级分类适配器
     */
    lateinit var topAdapter: TopCategoryAdapter


    /**
     * 二级分类适配器
     */
    lateinit var secondAdapter: SecondCategoryAdapter


    /**
     * dagger注冊
     */
    override fun injectComponent() {
        DaggerCategoryComponent.builder().activityComponent(mActivityComponent).categoryModule(CategoryModule())
                .build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        //填充布局
        return inflater?.inflate(R.layout.fragment_category, container, false)
    }

    /**
     * onViewCreated在onCreateView执行完后立即执行
     * onCreateView返回的就是fragment要显示的view
     */
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
    }



    /**
     * 初始化fragment视图
     */
    private fun initView() {
        mTopCategoryRv.layoutManager = LinearLayoutManager(context)
        topAdapter = TopCategoryAdapter(context)
        mTopCategoryRv.adapter = topAdapter

        //单项点击事件
        topAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
                for (category in topAdapter.dataList) {
                    //将所有分类遍历，将点击的selected状态设置为true
                    category.isSelected = item.id == category.id
                }
                topAdapter.notifyDataSetChanged()

                loadData(item.id)
            }
        })

        mSecondCategoryRv.layoutManager = GridLayoutManager(context, 3)
        secondAdapter = SecondCategoryAdapter(context)
        mSecondCategoryRv.adapter = secondAdapter
        secondAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
                //startActivity()
                startActivity<GoodsActivity>(GoodsConstant.KEY_CATEGORY_ID to item.id)
            }
        })

    }

    /**
     * 加载数据
     */
    private fun loadData(parentId: Int = 0) {
        if(parentId != 0) {
            mMultiStateView.startLoading()
        }
        mPresenter.getCategory(parentId)
    }

    /**
     * 获取到了商品分类后的回调
     */
    override fun onGetCategoryResult(result: MutableList<Category>?) {
        if(result != null && result.size > 0) {
            if(result[0].parentId ==  0) {
                //将第一个默认选中
                result[0].isSelected = true
                //将第一级分类的数据设置到第一级分类的适配器中
                topAdapter.setData(result)
                //再加载一下这个一级分类下的子分类
                mPresenter.getCategory(result[0].id)
            }else{
                //第二级分类设置数据
                secondAdapter.setData(result)
                //默认为不显示，在这里设置为显示
                mTopCategoryIv.setVisible(true)
                mCategoryTitleTv.setVisible(true)
                mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            }
        }else{
            //没有数据的选项
            mTopCategoryIv.setVisible(false)
            mCategoryTitleTv.setVisible(false)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

}