package com.whoiszxl.common;

import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;

public class AppCommonConstants {


    public static ArrayList<String> netList = CollectionUtil.newArrayList("2g", "3g", "4g", "5g", "wifi");

    public static ArrayList<String> versionList = CollectionUtil.newArrayList("v1.1.0", "v2.1.0", "v3.1.0", "v4.1.0", "v5.1.0");

    public static ArrayList<String> channelList = CollectionUtil.newArrayList("wandoujia", "xiaomi", "huawei", "appstore", "anzhi");

    public static ArrayList<String> modelList = CollectionUtil.newArrayList(
            "iphone xr", "iphone 5s", "iphone 12", "iphone 13", "iphone 14",
            "xiaomi 6", "huawei p30", "xiaomi 11", "nokia 5230", "nokia e63");

    public static ArrayList<String> brandList = CollectionUtil.newArrayList("xiaomi", "apple", "nokia", "huawei", "htc");

    public static ArrayList<String> keywordList = CollectionUtil.newArrayList(
            "queen", "killer queen", "波西米亚狂想曲", "小米手机", "苹果电脑",
            "iphone", "苦瓜", "菠萝", "国际歌CD", "沙龙CD", "海上花原声带", "PS5", "战神5");

    public static ArrayList<String> entryList = CollectionUtil.newArrayList("install", "icon", "notify");

}
