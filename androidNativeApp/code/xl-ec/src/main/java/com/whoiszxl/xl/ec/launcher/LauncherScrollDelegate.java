package com.whoiszxl.xl.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.whoiszxl.xl.app.AccountManager;
import com.whoiszxl.xl.app.IUserChecker;
import com.whoiszxl.xl.delegates.XlDelegate;
import com.whoiszxl.xl.ec.R;
import com.whoiszxl.xl.ui.launcher.LauncherHolderCreator;
import com.whoiszxl.xl.ui.launcher.ScrollLauncherTag;
import com.whoiszxl.xl.util.storage.XLPreference;

import java.util.ArrayList;

/**
 * @author whoiszxl
 * 首次安装app进入时候的轮播图页面
 */
public class LauncherScrollDelegate extends XlDelegate implements OnItemClickListener{

    /**
     * 轮播图控件
     */
    private ConvenientBanner<Integer> mConvenientBanner = null;

    /**
     * 存储轮播图资源id的List
     */
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();


    private ILauncherListener mILauncherListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    /**
     * 初始化轮播图
     */
    private void initBanner() {
        //将图片资源文件存入数组
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);

        //通过插件设置各种参数
        mConvenientBanner
                .setPages(new LauncherHolderCreator(), INTEGERS) //配置一个自定义图片holder,存入，并附带着图片资源数组
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus}) //配置轮播的指示器小点
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL) //配置位置居中
                .setOnItemClickListener(this)//配置轮播图点击事件
                .setCanLoop(false);//不允许循环
    }

    @Override
    public Object setLayout() {
        //layout直接返回插件的这个东西了，这个就是继承layout
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {
        //点击最后一个
        if(position == INTEGERS.size()-1) {
            XLPreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            //检查用户是否登录
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                }

                @Override
                public void onNotSignIn() {
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                }
            });
        }
    }
}
