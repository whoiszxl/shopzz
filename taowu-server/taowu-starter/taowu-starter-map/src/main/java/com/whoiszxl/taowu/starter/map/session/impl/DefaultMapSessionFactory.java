package com.whoiszxl.taowu.starter.map.session.impl;

import com.whoiszxl.taowu.starter.map.session.MapSession;
import com.whoiszxl.taowu.starter.map.IMapApi;
import com.whoiszxl.taowu.starter.map.session.MapSessionFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

/**
 * https://restapi.amap.com/v3/staticmap
 * ?location=112.96846,28.19180
 * &zoom=13
 * &size=480*320
 * &markers=mid,0xFFaaaa,C:112.96846,28.19180
 * &key=e0e344323487736d8dad831d70c31d8a
 */
public class DefaultMapSessionFactory implements MapSessionFactory {

    @Override
    public MapSession openSession() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(666, TimeUnit.SECONDS)
                .writeTimeout(666, TimeUnit.SECONDS)
                .readTimeout(666, TimeUnit.SECONDS)
                .build();

        IMapApi mapApi = new Retrofit.Builder()
                .baseUrl("https://restapi.amap.com/v3/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(IMapApi.class);

        return new DefaultMapSession(mapApi);
    }
}
