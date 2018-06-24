package com.whoiszxl.xl.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.whoiszxl.xl.delegates.bottom.BottomItemDelegate;
import com.whoiszxl.xl.ec.R;
import com.whoiszxl.xl.ec.R2;
import com.whoiszxl.xl.ec.api.Api;
import com.whoiszxl.xl.net.RestClient;
import com.whoiszxl.xl.net.callback.ISuccess;
import com.whoiszxl.xl.ui.recycler.BaseDecoration;
import com.whoiszxl.xl.ui.recycler.MultipleFields;
import com.whoiszxl.xl.ui.recycler.MultipleItemEntity;
import com.whoiszxl.xl.ui.refresh.RefreshHandler;

import java.util.ArrayList;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

/**
 * @author whoiszxl
 * 主页delegate
 */
public class IndexDelegate extends BottomItemDelegate {

    //
    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;
    @BindView(R2.id.icon_index_scan)
    IconTextView mIconScan = null;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchView = null;

    private RefreshHandler mRefreshHandler = null;


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler = RefreshHandler.create(mRefreshLayout,mRecyclerView,new IndexDataConverter());
    }

    /**
     * 初始化刷新布局RefreshLayout
     */
    private void initRefreshLayout() {
        //初始化那个刷新球的颜色变化
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        //设置位置
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    /**
     * 初始化主页的布局 RecyclerView
     */
    private void initRecyclerView() {
        // 创建一个宽度为4个单位的网格布局
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        // 将这个布局设置到indexDelegate里
        mRecyclerView.setLayoutManager(manager);
        //添加商品item之间的间隔
        mRecyclerView.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(getContext(),R.color.app_background),5));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //在懒加载的方法里面初始化刷新球和网格布局
        initRefreshLayout();
        initRecyclerView();
        //初始化首页数据
        mRefreshHandler.firstPage(Api.index_goods);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }



}
