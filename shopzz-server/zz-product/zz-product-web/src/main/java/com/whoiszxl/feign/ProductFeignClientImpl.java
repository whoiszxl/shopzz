package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.dto.SpuFeignDTO;
import com.whoiszxl.entity.Spu;
import com.whoiszxl.service.SkuService;
import com.whoiszxl.service.SpuService;
import com.whoiszxl.utils.ParamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品feign client 实现
 *
 * @author whoiszxl
 * @date 2022/3/31
 */
@Slf4j
@RestController
public class ProductFeignClientImpl implements ProductFeignClient{

    @Autowired
    private SkuService skuService;

    @Autowired
    private SpuService spuService;

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public ResponseResult<SpuFeignDTO> getSpuBySpuId(Long spuId) {
        Spu spu = spuService.getById(spuId);
        SpuFeignDTO spuFeignDTO = dozerUtils.map(spu, SpuFeignDTO.class);
        return ResponseResult.buildSuccess(spuFeignDTO);
    }

    @Override
    public ResponseResult<List<SpuFeignDTO>> getSpuListBySpuIdList(String spuIds) {
        List spuList = ParamUtils.str2Array(spuIds);
        List<Spu> list = spuService.listByIds(spuList);
        List<SpuFeignDTO> spuFeignDTOList = dozerUtils.mapList(list, SpuFeignDTO.class);
        return ResponseResult.buildSuccess(spuFeignDTOList);
    }
}
