package com.whoiszxl.xl.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.whoiszxl.xl.delegates.XlDelegate;
import com.whoiszxl.xl.ec.R;
import com.whoiszxl.xl.ec.R2;
import com.whoiszxl.xl.ec.api.Api;
import com.whoiszxl.xl.net.RestClient;
import com.whoiszxl.xl.net.callback.ISuccess;
import com.whoiszxl.xl.util.log.XLLogger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author whoiszxl
 * 登录页面
 */
public class SignInDelegate extends XlDelegate{

    @BindView(R2.id.edit_sign_in_username)
    TextInputEditText mUsername = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;


    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkForm()) {
            RestClient.builder()
                    .url(Api.jwt_login)
                    .params("username", mUsername.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            XLLogger.json("USER_PROFILE", response);
                            //Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                            SignHandler.onSignIn(response, mISignListener, mUsername, mPassword);
                        }
                    })
                    .build()
                    .post();
        }
    }


    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink() {
        getSupportDelegate().start(new SignUpDelegate());
    }

    private boolean checkForm() {
        final String username = mUsername.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (username.isEmpty() || username.length() < 3) {
            mUsername.setError("用户名格式不正确");
            isPass = false;
        } else {
            mUsername.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }



}
