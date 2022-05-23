package com.whoiszxl.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

/**
 * 文件类型枚举
 *
 * @author whoiszxl
 * @date 2022/3/23
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "FileType", description = "文件类型-枚举")
public enum FileType implements BaseEnum {

    /**
     * IMAGE="图片"
     */
    IMAGE("图片", 2),
    /**
     * VIDEO="视频"
     */
    VIDEO("视频", 3),
    /**
     * AUDIO="音频"
     */
    AUDIO("音频", 4),
    /**
     * DOC="文档"
     */
    DOC("文档", 5),
    /**
     * OTHER="其他"
     */
    OTHER("其他", 6),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "编码")
    private Integer num;


    /**
     * 根据当前枚举的name匹配
     */
    public static FileType match(String val, FileType def) {
        return Stream.of(values()).parallel().filter(item -> item.name().equalsIgnoreCase(val)).findAny().orElse(def);
    }

    public static FileType get(String val) {
        return match(val, null);
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "IMAGE,VIDEO,AUDIO,DOC,OTHER", example = "IMAGE")
    public String getCode() {
        return this.name();
    }

    public Integer getNum() {
        return this.num;
    }

}