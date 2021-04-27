package com.whoiszxl.zero.service.impl;

import com.whoiszxl.zero.dao.OrderDao;
import com.whoiszxl.zero.entity.param.SubmitOrderParam;
import com.whoiszxl.zero.entity.vo.OrderConfirmVO;
import com.whoiszxl.zero.entity.vo.OrderVO;
import com.whoiszxl.zero.service.OrderService;
import com.whoiszxl.zero.bean.Result;
import com.whoiszxl.zero.dto.CartDTO;
import com.whoiszxl.zero.dto.CartListParam;
import com.whoiszxl.zero.feign.CartFeign;
import com.whoiszxl.zero.feign.MemberAddressFeign;
import com.whoiszxl.zero.feign.MemberFeign;
import com.whoiszxl.zero.utils.IdWorker;
import com.whoiszxl.zero.utils.JwtUtils;
import com.whoiszxl.zero.vo.MemberAddressVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CartFeign cartFeign;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MemberAddressFeign memberAddressFeign;

    @Autowired
    private MemberFeign memberFeign;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public OrderVO submitOrder(SubmitOrderParam param) {
        return null;
    }


    @Override
    public OrderConfirmVO confirmOrder() {
        OrderConfirmVO confirmVO = new OrderConfirmVO();
        Long memberId = JwtUtils.getMemberId();
        //1. 拿到用户的收货地址
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
            Result<List<MemberAddressVO>> addressResult = memberAddressFeign.list();
            List<MemberAddressVO> addressVOS = addressResult.getData();
            confirmVO.setAddressList(addressVOS);
        }, executor);

        //2. 获取所有选中的商品
        CompletableFuture<Void> itemAndStockFuture = CompletableFuture.supplyAsync(() -> {
            Result<List<CartDTO>> cartResult = cartFeign.cartCheckedList(CartListParam.builder().memberId(memberId).build());
            List<CartDTO> cartDTOS = cartResult.getData();
            confirmVO.setCheckedItem(cartDTOS);
            return cartDTOS;

        }, executor).thenAcceptAsync(items -> {
            //3. TODO 校验库存

        }, executor);

        //4. TODO 获取积分信息


        //5. TODO 获取优惠券信息
        //6. 计算总价
        try {
            CompletableFuture.allOf(itemAndStockFuture, addressFuture).get();
        } catch (Exception e) {
            log.error("确认订单失败", e);
        }

        return confirmVO;
    }
}
