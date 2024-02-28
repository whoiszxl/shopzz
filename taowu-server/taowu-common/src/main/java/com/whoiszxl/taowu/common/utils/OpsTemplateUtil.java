package com.whoiszxl.taowu.common.utils;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.google.gson.Gson;

import java.util.Map;

/**
 * ops模板工具
 * @author whoiszxl
 */
public class OpsTemplateUtil {

    public static String convertTemplate(String templateStr, String params) {
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());
        Template template = engine.getTemplate(templateStr);
        return template.render(new Gson().fromJson(params, Map.class));
    }

    public static String replaceGanR(String str) {
        return str.replace("\r", "");
    }
}