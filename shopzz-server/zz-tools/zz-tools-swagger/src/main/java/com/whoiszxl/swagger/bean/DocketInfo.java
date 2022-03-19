package com.whoiszxl.swagger.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * DocketInfo类
 *
 * @author whoiszxl
 * @date 2021/11/30
 */
@Data
public class DocketInfo {

    /** 标题 */
    private String title = "seesee接口在线文档";

    /** 自定义组名 */
    private String group = "";

    /** 描述 */
    private String description = "seesee接口在线文档";

    /** 版本 */
    private String version = "1.0.0";

    /** 许可证 */
    private String license = "";

    /** 许可证URL */
    private String licenseUrl = "";

    /** 服务条款URL */
    private String termsOfServiceUrl = "";

    private Contact contact = new Contact();

    /**
     * swagger会解析的包路径
     **/
    /** 排序 */
    private String basePackage = "";

    /**
     * swagger会解析的url规则
     **/
    /** 排序 */
    private List<String> basePath = new ArrayList<>();

    /** 在basePath基础上需要排除的url规则 */
    private List<String> excludePath = new ArrayList<>();

    private List<GlobalOperationParameter> globalOperationParameters;

    /** 排序 */
    private Integer order = 1;

    public String getGroup() {
        if (group == null || "".equals(group)) {
            return title;
        }
        return group;
    }
}