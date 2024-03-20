package com.whoiszxl.taowu.cqrs.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * 视频表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-13
 */
@Data
@Schema(description = "视频返回实体")
public class VideoResponse implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "视频主键ID")
    private Long id;

    @Schema(description = "会员ID")
    private Long memberId;

    @Schema(description = "用户名称")
    private String memberUsername;

    @Schema(description = "用户头像")
    private String memberAvatar;

    @Schema(description = "文字内容")
    private String descs;

    @Schema(description = "视频封面")
    private String cover;

    @Schema(description = "视频文件地址")
    private String videoUrl;

    @Schema(description = "图片文件地址，json数组保存")
    private String imageUrl;

    @Schema(description = "视频秒数")
    private Float seconds;

    @Schema(description = "视频宽度")
    private Integer width;

    @Schema(description = "视频高度")
    private Integer height;

    @Schema(description = "资源类型：（1:视频 2:图片）")
    private Integer resourceType;

    @Schema(description = "观看类型：（1:所有人 2:私密 3:好友可见 4:部分可见 5:对谁不可见）")
    private Integer watchType;

    @Schema(description = "可见用户的id列表，使用逗号 , 分隔")
    private String canWatchMember;

    @Schema(description = "不可见用户的id列表，使用逗号 , 分隔")
    private String cannotWatchMember;

    @Schema(description = "来自什么渠道")
    private String channel;

    @Schema(description = "定位地址")
    private String address;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "ip地址")
    private String ip;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "up主信息")
    private Map<Object, Object> counterInfoMap;

}
