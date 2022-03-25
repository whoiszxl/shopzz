package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.Warehouse;
import com.whoiszxl.entity.WarehouseShelf;
import com.whoiszxl.mapper.WarehouseMapper;
import com.whoiszxl.mapper.WarehouseShelfMapper;
import com.whoiszxl.service.WarehouseService;
import com.whoiszxl.service.WarehouseShelfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 仓库货架服务实现
 *
 * @author whoiszxl
 * @date 2022/3/18
 */
@Slf4j
@Service
public class WarehouseShelfServiceImpl extends ServiceImpl<WarehouseShelfMapper, WarehouseShelf> implements WarehouseShelfService {

}
