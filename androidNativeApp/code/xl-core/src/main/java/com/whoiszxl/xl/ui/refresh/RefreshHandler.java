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

    /**
     * 简单工厂模式，通过new出自己类的私有构造函数来创建一个当前对象
     * @param REFRESH_LAYOUT
     * @param recyclerView
     * @param converter
     * @return
     */
    public static RefreshHandler create(SwipeRefreshLayout REFRESH_LAYOUT,
                                        RecyclerView recyclerView,
                                        DataConverter converter) {
        return new RefreshHandler(REFRESH_LAYOUT,recyclerView,converter,new PagingBean());
    }

    /**
     * 刷新的时候调用
     */
    private void refresh() {
        //在网络请求前将REFRESH_LAYOUT，也就是那个刷新球显示出来
        REFRESH_LAYOUT.setRefreshing(true);
        //开始做一个两秒的延时,在延时中做网络刷新请求，刷新完成之后将刷新球的状态设置为false隐藏
        Starter.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //todo 在這裡做網絡請求

                REFRESH_LAYOUT.setRefreshing(false);
            }
        },2000);
    }

    /**
     * 通过api url初始化首页数据
     * @param url
     */
    public void firstPage(String url) {
        //设置1秒的延迟
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                        //获取到接口json返回的data数据
                        final JSONObject object = JSON.parseObject(response).getJSONObject("data");
                        //设置分页需要的总条数和每页数量
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("pageSize"));
                        //设置adapter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        //页数自增1
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
