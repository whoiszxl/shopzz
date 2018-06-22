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
 * 注册页面
 */
public class SignUpDelegate extends XlDelegate {

    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText mPhone = null;
    @BindView(R2.id.edit_sign_up_password)
    TextInputEditText mPassword = null;
    @BindView(R2.id.edit_sign_up_re_password)
    TextInputEditText mRePassword = null;

    @BindView(R2.id.edit_sign_up_question)
    TextInputEditText mQuestion = null;

    @BindView(R2.id.edit_sign_up_answer)
    TextInputEditText mAnswer = null;

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp() {
        if (checkForm()) {
            RestClient.builder()
                    .url(Api.register)
                    .params("username", mName.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .params("passwordConfirm", mRePassword.getText().toString())
                    .params("phone", mPhone.getText().toString())
                    .params("email", mEmail.getText().toString())
                    .params("question", mQuestion.getText().toString())
                    .params("answer", mAnswer.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            XLLogger.json("USER_PROFILE", response);
                            //Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                            SignHandler.onSignUp(response, mISignListener, getSupportDelegate());
                        }
                    })
                    .build()
                    .post();
        }
    }

    /**
     * 本地校验注册表单参数是否有效
     * @return 是否有效的boolean
     */
    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();
        final String answer = mAnswer.getText().toString();
        final String question = mQuestion.getText().toString();

        boolean isPass = true;

        if (name.isEmpty()) {
            mName.setError("用户名不能为空");
            isPass = false;
        } else {
            mName.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号格式不正确");
            isPass = false;
        } else {
            mPhone.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("密码长度不能少于6位");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (rePassword.isEmpty() || rePassword.length() < 6 || !(rePassword.equals(password))) {
            mRePassword.setError("两次输入的密码不一致");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }

        if (question.isEmpty() || question.length() < 3) {
            mQuestion.setError("密保问题长度不能小于3位");
            isPass = false;
        } else {
            mQuestion.setError(null);
        }

        if (answer.isEmpty()) {
            mAnswer.setError("密保答案不能为空");
            isPass = false;
        } else {
            mAnswer.setError(null);
        }

        return isPass;
    }



    @OnClick(R2.id.tv_link_sign_in)
    void onClickLink() {
        getSupportDelegate().start(new SignInDelegate());
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
