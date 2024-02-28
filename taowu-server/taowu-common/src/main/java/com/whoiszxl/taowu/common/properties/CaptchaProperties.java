package com.whoiszxl.taowu.common.properties;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * 验证码配置
 * @author whoiszxl
 */
@Data
@Component
@ConfigurationProperties(prefix = "taowu.captcha")
public class CaptchaProperties {

    /** 图形验证码配置 */
    private CaptchaImage image;

    /** 邮箱验证码配置 */
    private CaptchaMail mail;

    @Data
    public static class CaptchaImage {

        /** 类型 */
        private CaptchaImageTypeEnum type;

        /** 内容长度 */
        private int length;

        /** 过期时间 */
        private long expirationInMinutes;

        /** 宽度 */
        private int width = 111;

        /** 高度 */
        private int height = 36;

        /** 字体 */
        private String fontName;

        /** 字体大小 */
        private int fontSize = 25;

        /**
         * 获取验证码信息
         * @return 验证码信息
         */
        public Captcha getCaptcha() {
            Captcha captcha = ReflectUtil.newInstance(type.getClazz(), this.width, this.height);
            captcha.setLen(length);
            if (StrUtil.isNotBlank(this.fontName)) {
                captcha.setFont(new Font(this.fontName, Font.PLAIN, this.fontSize));
            }
            return captcha;
        }
    }

    /**
     * 邮箱验证码配置
     */
    @Data
    public static class CaptchaMail {
        /** 内容长度 */
        private int length;

        /** 过期时间 */
        private long expirationInMinutes;

        /** 限制时间 */
        private long limitInSeconds;

        /** 模板路径 */
        private String templatePath;
    }

    @Getter
    @RequiredArgsConstructor
    private enum CaptchaImageTypeEnum {

        /** 算术 */
        ARITHMETIC(ArithmeticCaptcha.class),

        /**
         * 中文
         */
        CHINESE(ChineseCaptcha.class),

        /**
         * 中文GIF
         */
        CHINESE_GIF(ChineseGifCaptcha.class),

        /**
         * 英文GIF
         */
        GIF(GifCaptcha.class),

        /** 特殊类型 */
        SPEC(SpecCaptcha.class),;

        private final Class<? extends Captcha> clazz;
    }
    

}
