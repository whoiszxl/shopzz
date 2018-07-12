package com.whoiszxl.order.ui.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
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


    private lateinit var imageHost: String

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_order_goods_item,parent,false)
        return ViewHolder(view)
    }

    /**
     * TODO 通过适配器设置一个imageHost地址进来，只会执行一次，不知道会不会对adapter有影响，存疑先
     */
    fun setImageHost(imageHost:String){
        this.imageHost = imageHost
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        holder.itemView.mGoodsIconIv.loadUrl(this.imageHost + model.productImage)
        Log.d("imageHostss",this.imageHost + model.productImage )
        holder.itemView.mGoodsDescTv.text = model.productName
        //TODO sku还有问题哦
        holder.itemView.mGoodsSkuTv.text = "精品"
        holder.itemView.mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(model.currentUnitPrice)
        holder.itemView.mGoodsCountTv.text = "x${model.quantity}"

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
