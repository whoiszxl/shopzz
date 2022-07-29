package com.whoiszxl.entity;

import com.whoiszxl.enums.DisplayTypeEnum;
import com.whoiszxl.enums.ItemTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppDisplay {

    ItemTypeEnum itemType;

    String item;

    DisplayTypeEnum displayType;

    Integer order;
}
