package com.whoiszxl.xl.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.whoiszxl.xl.R;
import com.whoiszxl.xl.delegates.XlDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author whoiszxl
 */
public abstract class ProxyActivity extends SupportActivity {


    public abstract XlDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    /**
     * 加载子类setRootDelegate()返回的delegate的fragment到activity中
     * @param savedInstanceState
     */
    private void initContainer(@Nullable Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if(savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
