package com.whoiszxl.xl.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.whoiszxl.xl.R;
import com.whoiszxl.xl.app.Starter;
import com.whoiszxl.xl.net.RestClient;
import com.whoiszxl.xl.net.callback.ISuccess;
import com.whoiszxl.xl.ui.recycler.DataConverter;
import com.whoiszxl.xl.ui.recycler.MultipleRecyclerAdapter;

import es.dmoral.toasty.Toasty;

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;

    private RefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT,
                          RecyclerView recyclerView,
                          DataConverter converter,
                          PagingBean pagingBean) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = pagingBean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout REFRESH_LAYOUT,
                                        RecyclerView recyclerView,
                                        DataConverter converter) {
        return new RefreshHandler(REFRESH_LAYOUT,recyclerView,converter,new PagingBean());
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Starter.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //todo 在這裡做網絡請求

                REFRESH_LAYOUT.setRefreshing(false);
            }
        },2000);
    }

    public void firstPage(String url) {
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response).getJSONObject("data");
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("pageSize"));
                        //设置adapter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
