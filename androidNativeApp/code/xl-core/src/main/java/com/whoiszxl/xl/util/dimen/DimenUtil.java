package com.whoiszxl.xl.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.whoiszxl.xl.app.Starter;

/**
 * @author whoiszxl
 *
 */
public class DimenUtil {

    public static int getScreenWidth() {
        final Resources resources = Starter.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = Starter.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
