package com.whoiszxl.taowu.feign;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.utils.AssertUtils;
import com.whoiszxl.taowu.common.utils.BeanUtil;
import com.whoiszxl.taowu.common.utils.IdWorker;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.dto.CountFeignCommand;
import com.whoiszxl.taowu.entity.Counter;
import com.whoiszxl.taowu.service.ICounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CounterFeignClientImpl implements CounterFeignClient {

    private final ICounterService counterService;

    private final IdWorker idWorker;

    private final RedisUtils redisUtils;

    /**
     * 这种直接更新数据库的方式，在高并发的场景下会导致数据库支撑不住，所以可以考虑加一层缓存，
     * 比如说 GuavaCache 本地缓存，计数直接写入本地缓存中，在缓存失效时再统一刷入数据库中。
     * 或者使用 Redis，再定时扫描 Redis，将数据刷入数据库中。
     */
    @Override
    public ResponseResult<Boolean> countNumber(CountFeignCommand command) {
        // 判断数据库中是否存在记录，如果存在则更新，不存在则新增
        Counter counter = counterService.getOne(Wrappers.<Counter>lambdaQuery()
                .eq(Counter::getDimType, command.getDimType())
                .eq(Counter::getObjType, command.getObjType())
                .eq(Counter::getObjId, command.getObjId()));

        boolean saveOrUpdateFlag = false;
        if(ObjUtil.isNotNull(counter)) {
            // 更新
            saveOrUpdateFlag = counterService.update(Wrappers.<Counter>lambdaUpdate()
                    .eq(Counter::getId, counter.getId())
                    .setSql("count_value = count_value + " + command.getRegulation()));

        }else {
            // 新增
            AssertUtils.isTrue(command.getRegulation() > 0, "计数规则错误");
            counter = BeanUtil.copyProperties(command, Counter.class);
            counter.setCountValue(1L);
            counter.setId(idWorker.nextId());
            saveOrUpdateFlag = counterService.save(counter);
        }

        // 将计数数据保存到缓存中
        redisUtils.hIncrBy(
                RedisPrefixConstants.Counter.COUNTER_PREFIX + command.getDimType() + ":" + command.getObjId(),
                    String.valueOf(command.getObjType()),
                    Long.valueOf(command.getRegulation()));

        return ResponseResult.buildByFlag(saveOrUpdateFlag);
    }
}
