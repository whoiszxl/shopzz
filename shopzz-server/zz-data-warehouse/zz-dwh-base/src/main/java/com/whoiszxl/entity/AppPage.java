package com.whoiszxl.entity;

import com.whoiszxl.enums.DisplayTypeEnum;
import com.whoiszxl.enums.ItemTypeEnum;
import com.whoiszxl.enums.PageIdEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppPage {

    private PageIdEnum lastPageId;

    private PageIdEnum currentPageId;

    private ItemTypeEnum itemType;

    private String item;

    private Integer duringTime;

    private DisplayTypeEnum displayType;

}
