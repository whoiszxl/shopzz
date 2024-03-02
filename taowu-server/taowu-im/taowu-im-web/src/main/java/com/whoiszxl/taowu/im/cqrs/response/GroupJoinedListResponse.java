package com.whoiszxl.taowu.im.cqrs.response;

import com.whoiszxl.taowu.im.entity.Group;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;


/**
 * 成员加入群组列表返回实体
 * @author whoiszxl
 */
@Data
@Builder
@AllArgsConstructor
@Schema(description = "成员加入群组列表返回实体")
public class GroupJoinedListResponse {

    @Schema(description = "群组总数")
    private Integer total;

    @Schema(description = "群组总数")
    private List<Group> groupList;
}
