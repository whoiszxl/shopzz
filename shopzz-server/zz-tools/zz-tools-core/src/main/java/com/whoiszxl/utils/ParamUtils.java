package com.whoiszxl.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author whoiszxl
 * @date 2022/3/31
 */
public class ParamUtils {


    /**
     * 字符串拼接参数转数组
     *
     * @param ids 字符串拼接参数
     * @return
     */
    public static List str2Array(String ids) {
        return Arrays.stream(ids.split(","))
                .map(idStr -> Long.parseLong(idStr.trim()))
                .collect(Collectors.toList());
    }

    /**
     * 数组转字符串
     * @param spuIds
     * @return
     */
    public static String array2Str(List<Long> spuIds) {
        StringBuffer sb = new StringBuffer();
        for (Long spuId : spuIds) {
            sb.append(spuId.toString());
            sb.append(",");
        }

        return sb.toString().substring(0, sb.length() - 1);
    }
}
