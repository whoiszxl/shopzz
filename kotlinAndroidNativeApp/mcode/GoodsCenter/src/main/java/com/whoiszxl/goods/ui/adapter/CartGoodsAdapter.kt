package com.whoiszxl.goods.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.eightbitlab.rxbus.Bus
import com.whoiszxl.base.ext.loadUrl
import com.whoiszxl.base.ext.onClick
import com.whoiszxl.base.ui.adapter.BaseRecyclerViewAdapter
import com.whoiszxl.base.utils.YuanFenConverter
import com.whoiszxl.base.widgets.DefaultTextWatcher
import com.whoiszxl.goods.R
import com.whoiszxl.goods.data.protocol.CartGoods
import com.whoiszxl.goods.event.CartAllCheckedEvent
import com.whoiszxl.goods.event.UpdateTotalPriceEvent
import com.whoiszxl.goods.getEditText
import kotlinx.android.synthetic.main.layout_cart_goods_item.view.*

/*
    购物车数据适配器
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
        model.isChecked = model.productChecked == 1
        //是否选中
        holder.itemView.mCheckedCb.isChecked = model.isChecked
        //加载商品图片
        holder.itemView.mGoodsIconIv.loadUrl(model.productMainImage)
        //商品描述
        holder.itemView.mGoodsDescTv.text = model.productName
        //商品SKU
        holder.itemView.mGoodsSkuTv.text = "精品"
        //商品价格
        holder.itemView.mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(model.productTotalPrice)
        //商品数量
        holder.itemView.mGoodsCountBtn.setCurrentNumber(model.quantity)
        //选中按钮事件
        holder.itemView.mCheckedCb.onClick {
            model.isChecked = holder.itemView.mCheckedCb.isChecked
            val isAllChecked = dataList.all {it.isChecked }
            Bus.send(CartAllCheckedEvent(isAllChecked))
            notifyDataSetChanged()
        }

        //商品数量变化监听
        holder.itemView.mGoodsCountBtn.getEditText().addTextChangedListener(object:DefaultTextWatcher(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                model.quantity = s.toString().toInt()
                Bus.send(UpdateTotalPriceEvent())
            }
        })

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
