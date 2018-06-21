package com.whoiszxl.xlmall.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.whoiszxl.xl.delegates.XlDelegate;
import com.whoiszxl.xl.net.RestClient;
import com.whoiszxl.xl.net.callback.IError;
import com.whoiszxl.xl.net.callback.IFailure;
import com.whoiszxl.xl.net.callback.ISuccess;

public class ExampleDelegate extends XlDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        RestClient.builder()
                .url("http://118.126.92.128:10101/article/banners/")
                .loader(getContext())
                .params("","")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }

}
