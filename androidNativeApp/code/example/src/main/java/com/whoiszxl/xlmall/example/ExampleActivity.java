package com.whoiszxl.xlmall.example;

import com.whoiszxl.xl.activities.ProxyActivity;
import com.whoiszxl.xl.delegates.XlDelegate;

public class ExampleActivity extends ProxyActivity{

    @Override
    public XlDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
