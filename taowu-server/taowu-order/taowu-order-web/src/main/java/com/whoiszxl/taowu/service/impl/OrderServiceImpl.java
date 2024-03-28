package com.whoiszxl.taowu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.utils.*;
import com.whoiszxl.taowu.constants.OrderPayTypeConstants;
import com.whoiszxl.taowu.constants.OrderStatusConstants;
import com.whoiszxl.taowu.cqrs.command.OrderPayCommand;
import com.whoiszxl.taowu.cqrs.command.OrderSubmitCommand;
import com.whoiszxl.taowu.cqrs.dto.OrderCheckDTO;
import com.whoiszxl.taowu.cqrs.dto.OrderCreateInfoDTO;
import com.whoiszxl.taowu.cqrs.query.OrderListQuery;
import com.whoiszxl.taowu.cqrs.response.CartDetailApiResponse;
import com.whoiszxl.taowu.cqrs.response.CartItemVO;
import com.whoiszxl.taowu.cqrs.response.OrderItemResponse;
import com.whoiszxl.taowu.cqrs.response.OrderResponse;
import com.whoiszxl.taowu.dto.*;
import com.whoiszxl.taowu.entity.Order;
import com.whoiszxl.taowu.entity.OrderItem;
import com.whoiszxl.taowu.entity.PayInfoDc;
import com.whoiszxl.taowu.entity.PayInfoDcBuilder;
import com.whoiszxl.taowu.enums.CouponFullLimitedEnum;
import com.whoiszxl.taowu.enums.CouponTypeEnum;
import com.whoiszxl.taowu.factory.CreateDcAddressFactory;
import com.whoiszxl.taowu.feign.ProductFeignClient;
import com.whoiszxl.taowu.feign.PromotionFeignClient;
import com.whoiszxl.taowu.mapper.OrderItemMapper;
import com.whoiszxl.taowu.mapper.OrderMapper;
import com.whoiszxl.taowu.member.dto.MemberAddressFeignDTO;
import com.whoiszxl.taowu.member.feign.MemberFeignClient;
import com.whoiszxl.taowu.service.CartService;
import com.whoiszxl.taowu.service.DcPayInfoService;
import com.whoiszxl.taowu.service.OrderItemService;
import com.whoiszxl.taowu.service.OrderService;
import com.whoiszxl.taowu.state.LoggerOrderStateManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private final CartService cartService;

    private final OrderItemService orderItemService;

    private final ProductFeignClient productFeignClient;

    private final PromotionFeignClient promotionFeignClient;

    private final MemberFeignClient memberFeignClient;

    private final LoggerOrderStateManager loggerOrderStateManager;

    private final DcPayInfoService dcPayInfoService;

    private final CreateDcAddressFactory createDcAddressFactory;

    private final IdWorker idWorker;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    private final TokenHelper tokenHelper;

    @Override
    @Transactional
    public Long orderSubmit(OrderSubmitCommand orderSubmitCommand) {
        //1. 校验订单
        OrderCheckDTO orderCheckDTO = this.checkOrder(orderSubmitCommand);

        //2. 创建订单
        OrderCreateInfoDTO orderCreateInfoDTO = createOrderInfo(orderSubmitCommand);

        //3. 设置价格 TODO sku详细的拆分价格待计算
        orderCreateInfoDTO.setPayPrice(orderCheckDTO.getFinalDiscountPrice());
        orderCreateInfoDTO.getOrder().setTotalAmount(orderCheckDTO.getFinalPrice());
        orderCreateInfoDTO.getOrder().setFinalPayAmount(orderCheckDTO.getFinalDiscountPrice());

        //4. 订单与订单item入库
        this.save(orderCreateInfoDTO.getOrder());
        orderItemService.saveBatch(orderCreateInfoDTO.getOrderItemList());

        //5. 创建一个操作日志订单状态管理器，在订单状态流转到待付款状态时记录操作记录
        loggerOrderStateManager.create(orderCreateInfoDTO.getOrder());

        //6. 扣减库存
        subSaleStockAndAddLockStockBySkuId(orderCreateInfoDTO.getOrderItemList());

        //7. 优惠券核销
        if(orderSubmitCommand.getCouponId() != null) {
            writeOffCoupon(orderSubmitCommand.getCouponId(), orderCreateInfoDTO.getOrder().getId());
        }

        //8. 清空购物车选中的item
        cartService.clearCheckedCart();

        //9. TODO 发消息到MQ

        return orderCreateInfoDTO.getOrder().getId();
    }

    private void writeOffCoupon(Long couponId, Long orderId) {
        ResponseResult<Boolean> responseResult = promotionFeignClient.writeOffCoupon(couponId, orderId);
        if(!responseResult.isOk()) {
            ExceptionCatcher.catchServiceEx("核销优惠券失败");
        }
    }


    /**
     * 扣减库存
     * @param orderItemList
     */
    private void subSaleStockAndAddLockStockBySkuId(List<OrderItem> orderItemList) {
        List<ReduceStockFeignDTO> reduceStockFeignList = BeanUtil.copyToList(orderItemList, ReduceStockFeignDTO.class);
        ResponseResult<Boolean> responseResult = productFeignClient.subSaleStockAndAddLockStockBySkuId(reduceStockFeignList);
        if(!responseResult.isOk()) {
            ExceptionCatcher.catchServiceEx("扣减库存失败");
        }
    }

    /**
     * 创建订单和订单条目信息
     * @param orderSubmitCommand 提交的订单信息
     * @return
     */
    private OrderCreateInfoDTO createOrderInfo(OrderSubmitCommand orderSubmitCommand) {
        //1. 创建订单id和编号
        String orderNo = OrderUtils.makeOrderNo();
        long orderId = idWorker.nextId();

        //2. 创建订单和订单条目的基本信息
        Order order = buildOrder(orderId, orderNo, orderSubmitCommand);
        List<OrderItem> orderItems = buildOrderItems(orderId, orderNo);

        //3. 创建返回实体
        OrderCreateInfoDTO orderCreateInfoDTO = new OrderCreateInfoDTO();
        orderCreateInfoDTO.setOrder(order);
        orderCreateInfoDTO.setOrderItemList(orderItems);
        orderCreateInfoDTO.setFare(BigDecimal.ZERO);
        return orderCreateInfoDTO;
    }



    private List<OrderItem> buildOrderItems(long orderId, String orderNo) {
        //获取当前选中的购物车item列表
        CartDetailApiResponse cartDetail = cartService.getCartDetail();
        List<CartItemVO> cartItemVOList = cartDetail.getCartItemVOList();

        List<OrderItem> result = new ArrayList<>();
        for (CartItemVO item : cartItemVOList) {
            if(item.getChecked() == 1) {
                OrderItem orderItem = buildOrderItem(item);
                orderItem.setOrderId(orderId);
                orderItem.setOrderNo(orderNo);
                result.add(orderItem);
            }
        }

        return result;
    }

    /**
     * 创建订单详情单个条目
     * @param cartDTO 购物车条目
     * @return 订单条目
     */
    private OrderItem buildOrderItem(CartItemVO cartDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(idWorker.nextId());

        Long skuId = cartDTO.getSkuId();

        //配置sku信息
        orderItem.setSkuId(skuId);
        orderItem.setSkuName(cartDTO.getSkuName());

        //TODO 设置sku属性
        orderItem.setSkuAttrs("default");
        orderItem.setSkuPic(cartDTO.getSkuPic());
        orderItem.setSkuPrice(cartDTO.getPrice());
        orderItem.setQuantity(cartDTO.getQuantity());

        //配置spu信息
        orderItem.setProductId(cartDTO.getProductId());
        orderItem.setCategoryId(0L); //TODO

        orderItem.setCreatedBy("member");
        orderItem.setUpdatedBy("member");

        return orderItem;
    }

    private Order buildOrder(Long orderId, String orderNo, OrderSubmitCommand orderSubmitCommand) {
        Order order = new Order();
        order.setId(orderId);
        order.setOrderNo(orderNo);

        order.setMemberId(tokenHelper.getAppMemberId());
        order.setUsername("not set");
        order.setOrderStatus(OrderStatusConstants.UNKNOWN);

        //收货地址配置
        ResponseResult<MemberAddressFeignDTO> memberAddressResult = memberFeignClient.getMemberAddress(orderSubmitCommand.getAddressId());
        if(!memberAddressResult.isOk()) {
            ExceptionCatcher.catchServiceEx("地址无效");
        }
        AssertUtils.isNotNull(memberAddressResult.getData(), "地址无效");
        String addressJson = JsonUtil.toJson(memberAddressResult.getData());
        order.setSnapshotAddress(addressJson);
        order.setPayType(OrderPayTypeConstants.NOT_SET);
        order.setOrderComment(orderSubmitCommand.getOrderComment());
        return order;
    }

    /**
     * 校验订单
     * @param orderSubmitCommand 订单提交命令
     * @return
     */
    private OrderCheckDTO checkOrder(OrderSubmitCommand orderSubmitCommand) {
        OrderCheckDTO orderCheckDTO = new OrderCheckDTO();

        //1. 获取用户登录信息
        Long memberId = tokenHelper.getAppMemberId();

        //2. 获取购物车中选中的sku信息
        CartDetailApiResponse cartDetail = cartService.getCartDetail();
        List<CartItemVO> cartItemList = cartDetail.getCartItemVOList();
        if(ObjectUtils.isEmpty(cartItemList)) {
            ExceptionCatcher.catchServiceEx("购物车不存在选中商品");
        }

        List<Long> skuIds = cartItemList.stream().map(CartItemVO::getSkuId).collect(Collectors.toList());
        String feignParams = ParamUtils.array2Str(skuIds);
        ResponseResult<List<SkuFeignDTO>> skuListResult = productFeignClient.getSkuListBySkuIdList(feignParams);
        if(!skuListResult.isOk()) {
            ExceptionCatcher.catchServiceEx(skuListResult.getMessage());
        }
        List<SkuFeignDTO> skuFeignDTOList = skuListResult.getData();

        //2.1 校验订单中是否存在下架的SKU

        //2.2 校验订单中的商品库存是否足够
        ResponseResult<List<SkuStockFeignDTO>> skuStockResult = productFeignClient.getStockBySkuIdList(feignParams);
        if(!skuStockResult.isOk()) {
            ExceptionCatcher.catchServiceEx(skuStockResult.getMessage());
        }
        List<SkuStockFeignDTO> skuStockList = skuStockResult.getData();
        skuStockList.forEach(stock ->
            cartItemList.forEach(cartItemVO -> {
                if(stock.getSkuId().equals(cartItemVO.getSkuId())) {
                    if(stock.getSaleStockQuantity() < cartItemVO.getQuantity()) {
                        ExceptionCatcher.catchServiceEx("返回刷新下吧，有SKU库存不够了");
                    }
                }
            })
        );


        BigDecimal serverFinalPrice = BigDecimal.ZERO;
        for (SkuFeignDTO skuFeignDTO : skuFeignDTOList) {
            for (CartItemVO cartItemVO : cartItemList) {
                if(skuFeignDTO.getId().equals(cartItemVO.getSkuId())) {
                    serverFinalPrice = serverFinalPrice.add(skuFeignDTO.getPromotionPrice().multiply(new BigDecimal(cartItemVO.getQuantity())));
                }
            }
        }

        orderCheckDTO.setFinalPrice(serverFinalPrice);
        orderCheckDTO.setFinalDiscountPrice(serverFinalPrice);

        //3. 已使用优惠券逻辑
        Long couponId = orderSubmitCommand.getCouponId();
        if(couponId != null) {
            //3.1 校验优惠券是否有效
            ResponseResult<CouponFeignDTO> couponResult = promotionFeignClient.getCoupon(couponId);
            if(!couponResult.isOk()) {
                ExceptionCatcher.catchServiceEx("优惠券无效");
            }
            CouponFeignDTO couponDTO = couponResult.getData();
            LocalDateTime now = LocalDateTime.now();
            if(couponDTO.getStartTime().isBefore(now)) {
                ExceptionCatcher.catchServiceEx("未到使用时间");
            }

            if(couponDTO.getEndTime().isAfter(now)) {
                ExceptionCatcher.catchServiceEx("优惠券已过期");
            }

            //3.2 校验是否领取过、使用过
            ResponseResult<Boolean> isUsedResponse = promotionFeignClient.checkCouponIsUsed(couponId);
            if(!isUsedResponse.isOk()) {
                ExceptionCatcher.catchServiceEx("优惠券已领取过或使用过");
            }

            //3.3 校验订单是否满足使用阈值，如果是满减券或满减折扣券，并且是全场使用券，并且订单金额小于了使用阈值
            if((couponDTO.getType().equals(CouponTypeEnum.FULL_DISCOUNT.getCode())
                    || couponDTO.getType().equals(CouponTypeEnum.FULL_RATE.getCode()))
                    && couponDTO.getFullLimited().equals(CouponFullLimitedEnum.NOT_LIMIT.getCode())
                    && serverFinalPrice.compareTo(couponDTO.getUseThreshold()) < 0) {
                ExceptionCatcher.catchServiceEx("未达到优惠券使用阈值");
            }

            //3.4 校验订单是否满足使用阈值，如果是满减券或满减折扣券，并且是限制类目使用券
            if((couponDTO.getType().equals(CouponTypeEnum.FULL_DISCOUNT.getCode())
                    || couponDTO.getType().equals(CouponTypeEnum.FULL_RATE.getCode()))
                    && couponDTO.getFullLimited().equals(CouponFullLimitedEnum.LIMIT.getCode())) {

                //获取到优惠券能使用的分类
                List<Long> categoryIdList = couponDTO.getCategoryIdList();
                BigDecimal categoryPrice = BigDecimal.ZERO;

                for (SkuFeignDTO skuFeignDTO : skuFeignDTOList) {
                    if(categoryIdList.contains(skuFeignDTO.getCategoryId())) {
                        CartItemVO cartItemVO = cartItemList.stream().filter(e -> e.getSkuId().equals(skuFeignDTO.getId())).findFirst().get();
                        BigDecimal skuPrice = skuFeignDTO.getPromotionPrice().multiply(new BigDecimal(cartItemVO.getQuantity()));
                        categoryPrice = categoryPrice.add(skuPrice);
                    }
                }

                if(categoryPrice.compareTo(couponDTO.getUseThreshold()) < 0) {
                    ExceptionCatcher.catchServiceEx("未达到优惠券使用阈值");
                }
            }


            //4. 计算前端传入最终金额是否与服务端计算金额一致
            BigDecimal finalDiscountPrice = serverFinalPrice;

            if(CouponTypeEnum.FULL_DISCOUNT.getCode().equals(couponDTO.getType())
            || CouponTypeEnum.UNLIMITED.getCode().equals(couponDTO.getType())) {
                //满减和无门槛券直接减去折扣
                finalDiscountPrice = serverFinalPrice.subtract(couponDTO.getDiscountAmount());

                if(finalDiscountPrice.compareTo(orderSubmitCommand.getFinalPrice()) != 0) {
                    ExceptionCatcher.catchServiceEx("价格错误");
                }
            }

            if(CouponTypeEnum.FULL_RATE.getCode().equals(couponDTO.getType())) {
                //满减折扣直接乘以折扣比率
                finalDiscountPrice = serverFinalPrice.multiply(couponDTO.getDiscountRate());

                if(finalDiscountPrice.compareTo(orderSubmitCommand.getFinalPrice()) != 0) {
                    ExceptionCatcher.catchServiceEx("价格错误");
                }
            }

            orderCheckDTO.setFinalDiscountPrice(finalDiscountPrice);
        }

        return orderCheckDTO;
    }

    @Override
    public ResponseResult<PayInfoDc> pay(OrderPayCommand orderPayCommand) {
        //1. 判断订单状态是否能支付
        Order order = this.getById(orderPayCommand.getOrderId());
        if(!OrderStatusConstants.WAIT_FOR_PAY.equals(order.getOrderStatus())) {
            ExceptionCatcher.catchServiceEx("订单无法支付");
        }

        //2. 如果是数字货币支付，判断是否存在，不存在则创建
        if(OrderPayTypeConstants.DC_PAY.equals(orderPayCommand.getPayType())) {
            PayInfoDc payInfoDc = dcPayInfoService.getByOrderIdAndMemberId(order.getId(), order.getMemberId());
            if(payInfoDc != null) {
                return ResponseResult.buildSuccess(payInfoDc);
            }

            payInfoDc = PayInfoDcBuilder
                    .get() // 获取数字货币支付的信息构造器
                    .init(createDcAddressFactory, orderPayCommand.getDcName()) // 初始化基本的参数
                    .buildBaseData(order) // 构建订单的基本数据
                    .buildAddress(order) // 生成数字货币的支付地址
                    .rateCompute(order) // 数字货币和法币的汇率换算
                    .initStatus() // 初始化数字货币订单的支付状态为未上链
                    .create(); // 返回数字货币支付信息对象

            boolean saveFlag = dcPayInfoService.save(payInfoDc);
            return saveFlag ? ResponseResult.buildSuccess(payInfoDc) : ResponseResult.buildError("数字货币支付失败");
        }

        //3. TODO 微信支付，支付宝支付
        return ResponseResult.buildError("微信支付，支付宝支付 TODO");
    }

    @Override
    public Boolean updateStatus(Long id, Integer orderStatus) {
        Order order = new Order();
        order.setId(id);
        order.setOrderStatus(orderStatus);
        return this.updateById(order);
    }

    @Override
    public OrderInfoDTO getOrderInfo(Long orderId) {
        //获取订单信息
        Order order = this.getById(orderId);

        //获取订单条目信息
        List<OrderItem> orderItemList = orderItemService.list(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getOrderId, orderId));
        List<OrderItemDTO> orderItemDTOList = BeanUtil.copyToList(orderItemList, OrderItemDTO.class);

        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        orderInfoDTO.setOrderId(order.getId());
        orderInfoDTO.setMemberId(order.getMemberId());
        orderInfoDTO.setTotalAmount(order.getTotalAmount());
        orderInfoDTO.setOrderItemDTOList(orderItemDTOList);
        return orderInfoDTO;
    }

    @Override
    public List<OrderResponse> orderList(OrderListQuery orderListQuery) {
        Long memberId = StpUtil.getLoginIdAsLong();
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = Wrappers.<Order>lambdaQuery()
                .eq(Order::getMemberId, memberId)
                .orderByDesc(Order::getCreatedAt);
        if(orderListQuery.getOrderStatus() != null) {
            orderLambdaQueryWrapper.eq(Order::getOrderStatus, orderListQuery.getOrderStatus());
        }

        Page<Order> orderListPage = orderMapper.selectPage(new Page<>(orderListQuery.getPage(), orderListQuery.getSize()), orderLambdaQueryWrapper);
        List<OrderResponse> orderResponseList = cn.hutool.core.bean.BeanUtil.copyToList(orderListPage.getRecords(), OrderResponse.class);

        for (OrderResponse orderResponse : orderResponseList) {
            List<OrderItem> orderItemList = orderItemMapper.selectList(Wrappers.<OrderItem>lambdaQuery().eq(OrderItem::getOrderId, orderResponse.getId()));
            List<OrderItemResponse> orderItemResponseList = cn.hutool.core.bean.BeanUtil.copyToList(orderItemList, OrderItemResponse.class);
            orderResponse.setOrderItemResponseList(orderItemResponseList);
        }

        return orderResponseList;
    }


    @Override
    public OrderResponse orderDetail(Long orderId) {
        Long memberId = StpUtil.getLoginIdAsLong();
        Order order = orderMapper.selectOne(Wrappers.<Order>lambdaQuery()
                .eq(Order::getMemberId, memberId)
                .eq(Order::getId, orderId));

        OrderResponse orderResponse = BeanUtil.copyProperties(order, OrderResponse.class);

        List<OrderItem> orderItemList = orderItemMapper.selectList(Wrappers.<OrderItem>lambdaQuery()
                .eq(OrderItem::getOrderId, orderResponse.getId()));
        List<OrderItemResponse> orderItemResponseList = BeanUtil.copyToList(orderItemList, OrderItemResponse.class);

        orderResponse.setOrderItemResponseList(orderItemResponseList);
        return orderResponse;
    }
}
