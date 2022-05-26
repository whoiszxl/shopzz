package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.Blacklist;
import com.whoiszxl.mapper.BlacklistMapper;
import com.whoiszxl.service.BlacklistService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信人工处理任务表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Service
public class BlacklistServiceImpl extends ServiceImpl<BlacklistMapper, Blacklist> implements BlacklistService {

}
