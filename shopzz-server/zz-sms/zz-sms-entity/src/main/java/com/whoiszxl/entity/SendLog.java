package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.exception.ExceptionCatcher;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 短信发送日志表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Data
@TableName("sms_send_log")
@ApiModel(value = "SendLog对象", description = "短信发送日志表")
public class SendLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("渠道主键ID")
    private Long channelId;

    @ApiModelProperty("渠道平台")
    private String channelPlatform;

    @ApiModelProperty("渠道名称")
    private String channelName;

    @ApiModelProperty("模板")
    private String template;

    @ApiModelProperty("签名")
    private String signature;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("请求参数")
    private String request;

    @ApiModelProperty("返回参数")
    private String response;

    @ApiModelProperty("错误信息")
    private String error;

    @ApiModelProperty("耗时")
    private Long timeConsuming;

    @ApiModelProperty("日志ID")
    private Long apiLogId;

    @ApiModelProperty("状态: 0-失败 1-成功")
    private Integer status;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除, 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;

    public void checkResponse(String response) {
        if (response.startsWith("FAIL@#@")) {
            String[] responseArray = response.split("@#@");
            this.response = responseArray[2];
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError(responseArray[1]));
        } else {
            this.response = response;
        }
    }
}
