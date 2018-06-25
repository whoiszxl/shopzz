package com.whoiszxl.xl.delegates.web.client;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.whoiszxl.xl.delegates.web.WebDelegate;
import com.whoiszxl.xl.delegates.web.route.Router;
import com.whoiszxl.xl.util.log.XLLogger;

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE;

    public WebViewClientImpl(WebDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        XLLogger.d("shouldOverrideUrlLoading", url);

        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }
}
