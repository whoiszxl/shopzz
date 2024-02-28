package com.whoiszxl.taowu.starter.gpt.session.impl;

import com.whoiszxl.taowu.starter.gpt.IGptApi;
import com.whoiszxl.taowu.starter.gpt.interceptor.GptInterceptor;
import com.whoiszxl.taowu.starter.gpt.session.GptSessionFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * gpt session 工厂 openai实现
 * @author whoiszxl
 */
public class OpenAiGptSessionFactory implements GptSessionFactory {

    private final String key;
    private final String endpoint;

    public OpenAiGptSessionFactory(String key, String endpoint) {
        this.key = key;
        this.endpoint = endpoint;
    }

    @Override
    public OpenAiGptSession openSession() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new GptInterceptor(key))
                .connectTimeout(666, TimeUnit.SECONDS)
                .writeTimeout(666, TimeUnit.SECONDS)
                .readTimeout(666, TimeUnit.SECONDS)
                .build();

        IGptApi gptApi = new Retrofit.Builder()
                .baseUrl(endpoint)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(IGptApi.class);

        return new OpenAiGptSession(gptApi);
    }
}
