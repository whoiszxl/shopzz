package com.whoiszxl.xl.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage(Api.index_goods);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }



}
