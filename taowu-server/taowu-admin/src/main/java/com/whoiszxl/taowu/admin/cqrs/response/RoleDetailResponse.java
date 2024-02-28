package com.whoiszxl.taowu.admin.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@Schema(description = "角色")
public class RoleDetailResponse implements Serializable {

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

    @Schema(description = "创建者")
    private String createdBy;

    @Schema(description = "更新者")
    private String updatedBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    @Schema(description = "菜单ID集合")
    private List<Integer> menuIds;



}
