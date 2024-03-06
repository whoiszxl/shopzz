package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.common.utils.BeanUtil;
import com.whoiszxl.taowu.common.utils.JsonUtil;
import com.whoiszxl.taowu.common.utils.ParamUtils;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.cqrs.response.ColumnDetailApiResponse;
import com.whoiszxl.taowu.cqrs.response.ColumnSpuApiResponse;
import com.whoiszxl.taowu.dto.SpuFeignDTO;
import com.whoiszxl.taowu.entity.ProductColumn;
import com.whoiszxl.taowu.entity.ProductColumnSpu;
import com.whoiszxl.taowu.feign.ProductFeignClient;
import com.whoiszxl.taowu.mapper.ProductColumnMapper;
import com.whoiszxl.taowu.service.ProductColumnService;
import com.whoiszxl.taowu.service.ProductColumnSpuService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
@RequiredArgsConstructor
public class ProductColumnServiceImpl extends ServiceImpl<ProductColumnMapper, ProductColumn> implements ProductColumnService {

    private final RedisUtils redisUtils;

    private final ProductFeignClient productFeignClient;

    private final ProductColumnSpuService productColumnSpuService;

    @Override
    public ColumnDetailApiResponse detail(Long id) {
        String redisKey = RedisPrefixConstants.Marketing.COLUMN_DETAIL + id;
        String columnDetailJson = redisUtils.get(redisKey);
        if(StringUtils.isBlank(columnDetailJson)) {

            synchronized (ProductColumnServiceImpl.class) {
                columnDetailJson = redisUtils.get(redisKey);

                if(StringUtils.isBlank(columnDetailJson)) {
                    ColumnDetailApiResponse response = new ColumnDetailApiResponse();

                    //从DB获取
                    ProductColumn productColumn = super.getById(id);
                    BeanUtil.copyProperties(productColumn, response);

                    List<ProductColumnSpu> productColumnSpuList = productColumnSpuService.list(Wrappers.<ProductColumnSpu>lambdaQuery().eq(ProductColumnSpu::getColumnId, id));
                    if(ObjectUtils.isEmpty(productColumnSpuList)) {
                        ExceptionCatcher.catchServiceEx("专栏下无商品");
                    }

                    List<Long> spuIds = productColumnSpuList.stream().map(ProductColumnSpu::getSpuId).collect(Collectors.toList());
                    String feignParams = ParamUtils.array2Str(spuIds);
                    ResponseResult<List<SpuFeignDTO>> feignResponse = productFeignClient.getSpuListBySpuIdList(feignParams);
                    if(!feignResponse.isOk()) {
                        ExceptionCatcher.catchServiceEx("feignResponse.getMessage()");
                    }

                    List<SpuFeignDTO> spuFeignDTOList = feignResponse.getData();
                    List<ColumnSpuApiResponse> columnSpuApiResponses = BeanUtil.copyToList(spuFeignDTOList, ColumnSpuApiResponse.class);

                    response.setSpuList(columnSpuApiResponses);

                    columnDetailJson = JsonUtil.toJson(response);
                    redisUtils.set(redisKey, columnDetailJson);
                }

            }
        }

        return JsonUtil.fromJson(columnDetailJson, ColumnDetailApiResponse.class);
    }
}
