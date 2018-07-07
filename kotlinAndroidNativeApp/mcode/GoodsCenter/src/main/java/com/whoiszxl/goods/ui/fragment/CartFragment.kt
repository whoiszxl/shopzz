package com.whoiszxl.goods.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kennyc.view.MultiStateView
import com.whoiszxl.base.ext.onClick
import com.whoiszxl.base.ext.setVisible
import com.whoiszxl.base.ext.startLoading
import com.whoiszxl.base.ui.fragment.BaseMvpFragment
import com.whoiszxl.base.utils.AppPrefsUtils
import com.whoiszxl.base.utils.YuanFenConverter
import com.whoiszxl.goods.R
import com.whoiszxl.goods.common.GoodsConstant
import com.whoiszxl.goods.data.protocol.Cart
import com.whoiszxl.goods.data.protocol.CartGoods
import com.whoiszxl.goods.event.CartAllCheckedEvent
import com.whoiszxl.goods.event.CartSingleCheckedEvent
import com.whoiszxl.goods.event.UpdateCartSizeEvent
import com.whoiszxl.goods.event.UpdateTotalPriceEvent
import com.whoiszxl.goods.injection.component.DaggerCartComponent
import com.whoiszxl.goods.injection.module.CartModule
import com.whoiszxl.goods.presenter.CartListPresenter
import com.whoiszxl.goods.presenter.view.CartListView
import com.whoiszxl.goods.ui.adapter.CartGoodsAdapter
import com.whoiszxl.provider.common.ProviderConstant
import com.whoiszxl.provider.router.RouterPath
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.layout_cart_goods_item.*
import org.jetbrains.anko.support.v4.toast

/**
 * 购物车 Fragment
 */
class CartFragment : BaseMvpFragment<CartListPresenter>(), CartListView {


    private lateinit var mAdapter: CartGoodsAdapter

    private var mTotalPrice: Long = 0

    /**
     * Dagger注册
     */
    override fun injectComponent() {
        DaggerCartComponent.builder().activityComponent(mActivityComponent).cartModule(CartModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_cart, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toasty.error(context, "调用了cart",Toast.LENGTH_SHORT).show()
        initView()
        initClick()
        initObserve()
    }

    /**
     * 加载数据
     */
    override fun onStart() {
        super.onStart()
        loadData()
    }

    /**
     * 初始化视图和事件
     */
    private fun initView() {
        mCartGoodsRv.layoutManager = LinearLayoutManager(context)
        mAdapter = CartGoodsAdapter(context)
        mCartGoodsRv.adapter = mAdapter
    }

    /**
     * 初始化各种点击事件
     */
    private fun initClick() {
        mHeaderBar.getRightView().onClick {
            refreshEditStatus()
        }

        //全选按钮事件
        mAllCheckedCb.onClick {
            for (item in mAdapter.dataList) {
                item.productCheckedBoolean = mAllCheckedCb.isChecked
            }
            //mAdapter.notifyDataSetChanged()
            /**
             * 调用全选接口
             * 当前这里是onClick点击后的状态，所以isChecked是选中状态的话
             * 那就要执行选中状态的接口
             */
            if(mAllCheckedCb.isChecked) {
                mPresenter.selectCartAll()
            }else{
                mPresenter.selectUnCartAll()
            }
        }

        //删除按钮事件
        mDeleteBtn.onClick {

            var cartIdListStr = StringBuilder()
            mAdapter.dataList.forEach {
                if(it.productCheckedBoolean){
                    cartIdListStr.append(it.productId).append(",")
                }
            }

            cartIdListStr.deleteCharAt(cartIdListStr.length - 1)

            if (cartIdListStr == null) {
                toast("请选择需要删除的数据")
            } else {
                mPresenter.deleteCartList(cartIdListStr.toString())
            }
        }

        //结算按钮事件
        mSettleAccountsBtn.onClick {
            val cartGoodsList: MutableList<CartGoods> = arrayListOf()
            mAdapter.dataList.filter { it.productCheckedBoolean }
                    .mapTo(cartGoodsList) { it }
            if (cartGoodsList.size == 0) {
                toast("请选择需要提交的数据")
            } else {
                ///mPresenter.submitCart(cartGoodsList,mTotalPrice)
            }
        }
    }

    /*
        刷新是否为编辑状态
     */
    private fun refreshEditStatus() {
        val isEditStatus = getString(R.string.common_edit) == mHeaderBar.getRightText()
        mTotalPriceTv.setVisible(isEditStatus.not())
        mSettleAccountsBtn.setVisible(isEditStatus.not())
        mDeleteBtn.setVisible(isEditStatus)

        mHeaderBar.getRightView().text = if (isEditStatus) getString(R.string.common_complete) else getString(R.string.common_edit)


    }

    /*
        加载数据
     */
    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getCartList()
    }

