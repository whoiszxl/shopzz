package com.whoiszxl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.cqrs.response.ColumnDetailApiResponse;
import com.whoiszxl.cqrs.response.ColumnSpuApiResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.dto.SpuFeignDTO;
import com.whoiszxl.entity.ProductColumn;
import com.whoiszxl.entity.ProductColumnSpu;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.feign.ProductFeignClient;
import com.whoiszxl.mapper.ProductColumnMapper;
import com.whoiszxl.service.ProductColumnService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.service.ProductColumnSpuService;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.ParamUtils;
import com.whoiszxl.utils.RedisUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品专栏表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Service
public class ProductColumnServiceImpl extends ServiceImpl<ProductColumnMapper, ProductColumn> implements ProductColumnService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private ProductColumnSpuService productColumnSpuService;

    @Override
    public ColumnDetailApiResponse detail(Long id) {
        String redisKey = RedisKeyPrefixConstants.COLUMN_DETAIL + id;
        String columnDetailJson = redisUtils.get(redisKey);
        if(StringUtils.isBlank(columnDetailJson)) {

            synchronized (ProductColumnServiceImpl.class) {
                columnDetailJson = redisUtils.get(redisKey);

                if(StringUtils.isBlank(columnDetailJson)) {
                    ColumnDetailApiResponse response = new ColumnDetailApiResponse();

                    //从DB获取
                    ProductColumn productColumn = super.getById(id);
                    dozerUtils.map(productColumn, response);

                    List<ProductColumnSpu> productColumnSpuList = productColumnSpuService.list(Wrappers.<ProductColumnSpu>lambdaQuery().eq(ProductColumnSpu::getColumnId, id));
                    if(ObjectUtils.isEmpty(productColumnSpuList)) {
                        ExceptionCatcher.catchValidateEx(ResponseResult.buildError("专栏下无商品"));
                    }

                    List<Long> spuIds = productColumnSpuList.stream().map(s -> s.getSpuId()).collect(Collectors.toList());
                    String feignParams = ParamUtils.array2Str(spuIds);
                    ResponseResult<List<SpuFeignDTO>> feignResponse = productFeignClient.getSpuListBySpuIdList(feignParams);
                    if(!feignResponse.isOk()) {
                        ExceptionCatcher.catchValidateEx(ResponseResult.buildError(feignResponse.getMessage()));
                    }

                    List<SpuFeignDTO> spuFeignDTOList = feignResponse.getData();
                    List<ColumnSpuApiResponse> columnSpuApiResponses = dozerUtils.mapList(spuFeignDTOList, ColumnSpuApiResponse.class);

                    response.setSpuList(columnSpuApiResponses);

                    columnDetailJson = JsonUtil.toJson(response);
                    redisUtils.set(redisKey, columnDetailJson);
                }

            }
        }

        return JsonUtil.fromJson(columnDetailJson, ColumnDetailApiResponse.class);
    }
}
