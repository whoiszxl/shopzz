package com.whoiszxl.xl.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.whoiszxl.xl.net.RestClient;
import com.whoiszxl.xl.net.callback.IError;
import com.whoiszxl.xl.net.callback.IFailure;
import com.whoiszxl.xl.net.callback.ISuccess;
import com.whoiszxl.xl.util.log.XLLogger;

/**
 * @author whoiszxl
 */
public abstract class BaseWXEntryActivity extends BaseWXActivity {

    /**
     * 用户登录成功后回调
     */
    protected abstract void onSignInSuccess(String userInfo);


    /**
     * 微信发送请求到第三方应用后的回调
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {

    }

    /**
     * 第三方应用发送请求到微信后的回调
     * @param baseResp
     */
    @Override
    public void onResp(BaseResp baseResp) {
        //获取到响应的code
        final String code = ((SendAuth.Resp) baseResp).code;
        //拼接一下通过code和appid，secret获取access_token和openid的url
        final StringBuilder authUrl = new StringBuilder();
        authUrl
                .append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(XLWechat.APP_ID)
                .append("&secret=")
                .append(XLWechat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");

        //获取auth认证
        getAuth(authUrl.toString());
    }

    /**
     * 获取auth认证
     * @param authUrl
     */
    private void getAuth(String authUrl) {
        RestClient
                .builder()
                .url(authUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject authObj = JSON.parseObject(response);
                        final String accessToken = authObj.getString("access_token");
                        final String openId = authObj.getString("openid");

                        final StringBuilder userInfoUrl = new StringBuilder();
                        //拼接获取用户信息的url
                        userInfoUrl
                                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");

                        XLLogger.d("userInfoUrl", userInfoUrl.toString());
                        getUserInfo(userInfoUrl.toString());
                    }
                })
                .build()
                .get();
    }

    /**
     * 通过第三方接口获取用户信息
     * @param userInfoUrl
     */
    private void getUserInfo(String userInfoUrl) {
        RestClient
                .builder()
                .url(userInfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        onSignInSuccess(response);
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