    /*
        获取购物车列表回调
     */
    override fun onGetCartListResult(result: Cart?) {
        if (result != null && result.cartProductVoList.isNotEmpty()) {
            mAdapter.setData(result.cartProductVoList)
            mHeaderBar.getRightView().setVisible(true)
            mTotalPriceTv.text = "合计:${YuanFenConverter.changeF2YWithUnit(result.cartTotalPrice)}"
            mAllCheckedCb.isChecked = false
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mHeaderBar.getRightView().setVisible(false)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }

        //本地存储并发送事件刷新UI
        if (result != null) {
            AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE, result.cartProductVoList.size)
        }
        Bus.send(UpdateCartSizeEvent())
        //更新总价
        //updateTotalPrice()
        //不使用本地的计算，直接使用接口返回来的总价
        updateAllCartNumOfApi()
    }

    /*
        注册监听
     */
    private fun initObserve() {
        //选择所有  购物车商品事件
        Bus.observe<CartAllCheckedEvent>().subscribe { t: CartAllCheckedEvent ->
//            run {
//                mAllCheckedCb.isChecked = t.isAllChecked
//                //调用接口选中or不选中所有
//                if(t.isAllChecked) {
//                    mPresenter.selectUnCartAll()
//                }else{
//                    mPresenter.selectCartAll()
//                }
//                updateTotalPrice()
//            }

            //从单选这里判断是不是全部选中了，需要改变全选checkbox的状态
            run {
                mAllCheckedCb.isChecked = t.isAllChecked
            }
        }.registerInBus(this)

        // 更新总价
        Bus.observe<UpdateTotalPriceEvent>().subscribe {
            //updateTotalPrice()
            //updateAllCartNumOfApi()
            updateSingleCartNumOfApi(it.count, it.productId)
        }
                .registerInBus(this)

        //单个选中 回调
        Bus.observe<CartSingleCheckedEvent>().subscribe{ t: CartSingleCheckedEvent ->
            run {
                //调用单选接口
                if(t.isChecked) {
                    mPresenter.selectUnCartOne(t.productId)
                }else {
                    mPresenter.selectCartOne(t.productId)
                }
            }
        }
                .registerInBus(this)
    }

    /*
        取消监听
     */
    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    /*
        更新总价
     */
    @SuppressLint("SetTextI18n")
    private fun updateTotalPrice() {
        mTotalPrice = mAdapter.dataList
                .filter { it.productCheckedBoolean }
                .map { it.quantity * it.productPrice }
                .sum()

        mTotalPriceTv.text = "合计:${YuanFenConverter.changeF2YWithUnit(mTotalPrice)}"


    }


    override fun onSelectOrUnSelectResult(result: Long) {
        mTotalPriceTv.text = "合计:${YuanFenConverter.changeF2YWithUnit(result)}"
    }

    /**
     * 更新所有在数据库中购物车商品的数量
     */
    private fun updateAllCartNumOfApi() {
        for (it in mAdapter.dataList) {
            mPresenter.updateCartNum(it.quantity, it.productId)
        }
    }

    private fun updateSingleCartNumOfApi(count:Int, productId:Int) {
        mPresenter.updateCartNum(count, productId)
    }

    /*
        删除购物车回调
     */
    override fun onDeleteCartListResult(result: Boolean) {
        toast("删除成功")
        refreshEditStatus()
        loadData()
    }

    /*
        提交购物车回调
     */
    override fun onSubmitCartListResult(result: Int) {
        ARouter.getInstance().build(RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
                .withInt(ProviderConstant.KEY_ORDER_ID, result)
                .navigation()
    }

    /*
        设置Back是否可见
     */
    fun setBackVisible(isVisible: Boolean) {
        mHeaderBar.getLeftView().setVisible(isVisible)
    }

    override fun onResume() {
        super.onResume()
        mAdapter.notifyDataSetChanged()
    }
}
