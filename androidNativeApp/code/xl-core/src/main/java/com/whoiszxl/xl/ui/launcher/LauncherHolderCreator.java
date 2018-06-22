package com.whoiszxl.xl.ui.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

public class LauncherHolderCreator implements CBViewHolderCreator {
    @Override
    public Object createHolder() {
        return new LauncherHolder();
    }
}
