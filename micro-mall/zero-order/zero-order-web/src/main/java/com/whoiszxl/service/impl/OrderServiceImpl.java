package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dto.CartDTO;
import com.whoiszxl.dto.InventorySkuDTO;
import com.whoiszxl.dto.MemberAddressDTO;
import com.whoiszxl.dto.MemberCouponDTO;
import com.whoiszxl.entity.Order;
import com.whoiszxl.entity.vo.OrderConfirmVO;
import com.whoiszxl.feign.CartFeignClient;
import com.whoiszxl.feign.InventoryFeignClient;
import com.whoiszxl.feign.MemberFeignClient;
import com.whoiszxl.feign.PromotionFeignClient;
import com.whoiszxl.mapper.OrderMapper;
import com.whoiszxl.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private InventoryFeignClient inventoryFeignClient;

    @Autowired
    private MemberFeignClient memberFeignClient;

    @Autowired
    private PromotionFeignClient promotionFeignClient;

    @Override
    public OrderConfirmVO getOrderConfirmData() {
        OrderConfirmVO result = new OrderConfirmVO();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        CompletableFuture<Void> itemAndStockFuture = CompletableFuture.supplyAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            //1. 获取购物车中所有的选中的sku商品
            ResponseResult<List<CartDTO>> cartResult = cartFeignClient.getCheckedCartItem();
            List<CartDTO> checkedCartList = cartResult.getData();
            result.setCheckItem(checkedCartList);
            return checkedCartList;

        }, executor).thenAcceptAsync(checkedCartList -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            //2. 获取库存信息
            List<Long> skuIds = checkedCartList.stream().map(CartDTO::getSkuId).collect(Collectors.toList());
            ResponseResult<List<InventorySkuDTO>> stockResult = inventoryFeignClient.getSaleStockQuantity(skuIds);
            List<InventorySkuDTO> stockSkuList = stockResult.getData();

            Map<Long, Integer> stock = stockSkuList.stream().collect(Collectors.toMap(InventorySkuDTO::getSkuId, InventorySkuDTO::getQuantity));
            result.setStocks(stock);
        }, executor);

        //3. 获取所有收货地址
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<MemberAddressDTO> addressList = memberFeignClient.listMemberAddress();
            result.setMemberAddressDTOS(addressList);
        }, executor);

        //4. 获取优惠券信息
        CompletableFuture<Void> couponFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<MemberCouponDTO> myCouponList = promotionFeignClient.getMyCoupon();
            result.setMyCoupons(myCouponList);
        }, executor);

        //5. 获取活动信息 TODO

        //6. 获取运费信息 TODO

        //8. 执行异步任务
        try {
            CompletableFuture.allOf(itemAndStockFuture, addressFuture, couponFuture).get();
        } catch (Exception e) {
            log.error("获取确认订单界面数据失败", e);
        }

        return result;
    }
}
