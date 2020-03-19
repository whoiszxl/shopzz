/**
 * @(#)JsonUtil.java 2014-2-23 下午5:44:19
 */
package com.whoiszxl.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JsonUtil {
    
    public static String objectTojson(Object object){
        return JSON.toJSONString(object,SerializerFeature.WriteDateUseDateFormat);
    }
    

    public static String listTojson(List list){
        return JSON.toJSONString(list, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 字符串Json格式转换为对象Map
     * @param strJson {"username":"sxb"}
     * @return 根据json转换为Map对象
     */
    public static Map<String, Object> jsonToMap(String strJson){
        Map<String, Object> jsoMap = new HashMap<String, Object>();
        try {
            jsoMap = JSONObject.parseObject(strJson,Map.class);
        } catch (JSONException e) {
            System.out.println("json转换Map出错："+e.getMessage());
        }
        
        return jsoMap;
    }


    /**
     * 字符串Json 转换为对象List
     * @param strJson [{"username":"sxb"}]
     * @return 根据json转换List
     */
    public static List<Map<String, Object>> jsonToList(String strJson){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = JSONObject.parseObject(strJson, List.class);
        } catch (JSONException e) {
            System.out.println("json转换List出错："+e.getMessage());
        }
        return list;
    }
    

}
