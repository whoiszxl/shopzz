package com.whoiszxl.entity;

import com.whoiszxl.enums.ActionIdEnum;
import com.whoiszxl.enums.ItemTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppAction {

    private ActionIdEnum actionId;

    private ItemTypeEnum itemType;

    private String item;

    private Long ts;
}
