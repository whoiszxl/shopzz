package com.whoiszxl.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.exception.ExceptionCatcher;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 超类基础实体
 *
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SuperEntity<T> implements Serializable, Cloneable {
    public static final String FIELD_ID = "id";
    public static final String CREATED_AT = "createdAt";
    public static final String CREATED_BY = "createdBy";

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = Update.class)
    protected T id;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    protected LocalDateTime createdAt;

    @ApiModelProperty(value = "创建人ID")
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    protected T createdBy;

    @Override
    public Object clone() {
        //支持克隆  提高性能  仅仅是浅克隆
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("克隆失败"));
            return null;
        }
    }
}
