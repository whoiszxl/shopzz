package com.whoiszxl.xl.net.interceptors;

import android.support.annotation.NonNull;
import android.support.annotation.RawRes;

import com.whoiszxl.xl.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author whoiszxl
 * okhttp debug用的拦截器
 */
public class DebugInterceptor extends BaseInterceptor {

    /**
     * debug的地址
     */
    private final String DEBUG_URL;

    /**
     * 本地的debug json文件id
     */
    private final int DEBUG_RAW_ID;


    public DebugInterceptor(String debugUrl, int rawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = rawId;
    }

    /**
     * 创建一个json响应返回
     * @param chain
     * @param json
     * @return
     */
    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    /**
     * 传入chain和json资源id返回一个响应
     * @param chain
     * @param rawId
     * @return
     */
    private Response debugResponse(Chain chain, @RawRes int rawId) {
        final String json = FileUtil.getRawFile(rawId);
        return getResponse(chain, json);
    }

    /**
     * 拦截判断，如果访问的url里面包含了debug的url，那就需要走debug的处理
     * 如果不存在，那就直接跳过，走正常流程
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID);
        }
        return chain.proceed(chain.request());
    }
}
