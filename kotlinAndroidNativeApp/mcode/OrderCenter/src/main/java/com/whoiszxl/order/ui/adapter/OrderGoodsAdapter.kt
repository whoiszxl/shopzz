package com.whoiszxl.order.ui.adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.whoiszxl.base.ext.loadUrl
import com.whoiszxl.base.ui.adapter.BaseRecyclerViewAdapter
import com.whoiszxl.order.R
import com.whoiszxl.order.data.protocol.OrderGoods
import com.whoiszxl.base.utils.YuanFenConverter
import kotlinx.android.synthetic.main.layout_order_goods_item.view.*

/*
    订单中商品列表
 */
class OrderGoodsAdapter(context: Context) : BaseRecyclerViewAdapter<OrderGoods, OrderGoodsAdapter.ViewHolder>(context) {


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_order_goods_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        holder.itemView.mGoodsIconIv.loadUrl(model.productImage)
        holder.itemView.mGoodsDescTv.text = model.productName
        //TODO sku还有问题哦
        holder.itemView.mGoodsSkuTv.text = "精品"
        holder.itemView.mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(model.currentUnitPrice)
        holder.itemView.mGoodsCountTv.text = "x${model.quantity}"

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
