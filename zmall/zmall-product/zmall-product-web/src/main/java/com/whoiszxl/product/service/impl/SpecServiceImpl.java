package com.whoiszxl.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.product.mapper.SpecMapper;
import com.whoiszxl.product.entity.MallSpec;
import com.whoiszxl.product.service.SpecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-20
 */
@Slf4j
@Service
@Transactional
public class SpecServiceImpl extends ServiceImpl<SpecMapper, MallSpec> implements SpecService {

    @Autowired
    private SpecMapper specMapper;

    @Override
    public List<Map> findSpecListByCategoryName(String categoryName) {
        List<Map> specList = specMapper.findSpecListByCategoryName(categoryName);
        for (Map spec : specList) {
            String[] options = spec.get("options").toString().split(",");
            spec.put("options", options);
        }
        return specList;
    }
}
