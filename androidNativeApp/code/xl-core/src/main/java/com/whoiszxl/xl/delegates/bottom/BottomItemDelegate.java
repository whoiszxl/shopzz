package com.whoiszxl.xl.delegates.bottom;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.whoiszxl.xl.R;
import com.whoiszxl.xl.delegates.XlDelegate;

/**
 * @author whoiszxl
 * 底部导航栏的抽象delegate
 */
public abstract class BottomItemDelegate extends XlDelegate implements View.OnKeyListener{

    private long mExitTime = 0;

    /**
     * 两次双击返回键退出中 两次双击的时间间隔
     */
    private static final int EXIT_TIME = 2000;

    @Override
    public void onResume() {
        super.onResume();
        View rootView = getView();
        //如果不重新设置的话，退出一次应用之后第二次进来 key就不会生效了
        if(rootView != null) {
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if((System.currentTimeMillis() - mExitTime) > EXIT_TIME) {
                Toast.makeText(getContext(), "再按一次返回键退出"+getString(R.string.app_name),Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }else{
                _mActivity.finish();
                if(mExitTime != 0) {
                    mExitTime = 0;
                }
            }
            return true;
        }
        return false;
    }
}
