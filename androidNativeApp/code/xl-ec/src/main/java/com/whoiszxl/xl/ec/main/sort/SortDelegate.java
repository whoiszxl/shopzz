package com.whoiszxl.xl.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.whoiszxl.xl.delegates.bottom.BottomItemDelegate;
import com.whoiszxl.xl.ec.R;
import com.whoiszxl.xl.ec.main.sort.list.VerticalListDelegate;

/**
 * @author whoiszxl
 * 分类delegate
 */
public class SortDelegate extends BottomItemDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 將VerticalListDelegate的布局加载到vertical_list_container这个的控件里
        final VerticalListDelegate listDelegate = new VerticalListDelegate();
        loadRootFragment(R.id.vertical_list_container, listDelegate);
    }
}
