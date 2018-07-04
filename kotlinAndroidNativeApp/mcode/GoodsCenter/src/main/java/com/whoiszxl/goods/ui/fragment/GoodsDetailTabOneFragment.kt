package com.whoiszxl.goods.ui.fragment
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.whoiszxl.base.ext.onClick
import com.whoiszxl.base.ui.activity.BaseActivity
import com.whoiszxl.base.ui.fragment.BaseMvpFragment
import com.whoiszxl.base.utils.YuanFenConverter
import com.whoiszxl.base.widgets.BannerImageLoader
import com.whoiszxl.goods.R
import com.whoiszxl.goods.common.GoodsConstant
import com.whoiszxl.goods.data.protocol.Goods
import com.whoiszxl.goods.event.AddCartEvent
import com.whoiszxl.goods.event.GoodsDetailImageEvent
import com.whoiszxl.goods.event.SkuChangedEvent
import com.whoiszxl.goods.event.UpdateCartSizeEvent
import com.whoiszxl.goods.injection.component.DaggerGoodsComponent
import com.whoiszxl.goods.injection.module.GoodsModule
import com.whoiszxl.goods.presenter.GoodsDetailPresenter
import com.whoiszxl.goods.presenter.view.GoodsDetailView
import com.whoiszxl.goods.ui.activity.GoodsDetailActivity
import com.whoiszxl.goods.widget.GoodsSkuPopView
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_one.*
import org.jetbrains.anko.support.v4.toast
import java.util.*

/**
 * 商品详情Tab One
 */
class GoodsDetailTabOneFragment : BaseMvpFragment<GoodsDetailPresenter>(), GoodsDetailView {
    private lateinit var mSkuPop: GoodsSkuPopView
    //SKU弹层出场动画
    private lateinit var mAnimationStart: Animation
    //SKU弹层退场动画
    private lateinit var mAnimationEnd: Animation

    private var mCurGoods:Goods? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_goods_detail_tab_one, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAnim()
        initSkuPop()
        loadData()
        initObserve()
    }

    /*
        初始化视图
     */
    private fun initView() {
        mGoodsDetailBanner.setImageLoader(BannerImageLoader())
        mGoodsDetailBanner.setBannerAnimation(Transformer.Default)
        mGoodsDetailBanner.setDelayTime(4000)
        //设置指示器位置（当banner模式中有指示器时）
        mGoodsDetailBanner.setIndicatorGravity(BannerConfig.RIGHT)

        //sku弹层
        mSkuView.onClick {
            mSkuPop.showAtLocation((activity as GoodsDetailActivity).contentView
                    , Gravity.BOTTOM and Gravity.CENTER_HORIZONTAL,
                    0, 0
            )
            (activity as BaseActivity).contentView.startAnimation(mAnimationStart)
        }
    }

    /*
      初始化缩放动画
   */
    private fun initAnim() {
        mAnimationStart = ScaleAnimation(
                1f, 0.98f, 1f, 0.98f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mAnimationStart.duration = 300
        mAnimationStart.fillAfter = true

        mAnimationEnd = ScaleAnimation(
                0.98f, 1f, 0.98f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mAnimationEnd.duration = 300
        mAnimationEnd.fillAfter = true
    }

    /*
        初始化sku弹层
     */
    private fun initSkuPop() {
        mSkuPop = GoodsSkuPopView(activity)
        mSkuPop.setOnDismissListener{
            (activity as BaseActivity).contentView.startAnimation(mAnimationEnd)
        }
    }

    /*
        加载数据
     */
    private fun loadData() {
        mPresenter.getGoodsDetailList(activity.intent.getIntExtra(GoodsConstant.KEY_GOODS_ID, -1))
    }

    /*
        Dagger注册
     */
    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)
        mPresenter.mView = this
    }

    /*
        获取商品详情 回调
     */
    override fun onGetGoodsDetailResult(result: Goods) {
        mCurGoods = result

        var bannerList = LinkedList<String>()
        for (subBanner in result.subImages.split(",")) {
            bannerList.add(result.imageHost + subBanner)
        }
        mGoodsDetailBanner.setImages(bannerList)
        mGoodsDetailBanner.start()

        mGoodsDescTv.text = result.name
        mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(result.price)
        mSkuSelectedTv.text = result.name

        //TODO 需要加载webview加载desc图片
        Bus.send(GoodsDetailImageEvent(result.imageHost+result.mainImage, result.imageHost+result.mainImage))

        loadPopData(result)
    }

    /*
        加载SKU数据
     */
    private fun loadPopData(result: Goods) {
        mSkuPop.setGoodsIcon(result.mainImage)
        mSkuPop.setGoodsCode(result.id.toString())
        mSkuPop.setGoodsPrice(result.price)
        mSkuPop.setSkuData(result.skus)

    }

    /*
        监听SKU变化及加入购物车事件
     */
    @SuppressLint("SetTextI18n")
    private fun initObserve(){
        Bus.observe<SkuChangedEvent>()
                .subscribe {
                    mSkuSelectedTv.text = mSkuPop.getSelectSku() +GoodsConstant.SKU_SEPARATOR + mSkuPop.getSelectCount()+"件"
                }.registerInBus(this)

        Bus.observe<AddCartEvent>()
                .subscribe {
                    addCart()
                }.registerInBus(this)
    }

    /*
        取消事件监听
     */
    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    /*
        加入购物车
     */
    private fun addCart(){
//        mCurGoods?.let {
//            mPresenter.addCart(it.id,
//                    it.goodsDesc,
//                    it.goodsDefaultIcon,
//                    it.goodsDefaultPrice,
//                    mSkuPop.getSelectCount(),
//                    mSkuPop.getSelectSku()
//            )
//        }

    }

    /*
        加入购物车 回调
     */
    override fun onAddCartResult(result: Int) {
        Bus.send(UpdateCartSizeEvent())
    }

}