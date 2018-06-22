package com.whoiszxl.xl.ec.sign;


import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whoiszxl.xl.app.AccountManager;
import com.whoiszxl.xl.app.ConfigKeys;
import com.whoiszxl.xl.app.Configurator;
import com.whoiszxl.xl.app.Starter;
import com.whoiszxl.xl.delegates.BaseDelegate;
import com.whoiszxl.xl.ec.database.DatabaseManager;
import com.whoiszxl.xl.ec.database.UserProfile;

import me.yokeyword.fragmentation.SupportFragmentDelegate;

/**
 * @author whoiszxl
 */
public class SignHandler {

    public static void onSignIn(String response, ISignListener signListener, TextInputEditText mUsername, TextInputEditText mPassword) {
        JSONObject jsonObject = JSON.parseObject(response);

        final int status = jsonObject.getInteger("status");
        final String msg = jsonObject.getString("msg");
        if(status != 0) {
            if (msg.contains("密码")) {
                mPassword.setError(msg);
            } else {
                mUsername.setError(msg);
            }
        }else {
            //TODO 这里的toast弹出，在小米手机上还会在最前面带上应用名，需要弄个github插件 https://github.com/GrenderG/Toasty
            Toast.makeText((Context) Starter.getConfiguration(ConfigKeys.APPLICATION_CONTEXT),msg,Toast.LENGTH_LONG).show();
            final String token = jsonObject.getString("data");
            final UserProfile profile = new UserProfile(token);
            DatabaseManager.getInstance().getDao().insert(profile);

            //已经注册并登录成功了
            AccountManager.setSignState(true);
            signListener.onSignInSuccess();
        }

    }


    public static void onSignUp(String response, ISignListener signListener, SupportFragmentDelegate jump) {
        JSONObject jsonObject = JSON.parseObject(response);

        final int status = jsonObject.getInteger("status");
        final String msg = jsonObject.getString("msg");
        Toast.makeText((Context) Starter.getConfiguration(ConfigKeys.APPLICATION_CONTEXT),msg,Toast.LENGTH_LONG).show();
        if(status == 0) {
            //注册成功,需要直接跳转到登录界面
            jump.start(new SignInDelegate(), 2);
        }
    }

}
