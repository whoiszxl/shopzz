package com.whoiszxl.xl.delegates.web;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.whoiszxl.xl.util.log.XLLogger;

public class WebViewInitializer {

    @SuppressLint("SetJavaScriptEnabled")
    public WebView createWebView(WebView webView) {
        WebView.setWebContentsDebuggingEnabled(true);
        //不能橫向滾動
        webView.setHorizontalScrollBarEnabled(false);
        //不能縱向滾動
        webView.setVerticalScrollBarEnabled(false);
        //允許截圖
        webView.setDrawingCacheEnabled(true);
        //屏蔽長按事件
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        //初始化websettings
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        final String ua = settings.getUserAgentString();
        settings.setUserAgentString(ua+"XL");
        settings.setBuiltInZoomControls(false);
        //隱藏縮放控件
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        //禁止縮放
        settings.setSupportZoom(false);
        //文件權限
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        //緩存相關
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        return webView;
    }

}
