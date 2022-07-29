package com.whoiszxl.utils;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.google.gson.Gson;

import java.util.Map;

public class MyTemplateUtil {


    public static String convertTemplate(String templateStr, String params) {
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());
        Template template = engine.getTemplate(templateStr);
        String result = template.render(new Gson().fromJson(params, Map.class));
        return result;
    }

    public static String replaceGanR(String str) {
        return str.replace("\r", "");
    }
}
