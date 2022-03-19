package com.whoiszxl.swagger;

import com.whoiszxl.swagger.bean.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * SwaggerProperties 参数配置类
 *
 * @author whoiszxl
 * @date 2021/11/30
 */

@ConfigurationProperties(prefix = "seesee.swagger2")
@Data
public class SwaggerProperties {

    /** 是否开启swagger文档 */
    private Boolean enabled = true;

    /** 是否是生产环境 */
    private Boolean production = false;

    /** 离线文档路径 */
    private Markdown markdown = new Markdown();

    /** token名 */
    private String tokenName = "token";

    /**
     * 访问账号密码
     */
    private Basic basic = new Basic();

    /**
     * 标题
     **/
    private String title = "在线文档";
    private String group = "";
    /**
     * 描述
     **/
    private String description = "在线文档";
    /**
     * 版本
     **/
    private String version = "1.0.0";
    /**
     * 许可证
     **/
    private String license = "Apache License 2.0";
    /**
     * 许可证URL
     **/
    private String licenseUrl = "https://github.com/whoiszxl/seesee/blob/master/LICENSE";
    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";

    private Contact contact = new Contact();

    /**
     * swagger会解析的包路径
     **/
    private String basePackage = "com.whoiszxl";

    /**
     * swagger会解析的url规则
     **/
    private List<String> basePath = new ArrayList<>();
    /**
     * 在basePath基础上需要排除的url规则
     **/
    private List<String> excludePath = new ArrayList<>();

    /**
     * 分组文档
     **/
    private Map<String, DocketInfo> docket = new LinkedHashMap<>();

    /**
     * host信息
     **/
    private String host = "127.0.0.1";

    /**
     * 排序
     */
    private Integer order = 1;

    /**
     * 全局参数配置
     **/
    private List<GlobalOperationParameter> globalOperationParameters;


}
