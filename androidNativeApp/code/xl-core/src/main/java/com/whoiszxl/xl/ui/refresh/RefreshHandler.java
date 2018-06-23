package com.whoiszxl.xl.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;

import com.whoiszxl.xl.app.Starter;
import com.whoiszxl.xl.net.RestClient;
import com.whoiszxl.xl.net.callback.ISuccess;

import es.dmoral.toasty.Toasty;

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener{

    private final SwipeRefreshLayout REFRESH_LAYOUT;

    public RefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        REFRESH_LAYOUT.setOnRefreshListener(this);
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
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toasty.warning(Starter.getApplicationContext(), response).show();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onRefresh() {
        refresh();
    }
}
