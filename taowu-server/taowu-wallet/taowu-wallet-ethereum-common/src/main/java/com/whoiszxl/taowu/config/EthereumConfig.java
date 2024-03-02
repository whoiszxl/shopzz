package com.whoiszxl.taowu.config;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.util.concurrent.TimeUnit;

@Configuration
public class EthereumConfig {

    @Value("${ethereum.nodeurl}")
    private String ethereumNodeUrl;

    @Bean
    public Web3j web3j() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        return Web3j.build(new HttpService(ethereumNodeUrl, okHttpClient));
    }

}
