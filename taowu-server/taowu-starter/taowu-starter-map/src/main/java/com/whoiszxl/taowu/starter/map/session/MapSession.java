package com.whoiszxl.taowu.starter.map.session;

import com.whoiszxl.taowu.starter.map.cqrs.request.StaticMapRequest;
import okhttp3.ResponseBody;

/**
 * @author whoiszxl
 */
public interface MapSession {

    /**
     * 获取静态地图
     * @param staticMapRequest
     * @return
     */
    ResponseBody getStaticMap(StaticMapRequest staticMapRequest);

    /**
     * 通过经纬度获取静态地图
     * @param location
     * @return
     */
    String getStaticMap(String location);
}
