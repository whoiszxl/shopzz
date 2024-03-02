package com.whoiszxl.taowu.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;

import java.net.MalformedURLException;

@Slf4j
@Configuration
public class BitcoinClientConfig {

    @Value("${bitcoin.nodeurl}")
    private String bitcoinNodeUrl;

    @Bean
    public BitcoindRpcClient bitcoindRpcClient() {
        try {
            return new BitcoinJSONRPCClient(bitcoinNodeUrl);
        } catch (MalformedURLException e) {
            log.error("bitcoin client 初始化失败", e);
            return null;
        }
    }
}
