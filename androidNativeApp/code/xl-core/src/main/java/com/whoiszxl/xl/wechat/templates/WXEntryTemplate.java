package com.whoiszxl.xl.wechat.templates;

import com.whoiszxl.xl.wechat.BaseWXEntryActivity;
import com.whoiszxl.xl.wechat.XLWechat;

public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        XLWechat.getInstance().getSignInCallback().onSignInSuccess(userInfo);
    }
}
