package com.whoiszxl.taowu.admin.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 系统菜单
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
@Data
@Schema(description = "系统菜单")
public class MenuResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Integer id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "上级菜单ID")
    private Integer parentId;

    @Schema(description = "菜单类型 1-目录 2-菜单 3-按钮")
    private Integer type;

    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "路由参数")
    private String query;

    @Schema(description = "是否外链 1-是 0-否")
    private Integer isFrame;

    @Schema(description = "是否缓存 1-是 0-否")
    private Integer isCache;

    @Schema(description = "是否可见 1-是 0-否")
    private Integer visible;

    @Schema(description = "权限标识")
    private String permission;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "排序索引")
    private Integer sort;

    @Schema(description = "业务状态")
    private Integer status;



}
