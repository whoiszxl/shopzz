package com.whoiszxl.taowu.starter.map.session.impl;

import com.whoiszxl.taowu.starter.map.IMapApi;
import com.whoiszxl.taowu.starter.map.session.MapSession;
import com.whoiszxl.taowu.starter.map.cqrs.request.StaticMapRequest;
import okhttp3.ResponseBody;

/**
 * 默认地图session会话
 * @author whoiszxl
 */
public class DefaultMapSession implements MapSession {

    private final IMapApi mapApi;

    public DefaultMapSession(IMapApi mapApi) {
        this.mapApi = mapApi;
    }

    @Override
    public ResponseBody getStaticMap(StaticMapRequest staticMapRequest) {
        return this.mapApi.getStaticMap(staticMapRequest.getLocation(),
                staticMapRequest.getZoom(),
                staticMapRequest.getSize(),
                staticMapRequest.getMarkers(),
                staticMapRequest.getKey()).blockingGet();
    }

    @Override
    public String getStaticMap(String location) {
        return null;
    }
}
