package com.whoiszxl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppMain {

    /** 客户端日志时间 */
    private Long ts;

    /** app公共信息 */
    private AppCommon appCommon;

    private AppPage appPage;

    private AppStart appStart;

    private List<AppDisplay> displayList;

    private List<AppAction> actionList;
}
