package com.whoiszxl.taowu.starter.gpt.interceptor;

import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * gpt请求拦截器，配置对应的key
 * @author whoiszxl
 */
public class GptInterceptor implements Interceptor {

    private String key;

    public GptInterceptor(String key) {
        this.key = key;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .url(request.url())
                .header(Header.AUTHORIZATION.getValue(), "Bearer " + key)
                .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .method(request.method(), request.body())
                .build();

        return chain.proceed(newRequest);
    }
}
