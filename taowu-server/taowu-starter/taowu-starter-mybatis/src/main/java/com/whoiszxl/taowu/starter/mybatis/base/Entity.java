package com.whoiszxl.taowu.starter.mybatis.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 基础实体
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
public class Entity<T> {

    public static final String FIELD_ID = "id";
    public static final String CREATED_AT = "createdAt";
    public static final String CREATED_BY = "createdBy";
    public static final String UPDATED_AT = "updatedAt";
    public static final String UPDATED_BY = "updatedBy";
    public static final String VERSION = "version";
    public static final String IS_DELETED = "is_deleted";

    @TableId(value = "id", type = IdType.INPUT)
    @Schema(description = "主键")
    @NotNull(message = "id不能为空", groups = Update.class)
    protected T id;

    @Schema(description = "最后修改时间")
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updatedAt;

    @Schema(description = "最后修改人ID")
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    protected T updatedBy;

    @Schema(description = "创建时间")
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    protected LocalDateTime createdAt;

    @Schema(description = "创建人ID")
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    protected T createdBy;

    @Schema(description = "乐观锁版本号")
    @TableField(value = "version")
    protected Long version;

    @Schema(description = "是否删除")
    @TableField(value = "is_deleted")
    protected Integer isDeleted;

    public Entity(T id, LocalDateTime createdAt, T createdBy, LocalDateTime updatedAt, T updatedBy, Long version, Integer isDeleted) {
        this.id = id;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.version = version;
        this.isDeleted = isDeleted;
    }

    public Entity() {
    }

}
