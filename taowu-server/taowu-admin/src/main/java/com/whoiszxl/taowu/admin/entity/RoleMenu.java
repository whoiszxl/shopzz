package com.whoiszxl.taowu.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 角色与菜单关联表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
@Getter
@Setter
@TableName("sys_role_menu")
@Schema(description = "角色与菜单关联表")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "角色ID")
    private Integer roleId;

    @Schema(description = "菜单ID")
    private Integer menuId;

    public RoleMenu(Integer roleId, Integer menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
}
