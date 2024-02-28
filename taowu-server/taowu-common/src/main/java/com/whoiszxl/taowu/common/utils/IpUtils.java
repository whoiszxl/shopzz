package com.whoiszxl.taowu.common.utils;

import cn.hutool.core.net.NetUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.whoiszxl.taowu.common.properties.TaowuProperties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import net.dreamlu.mica.ip2region.core.IpInfo;

/**
 * IP 工具
 * @author whoiszxl
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IpUtils {

    /**
     * 太平洋网开放 API：查询 IP 归属地
     */
    private static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true";

    /**
     * 根据 IP 获取归属地信息
     *
     * @param ip
     *            IP 地址
     * @return 归属地信息
     */
    public static String getCityInfo(String ip) {
        if (TaowuProperties.PARSE_LOCAL_ADDR) {
            return getLocalCityInfo(ip);
        } else {
            return getHttpCityInfo(ip);
        }
    }

    /**
     * 根据 IP 获取归属地信息（网络解析）
     *
     * @param ip
     *            IP 地址
     * @return 归属地信息
     */
    public static String getHttpCityInfo(String ip) {
        if (isInnerIp(ip)) {
            return "内网IP";
        }
        String api = String.format(IP_URL, ip);
        JSONObject object = JSONUtil.parseObj(HttpUtil.get(api));
        return object.get("addr", String.class);
    }

    /**
     * 根据 IP 获取归属地信息（本地解析）
     *
     * @param ip
     *            IP 地址
     * @return 归属地信息
     */
    public static String getLocalCityInfo(String ip) {
        if (isInnerIp(ip)) {
            return "内网IP";
        }
        Ip2regionSearcher ip2regionSearcher = SpringUtil.getBean(Ip2regionSearcher.class);
        IpInfo ipInfo = ip2regionSearcher.memorySearch(ip);
        if (ipInfo != null) {
            return ipInfo.getAddress();
        }
        return null;
    }

    /**
     * 是否为内网 IPv4
     *
     * @param ip
     *            IP 地址
     * @return 是否为内网 IP
     */
    public static boolean isInnerIp(String ip) {
        ip = "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : HtmlUtil.cleanHtmlTag(ip);
        return NetUtil.isInnerIP(ip);
    }
}
