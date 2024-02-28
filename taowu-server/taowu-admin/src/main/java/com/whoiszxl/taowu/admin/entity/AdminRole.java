package com.whoiszxl.taowu.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 管理员&角色关联表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
@Data
@TableName("sys_admin_role")
@Schema(description = "管理员&角色关联表")
public class AdminRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "角色ID")
    private Integer roleId;

    @Schema(description = "管理员ID")
    private Integer adminId;

    public AdminRole(Integer roleId, Integer adminId) {
        this.roleId = roleId;
        this.adminId = adminId;
    }
}
