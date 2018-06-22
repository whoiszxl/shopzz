package com.whoiszxl.xl.app;

import com.whoiszxl.xl.util.storage.XLPreference;

/**
 * @author whoiszxl
 */
public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    /**
     * 保存用户是否登录的状态,登录后调用他
     * @param state
     */
    public static void setSignState(boolean state) {
        XLPreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    /**
     * 判断是否登录
     * @return
     */
    private static boolean isSignIn() {
        return XLPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }
}
