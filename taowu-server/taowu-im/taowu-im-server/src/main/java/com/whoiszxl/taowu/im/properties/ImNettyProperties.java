package com.whoiszxl.taowu.im.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * netty配置类
 * @author whoiszxl
 */
@Data
@Component
@ConfigurationProperties(prefix = "netty")
public class ImNettyProperties {

    /**
     * websocket端口
     */
    private Integer websocketPort;

    /**
     * tcp端口
     */
    private Integer tcpPort;

    /**
     * 心跳超时时间
     */
    private Integer timeoutMs;

    /**
     * 节点ID
     */
    private String nodeId;

}
