package com.whoiszxl.taowu.starter.map;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * map接口
 * @author whoiszxl
 */
public interface IMapApi {

    /**
     * 获取静态地图
     * @param location 经纬度，如：112.96846,28.19180
     * @param zoom 地图缩放级别:[1,17]
     * @param size 图片宽度*图片高度。最大值为1024*1024
     * @param markers 标注位置，如: mid,0xFFaaaa,C:112.96846,28.19180
     * @param key 秘钥
     * @return 静态地图二进制资源
     */
    @GET("staticmap")
    Single<ResponseBody> getStaticMap(@Query("location") String location,
                                      @Query("zoom") int zoom,
                                      @Query("size") String size,
                                      @Query("markers") String markers,
                                      @Query("key") String key);

}
