package com.whoiszxl.query.model.qry;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员查询实体
 *
 * @author whoiszxl
 * @date 2022/4/1
 */
@Data
@ApiModel("会员查询实体")
public class MemberQuery extends PageQuery {

    @ApiModelProperty("会员名")
    private String username;

}
