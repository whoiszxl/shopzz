package com.whoiszxl.xlmall.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.whoiszxl.xl.delegates.XlDelegate;

public class ExampleDelegate extends XlDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
