package com.whoiszxl.xl.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wang.avi.AVLoadingIndicatorView;
import com.whoiszxl.xl.R;
import com.whoiszxl.xl.util.dimen.DimenUtil;

import java.util.ArrayList;

/**
 * @author whoiszxl
 * loading加载库
 */
public class AVLoader {

    /**
     * loading 图案的大小
     */
    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;

    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    /**
     * 配置loading的默认样式
     */
    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();

    /**
     * 展示这个loading
     * @param context context对象
     * @param type loading的样式的简单类名的枚举对象
     */
    public static void showLoading(Context context, Enum<LoaderStyle> type) {
        showLoading(context, type.name());
    }

    /**
     * 展示这个loading
     * @param context context对象
     * @param type loading的样式的简单类名的字符串
     */
    public static void showLoading(Context context, String type) {

        //获取到资源中的dialog配置
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        //通过类型创建AVLoadingIndicatorView对象
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        //给dialog设置进去
        dialog.setContentView(avLoadingIndicatorView);

        //获取到屏幕的宽高
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        //获取到window对象
        final Window dialogWindow = dialog.getWindow();

        //如果window对象存在
        if (dialogWindow != null) {
            //设置一个合适的比例和位置
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        //将这个dialog存到数组中
        LOADERS.add(dialog);
        //展示一下
        dialog.show();
    }

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    /**
     * 停止loading，遍历所有showing状态的直接cancel掉
     */
    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
    }
}
