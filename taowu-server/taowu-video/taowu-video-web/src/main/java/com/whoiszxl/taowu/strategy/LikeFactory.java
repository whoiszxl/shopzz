package com.whoiszxl.taowu.strategy;

import com.whoiszxl.taowu.enums.LikeTypeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
/**
 * 点赞工厂
 *
 * @author whoiszxl
 * @date 2021/12/8
 */
@Component
public class LikeFactory implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;
    private final Map<String, LikeStrategy> LIKE_STRATEGY_MAP = new HashMap<>();

    public LikeStrategy getLikeStrategy(Integer type){
        return LIKE_STRATEGY_MAP.get(LikeTypeEnum.getTypeName(type));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, LikeStrategy> likeStrategyMap = applicationContext.getBeansOfType(LikeStrategy.class);
        LIKE_STRATEGY_MAP.put(LikeTypeEnum.VIDEO.getTypeName(), likeStrategyMap.get("videoLike"));
        LIKE_STRATEGY_MAP.put(LikeTypeEnum.COMMENT.getTypeName(), likeStrategyMap.get("commentLike"));
    }
}