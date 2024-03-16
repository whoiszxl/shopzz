package com.whoiszxl.taowu.configuration;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.houbb.sensitive.word.api.IWordAllow;
import com.github.houbb.sensitive.word.api.IWordDeny;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.houbb.sensitive.word.support.allow.WordAllows;
import com.github.houbb.sensitive.word.support.deny.WordDenys;
import com.github.houbb.sensitive.word.support.ignore.SensitiveWordCharIgnores;
import com.github.houbb.sensitive.word.support.resultcondition.WordResultConditions;
import com.github.houbb.sensitive.word.support.tag.WordTags;
import com.whoiszxl.taowu.entity.SensitiveWord;
import com.whoiszxl.taowu.enums.SensitiveKeywordTypeEnum;
import com.whoiszxl.taowu.mapper.SensitiveWordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 敏感词配置类
 * 参考开源项目：https://github.com/houbb/sensitive-word?tab=readme-ov-file#%E8%87%AA%E5%AE%9A%E4%B9%89%E6%95%B0%E6%8D%AE%E6%BA%90
 */
@Configuration
@RequiredArgsConstructor
public class SensitiveWordConfiguration {

    private final SensitiveWordMapper sensitiveWordMapper;

    /**
     * 配置项参考：<a href="https://github.com/houbb/sensitive-word?tab=readme-ov-file#%E9%85%8D%E7%BD%AE%E8%AF%B4%E6%98%8E">...</a>
     */
    @Bean
    public SensitiveWordBs sensitiveWordBs(IWordAllow myAllows, IWordDeny myDenys) {
        return SensitiveWordBs.newInstance()
                .wordAllow(WordAllows.chains(WordAllows.defaults(), myAllows))
                .wordDeny(WordDenys.chains(WordDenys.defaults(), myDenys))
                .ignoreCase(true)
                .ignoreWidth(true)
                .ignoreNumStyle(true)
                .ignoreChineseStyle(true)
                .ignoreEnglishStyle(true)
                .ignoreRepeat(false)
                .enableNumCheck(true)
                .enableEmailCheck(true)
                .enableUrlCheck(true)
                .enableWordCheck(true)
                .numCheckLen(8)
                .wordTag(WordTags.none())
                .charIgnore(SensitiveWordCharIgnores.defaults())
                .wordResultCondition(WordResultConditions.alwaysTrue())
                .init();
    }


    @Bean
    public IWordDeny myDenys() {
        return () -> {
            List<SensitiveWord> sensitiveWords = sensitiveWordMapper
                    .selectList(Wrappers.<SensitiveWord>lambdaQuery()
                            .eq(SensitiveWord::getKeywordType, SensitiveKeywordTypeEnum.DENY.getCode()));
            return sensitiveWords.stream().map(SensitiveWord::getKeyword).collect(Collectors.toList());
        };
    }

    @Bean
    public IWordAllow myAllows() {
        return () -> {
            List<SensitiveWord> sensitiveWords = sensitiveWordMapper
                    .selectList(Wrappers.<SensitiveWord>lambdaQuery()
                            .eq(SensitiveWord::getKeywordType, SensitiveKeywordTypeEnum.ALLOW.getCode()));
            return sensitiveWords.stream().map(SensitiveWord::getKeyword).collect(Collectors.toList());
        };
    }

}
