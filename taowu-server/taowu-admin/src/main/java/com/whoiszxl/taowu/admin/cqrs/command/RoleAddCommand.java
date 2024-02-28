package com.whoiszxl.taowu.admin.cqrs.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "角色添加命令")
public class RoleAddCommand implements Serializable {

    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @Schema(description = "角色代码")
    @NotBlank(message = "角色代码不能为空")
    private String code;

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "菜单ID列表")
    private List<Integer> menuIds;
}
