package com.whoiszxl.xl.ec.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.whoiszxl.xl.delegates.XlDelegate;
import com.whoiszxl.xl.ec.R;
import com.whoiszxl.xl.ec.R2;
import com.whoiszxl.xl.ui.launcher.ScrollLauncherTag;
import com.whoiszxl.xl.util.storage.XLPreference;
import com.whoiszxl.xl.util.timer.BaseTimerTask;
import com.whoiszxl.xl.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author whoiszxl
 * 第一次进来时候的广告倒计时页面
 */
public class LauncherDelegate extends XlDelegate implements ITimerListener {

    /**
     * 倒计时的文字控件
     */
    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer = null;

    /**
     * 倒计时的Timer对象
     */
    private Timer mTimer = null;

    /**
     * 倒计时的总共秒数
     */
    private int mCount = 5;


    /**
     * 点击倒计时控件的事件
     */
    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView() {
        if(mTimer != null){
            //取消倒计时事件并检查是否要进入启动轮播图界面
            mTimer.cancel();
            mTimer = null;
            checkIsShowScroll();
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }

    //判断是否要显示滑动轮播
    private void checkIsShowScroll() {
        if(!XLPreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            start(new LauncherScrollDelegate(), SINGLETASK);
        }else {
            //TODO 检查用户是否登录
        }
    }


    /**
     * 初始化Timer的事件
     */
    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }


    /**
     * 倒计时实际处理的业务
     */
    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mTvTimer != null) {
                    //在线程中设置倒计时的文字内容
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    //自减
                    mCount--;
                    //倒计时到头了，取消倒计时事件并判断要不要进入轮播图界面
                    if(mCount < 0) {
                        if(mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }
}
