package com.whoiszxl.xl.wechat;

import android.app.Activity;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.whoiszxl.xl.app.ConfigKeys;
import com.whoiszxl.xl.app.Starter;
import com.whoiszxl.xl.wechat.callback.IWeChatSignInCallback;

public class XLWechat {

    public static final String APP_ID = Starter.getConfiguration(ConfigKeys.WE_CHAT_APP_ID);
    public static final String APP_SECRET = Starter.getConfiguration(ConfigKeys.WE_CHAT_APP_SECRET);

    private final IWXAPI WXAPI;
    private IWeChatSignInCallback mSignInCallback = null;

    private static final class Holder {
        private static final XLWechat INSTANCE = new XLWechat();
    }

    public static XLWechat getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 私有构造初始化，创建注册微信
     */
    private XLWechat() {
        final Activity activity = Starter.getConfiguration(ConfigKeys.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        WXAPI.registerApp(APP_ID);
    }

    public final IWXAPI getWXAPI() {
        return WXAPI;
    }

    public XLWechat onSignSuccess(IWeChatSignInCallback callback) {
        this.mSignInCallback = callback;
        return this;
    }

    public IWeChatSignInCallback getSignInCallback() {
        return mSignInCallback;
    }

    public final void signIn() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);
    }
}
