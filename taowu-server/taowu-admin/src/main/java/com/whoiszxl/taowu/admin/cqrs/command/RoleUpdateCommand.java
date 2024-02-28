package com.whoiszxl.taowu.admin.cqrs.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
@Schema(description = "角色")
public class RoleUpdateCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Integer id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色代码")
    private String code;

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "业务状态")
    private Integer status;

    @Schema(description = "菜单ID列表")
    private List<Integer> menuIds;

}
