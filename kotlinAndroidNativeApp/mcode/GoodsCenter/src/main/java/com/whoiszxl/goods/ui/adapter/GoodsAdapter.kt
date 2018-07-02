package com.whoiszxl.goods.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.whoiszxl.base.ext.loadUrl
import com.whoiszxl.base.ui.adapter.BaseRecyclerViewAdapter
import com.whoiszxl.base.utils.YuanFenConverter
import com.whoiszxl.goods.R
import com.whoiszxl.goods.data.protocol.Goods
import kotlinx.android.synthetic.main.layout_goods_item.view.*

/**
 * 商品数据适配器
 */
class GoodsAdapter(context: Context) : BaseRecyclerViewAdapter<Goods, GoodsAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_goods_item,
                        parent,
                        false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        //商品图标
        holder.itemView.mGoodsIconIv.loadUrl(model.imageHost+model.mainImage)
        //商品描述
        holder.itemView.mGoodsDescTv.text = model.name
        //商品价格
        holder.itemView.mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(model.price)
        //商品销量及库存
        holder.itemView.mGoodsSalesStockTv.text = "销量${model.goodsSalesCount}件        库存${model.stock}"
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
