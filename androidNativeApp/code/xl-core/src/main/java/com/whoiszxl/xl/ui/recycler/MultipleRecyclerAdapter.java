package com.whoiszxl.xl.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.whoiszxl.xl.R;
import com.whoiszxl.xl.ui.banner.BannerCreator;

import java.util.ArrayList;
import java.util.List;

public class MultipleRecyclerAdapter
        extends BaseMultiItemQuickAdapter<MultipleItemEntity,MultipleViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup,OnItemClickListener{

    //确保只会初始化一次Banner，防止划上滑下重复加载
    private boolean mIsInitBanner = false;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .centerCrop();

    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerAdapter(data);
    }

    /**
     * 拿到数据转换器调用convert会返回json对应的实体集合
     * 然后会将这个实体数据传递到当前类的父类的Adapter中
     * @param converter
     * @return
     */
    public static MultipleRecyclerAdapter create(DataConverter converter) {
        return new MultipleRecyclerAdapter(converter.convert());
    }


    /**
     * 初始化
     */
    private void init() {
        //设置不同的item布局，将每种布局的xml都添加到适配器中
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE, R.layout.item_multiple_image);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_image_text);
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner);

        //设置宽度监听
        setSpanSizeLookup(this);
        openLoadAnimation();

        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    /**
     * 转换数据
     * @param holder
     * @param entity
     */
    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        final String text;
        final String imageUrl;
        final ArrayList<String> bannerImages;
        //开始遍历适配器中存在的ItemType
        switch (holder.getItemViewType()) {
            case ItemType.TEXT:
                //纯文本模式，从entity实体中拿到文本，再通过holder设置到这个xml布局的控件中
                text = entity.getField(MultipleFields.TEXT);
                holder.setText(R.id.text_single, text);
                break;
            case ItemType.IMAGE:
                //纯图片模式，通过Glide将图片into到img_single的控件中去
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_single));
                break;
            case ItemType.TEXT_IMAGE:
                text = entity.getField(MultipleFields.TEXT);
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_multiple));
                holder.setText(R.id.tv_multiple, text);
                break;
            case ItemType.BANNER:
                if(!mIsInitBanner) {
                    bannerImages = entity.getField(MultipleFields.BANNER);
                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.banner_recycler_item);
                    BannerCreator.setDefault(convenientBanner,bannerImages,this);
                    mIsInitBanner = true;
                }
                break;
            default:
                break;

        }
    }

    /**
     * 从这里拿到item的size
     * @param gridLayoutManager
     * @param position
     * @return
     */
    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    /**
     * 点击事件
     * @param position
     */
    @Override
    public void onItemClick(int position) {

    }
}
