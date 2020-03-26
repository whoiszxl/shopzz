package com.whoiszxl.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.order.entity.ReturnCause;
import com.whoiszxl.order.mapper.ReturnCauseMapper;
import com.whoiszxl.order.service.ReturnCauseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-26
 */
@Slf4j
@Service
@Transactional
public class ReturnCauseServiceImpl extends ServiceImpl<ReturnCauseMapper, ReturnCause> implements ReturnCauseService {

}
