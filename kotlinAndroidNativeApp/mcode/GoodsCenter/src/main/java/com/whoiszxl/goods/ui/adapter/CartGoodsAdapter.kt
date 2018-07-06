package com.whoiszxl.goods.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.whoiszxl.base.ext.loadUrl
import com.whoiszxl.base.ext.onClick
import com.whoiszxl.base.ui.adapter.BaseRecyclerViewAdapter
import com.whoiszxl.base.utils.YuanFenConverter
import com.whoiszxl.base.widgets.DefaultTextWatcher
import com.whoiszxl.goods.R
import com.whoiszxl.goods.data.protocol.CartGoods
import com.whoiszxl.goods.event.CartAllCheckedEvent
import com.whoiszxl.goods.event.CartSingleCheckedEvent
import com.whoiszxl.goods.event.UpdateTotalPriceEvent
import com.whoiszxl.goods.getEditText
import kotlinx.android.synthetic.main.layout_cart_goods_item.view.*

/**
 * 购物车数据适配器
 */
class CartGoodsAdapter(context: Context) : BaseRecyclerViewAdapter<CartGoods, CartGoodsAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_cart_goods_item,
                        parent,
                        false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        //是否选中
        holder.itemView.mCheckedCb.isChecked = model.productCheckedBoolean
        //加载商品图片
        holder.itemView.mGoodsIconIv.loadUrl(model.imageHost+model.productMainImage)
        //商品描述
        holder.itemView.mGoodsDescTv.text = model.productName
        //商品SKU
        holder.itemView.mGoodsSkuTv.text = model.productCheckedBoolean.toString()
        //商品价格
        holder.itemView.mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(model.productPrice)
        //商品数量
        holder.itemView.mGoodsCountBtn.setCurrentNumber(model.quantity)
        //选中按钮事件
        holder.itemView.mCheckedCb.onClick {
            //发送单个点击事件，改变一下单个购物车商品的选中状态
            Bus.send(CartSingleCheckedEvent(model.productId, model.productCheckedBoolean))
            //修改当前model item的选中状态为点击后的状态
            model.productCheckedBoolean = holder.itemView.mCheckedCb.isChecked
            //遍历一下可以判断是否全选了
            //val isAllChecked = dataList.all { it.productCheckedBoolean }
            //再发送全选事件到事件回调方法中
            //Bus.send(CartAllCheckedEvent(isAllChecked))
            //通知一下改变数据
            //notifyDataSetChanged()
        }

        //商品数量变化监听
        holder.itemView.mGoodsCountBtn.getEditText().addTextChangedListener(object:DefaultTextWatcher(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                model.quantity = s.toString().toInt()
                Bus.send(UpdateTotalPriceEvent(s.toString().toInt(), model.productId))
            }
        })

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
