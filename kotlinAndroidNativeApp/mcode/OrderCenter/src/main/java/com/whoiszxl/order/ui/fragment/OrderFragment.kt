package com.whoiszxl.order.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.kennyc.view.MultiStateView
import com.whoiszxl.base.ext.startLoading
import com.whoiszxl.base.ui.adapter.BaseRecyclerViewAdapter
import com.whoiszxl.base.ui.fragment.BaseFragment
import com.whoiszxl.base.ui.fragment.BaseMvpFragment
import com.whoiszxl.order.R
import com.whoiszxl.order.common.OrderConstant
import com.whoiszxl.order.data.protocol.Order
import com.whoiszxl.order.data.protocol.OrderList
import com.whoiszxl.order.injection.component.DaggerOrderComponent
import com.whoiszxl.order.injection.module.OrderModule
import com.whoiszxl.order.presenter.OrderListPresenter
import com.whoiszxl.order.presenter.view.OrderListView
import com.whoiszxl.order.ui.adapter.OrderAdapter
import com.whoiszxl.provider.common.ProviderConstant
import com.whoiszxl.provider.router.RouterPath
import org.jetbrains.anko.support.v4.toast
import kotlinx.android.synthetic.main.fragment_order.*

class OrderFragment:BaseMvpFragment<OrderListPresenter>(),OrderListView {

    private lateinit var mAdapter:OrderAdapter

    /*
        Dagger注册
     */
    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(mActivityComponent).orderModule(OrderModule()).build().inject(this)
        mPresenter.mView = this
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_order,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    /*
        初始化视图
     */
    private fun initView() {
        mOrderRv.layoutManager = LinearLayoutManager(activity)
        mAdapter = OrderAdapter(activity)
        mOrderRv.adapter = mAdapter

        /*
            订单对应操作
         */
        mAdapter.listener = object :OrderAdapter.OnOptClickListener {
            override fun onOptClick(optType: Int, order: Order) {
                when(optType){
                    OrderConstant.OPT_ORDER_PAY -> {
                        ARouter.getInstance().build(RouterPath.PaySDK.PATH_PAY)
                                .withString(ProviderConstant.KEY_ORDER_ID,order.orderNo)
                                .withLong(ProviderConstant.KEY_ORDER_PRICE,order.productTotalPrice)
                                .navigation()
                    }
                    OrderConstant.OPT_ORDER_CONFIRM -> {
                        //mPresenter.confirmOrder(order.orderNo)
                    }
                    OrderConstant.OPT_ORDER_CANCEL -> {
                        //mPresenter.cancelOrder(order.id)
                        showCancelDialog(order)
                    }
                }
            }
        }

        /*
            列表单项点击事件
         */
        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Order> {
            override fun onItemClick(item: Order, position: Int) {
                //startActivity<OrderDetailActivity>(ProviderConstant.KEY_ORDER_ID to item.id)
            }
        })


    }

    /*
        取消订单对话框
     */
    private fun showCancelDialog(order:Order) {
        AlertView("取消订单", "确定取消该订单？", "取消", null, arrayOf("确定"), activity, AlertView.Style.Alert, OnItemClickListener { o, position ->
            if (position == 0){
                mPresenter.cancelOrder(order.orderNo)
            }
        }

        ).show()
    }

    /*
        加载数据
     */
    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getOrderList(arguments.getInt(OrderConstant.KEY_ORDER_STATUS,-1))
    }

    /*
        获取订单列表回调
     */
    override fun onGetOrderListResult(result: OrderList) {
        if (result.list != null && result.list.size > 0){
            mAdapter.setImageHost(result.list[0].imageHost)
            mAdapter.setData(result.list)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        }else{
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    /*
        订单确认收货回调
     */
    override fun onConfirmOrderResult(result: Boolean) {
        toast("确认收货成功")
        loadData()
    }

    /*
        取消订单回调
     */
    override fun onCancelOrderResult(result: Boolean) {
        toast("取消订单成功")
        loadData()
    }
}