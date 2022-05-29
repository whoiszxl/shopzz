package com.whoiszxl.cqrs.dto;

import com.whoiszxl.entity.Channel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChannelDTO extends Channel {

    @ApiModelProperty("最近一小时成功发送的短信数量")
    private int lastSuccessNumInAnHour;

    private int lastSuccessNum;


    @ApiModelProperty("签名信息")
    private List<SignatureDTO> signatureDTOS;
    @ApiModelProperty("签名id集合")
    private List<String> signatureIds;

    @ApiModelProperty("模板信息")
    private List<TemplateDTO> templateDTOS;
    @ApiModelProperty("模板id集合")
    private List<String> templateIds;

    @ApiModelProperty("测试")
    private PlatformDTO platformDTO;


}
