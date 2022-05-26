package com.whoiszxl.utils;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模板工具类
 *
 * @author whoiszxl
 * @date 2022/5/26
 */
public class TemplateUtils {

    public static String renderString(String content, Map<String, String> map) {
        Set<Map.Entry<String, String>> sets = map.entrySet();
        for (Map.Entry<String, String> entry : sets) {
            String regex = "\\$\\{" + entry.getKey() + "\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            content = matcher.replaceAll(entry.getValue());
        }
        return content;
    }
}
