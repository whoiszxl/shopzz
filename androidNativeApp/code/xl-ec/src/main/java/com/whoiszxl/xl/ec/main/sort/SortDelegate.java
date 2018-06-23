package com.whoiszxl.xl.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.whoiszxl.xl.delegates.bottom.BottomItemDelegate;
import com.whoiszxl.xl.ec.R;

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
}
