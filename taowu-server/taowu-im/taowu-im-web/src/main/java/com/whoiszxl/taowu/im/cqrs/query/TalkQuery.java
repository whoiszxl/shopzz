package com.whoiszxl.taowu.im.cqrs.query;

import com.whoiszxl.taowu.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 群组信息查询参数
 * @author whoiszxl
 */
@Data
@Schema(description = "对话查询参数")
public class TalkQuery extends PageQuery {


}
