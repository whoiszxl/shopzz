package com.whoiszxl.cqrs.dto;

import com.whoiszxl.entity.Platform;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "接入平台")
public class PlatformDTO extends Platform {

}
