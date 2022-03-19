package com.whoiszxl.swagger.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 离线文档配置
 *
 * @author whoiszxl
 * @date 2021/11/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Markdown {
    private Boolean enable = false;
    private String basePath = "classpath:markdown/*";
}
