package com.whoiszxl.xl.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * @author whoiszxl
 * 作为fragment的delegate基类
 */
public abstract class BaseDelegate extends SwipeBackFragment{

    private Unbinder mUnbinder = null;

    /**
     *  让子类强制重写返回xml的安卓界面布局，或者返回一个实实在在的View对象
     * @return
     */
    public abstract Object setLayout();

    /**
     * 让子类强制重写，做一些绑定控件的操作
     * @param savedInstanceState
     * @param rootView
     */
    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取到子类重写的setLayout()返回的是View还是资源ID，然后获取到实际的View
        View rootView = null;
        if(setLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) setLayout(), container, false);
        }else if(setLayout() instanceof View) {
            rootView = (View) setLayout();
        }

        //如果不为空，就直接让黄油刀绑定这个View到this中
        if(rootView != null) {
            mUnbinder = ButterKnife.bind(this, rootView);
            onBindView(savedInstanceState, rootView);
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //在销毁的时候，黄油刀解绑之
        if(mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
