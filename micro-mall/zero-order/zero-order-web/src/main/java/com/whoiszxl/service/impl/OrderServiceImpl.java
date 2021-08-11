package com.whoiszxl.service.impl;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.constants.OrderPayTypeConstants;
import com.whoiszxl.constants.OrderStatusConstants;
import com.whoiszxl.dto.OrderCreateInfoDTO;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dto.*;
import com.whoiszxl.entity.DcPayInfo;
import com.whoiszxl.entity.DcPayInfoBuilder;
import com.whoiszxl.entity.Order;
import com.whoiszxl.entity.OrderItem;
import com.whoiszxl.entity.vo.OrderConfirmVO;
import com.whoiszxl.entity.vo.OrderCreateInfo;
import com.whoiszxl.entity.vo.OrderPayVO;
import com.whoiszxl.entity.vo.OrderSubmitVO;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.factory.CreateDcAddressFactory;
import com.whoiszxl.feign.*;
import com.whoiszxl.mapper.OrderMapper;
import com.whoiszxl.mq.MQConstants;
import com.whoiszxl.mq.MQEnum;
import com.whoiszxl.mq.MQSender;
import com.whoiszxl.mq.MQSenderFactory;
import com.whoiszxl.service.DcPayInfoService;
import com.whoiszxl.service.OrderItemService;
import com.whoiszxl.service.OrderService;
import com.whoiszxl.service.PayInfoService;
import com.whoiszxl.state.LoggerOrderStateManager;
import com.whoiszxl.utils.BeanCopierUtils;
import com.whoiszxl.utils.IdWorker;
import com.whoiszxl.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private LoggerOrderStateManager orderStateManager;

    @Autowired
    private MQSenderFactory mqSenderFactory;

    @Autowired
    private DcPayInfoService dcPayInfoService;

    @Autowired
    private PayInfoService payInfoService;

    @Autowired
    private CreateDcAddressFactory createDcAddressFactory;

    @Autowired
    private IdWorker idWorker;

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

    @Override
    @Transactional
    public ResponseResult pay(OrderPayVO orderPayVO) {
        //1. 判断订单状态是否能支付
        Order order = this.getById(orderPayVO.getOrderId());
        if(!OrderStatusConstants.WAIT_FOR_PAY.equals(order.getOrderStatus())) {
            return ResponseResult.buildError("订单无法支付");
        }

        //2. 如果是数字货币支付，判断是否存在，不存在则创建
        if(OrderPayTypeConstants.DC_PAY.equals(orderPayVO.getPayType())) {
            DcPayInfo dcPayInfo = dcPayInfoService.getByOrderIdAndMemberId(order.getId(), order.getMemberId());
            if(dcPayInfo != null) {
                return ResponseResult.buildSuccess(dcPayInfo);
            }

            dcPayInfo = DcPayInfoBuilder
                    .get()
                    .init(createDcAddressFactory, orderPayVO.getDcName())
                    .buildBaseData(order)
                    .buildAddress(order)
                    .initStatus()
                    .create();

            //汇率换算
            BigDecimal rateAmount = rateCompute(order.getTotalAmount());
            dcPayInfo.setTotalAmount(rateAmount);

            boolean saveFlag = dcPayInfoService.save(dcPayInfo);
            if(saveFlag) {
                return ResponseResult.buildSuccess(dcPayInfo);
            }
            return ResponseResult.buildError("数字货币支付失败");
        }

        //3. TODO 如果是普通支付

        return ResponseResult.buildError("TODO");
    }

    @Override
    public OrderInfoDTO getOrderInfo(Long orderId) {
        //获取订单信息
        Order order = this.getById(orderId);

        //获取订单条目信息
        List<OrderItem> orderItemList = orderItemService.list(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getOrderId, orderId));
        List<OrderItemDTO> orderItemDTOList = BeanCopierUtils.copyListProperties(orderItemList, OrderItemDTO::new);

        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        orderInfoDTO.setOrderId(order.getId());
        orderInfoDTO.setMemberId(order.getMemberId());
        orderInfoDTO.setTotalAmount(order.getTotalAmount());
        orderInfoDTO.setOrderItemDTOList(orderItemDTOList);
        return orderInfoDTO;
    }

    @Override
    @Transactional
    public String submitOrder(OrderSubmitVO orderSubmitVo) {
        //0. 获取当前登录用户的信息
        MemberDetailDTO memberDetailDTO = memberFeignClient.getMemberInfo();
        //1. 创建订单和订单详细条目
        OrderCreateInfo orderCreateInfo = createOrderInfo(memberDetailDTO, orderSubmitVo);

        //2. 验证提交的价格和计算的价格是否一致
        if(orderSubmitVo.getTotalAmount().compareTo(orderCreateInfo.getPayPrice()) != 0) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("价格计算异常"));
        }

        //3. 订单与订单详细条目入库
        Order order = orderCreateInfo.getOrder();
        this.save(order);
        orderItemService.saveBatch(orderCreateInfo.getOrderItemList());

        //4. 创建一个操作日志订单状态管理器，在订单状态流转到待付款状态时记录操作记录
        orderStateManager.create(order);

        //5. 通知库存中心订单提交了，锁定库存中心的SKU库存
        OrderCreateInfoDTO orderRequestParam = orderCreateInfo.clone(OrderCreateInfoDTO.class);
        ResponseResult updateInventoryResult = inventoryFeignClient.notifySubmitOrderEvent(orderRequestParam);
        if(!updateInventoryResult.isOk()) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("库存不足"));
        }

        //6. 使用优惠券
        ResponseResult<Boolean> useCouponResult = promotionFeignClient.useCoupon(orderSubmitVo.getCouponId());
        if(!useCouponResult.isOk()) {
            log.error("优惠券使用失败：{}", useCouponResult.getMessage());
        }

        //7. 发送Kafka消息到WMS中心进行处理
        //7.1 通过商品的SKU ID查询到货位库存的明细条目，并进行遍历，一个SKU可能在多个货位上
        //7.2 创建出需要拣货的条目和发货的条目并进行批量入库
        //7.4 更新wms中心的库存
        OrderCreateInfoDTO orderCreateInfoDTO = new OrderCreateInfoDTO();
        BeanCopierUtils.copyProperties(orderCreateInfo, orderCreateInfoDTO);
        MQSender kafkaSender = mqSenderFactory.get(MQEnum.KAFKA);
        kafkaSender.send(MQConstants.SUBMIT_ORDER_QUEUE, JsonUtil.toJson(orderCreateInfoDTO));

        return order.getOrderSn();
    }

    /**
     * 创建订单和订单条目信息
     * @param memberDetailDTO 用户信息
     * @param orderSubmitVo 提交的订单信息
     * @return
     */
    private OrderCreateInfo createOrderInfo(MemberDetailDTO memberDetailDTO, OrderSubmitVO orderSubmitVo) {
        MemberDTO member = memberDetailDTO.getMember();
        //1. 生成订单号
        long orderId = idWorker.nextId();

        //2. 创建订单和订单条目的基本信息
        Order order = buildOrder(member, orderId, orderSubmitVo);
        List<OrderItem> orderItems = buildOrderItems(orderId);

        //3. 价格计算
        assert orderItems != null;
        computePrice(order, orderItems);

        OrderCreateInfo orderCreateInfo = new OrderCreateInfo();
        orderCreateInfo.setOrder(order);
        orderCreateInfo.setOrderItemList(orderItems);
        orderCreateInfo.setFare(BigDecimal.ZERO);
        orderCreateInfo.setPayPrice(order.getTotalAmount());

        return orderCreateInfo;
    }

    /**
     * 价格计算
     * @param order 订单信息
     * @param orderItems 订单条目信息
     */
    private void computePrice(Order order, List<OrderItem> orderItems) {
        //运费，促销，积分，优惠券 TODO
        order.setTotalAmount(new BigDecimal("0"));
        order.setFreightAmount(new BigDecimal("0"));
        order.setPromotionAmount(new BigDecimal("0"));
        order.setPointAmount(new BigDecimal("0"));
        order.setCouponAmount(new BigDecimal("0"));


        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            //TODO 暂用sku价格
            orderItem.setRealAmount(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getQuantity().toString())));

            total = total.add(orderItem.getRealAmount());
        }
        order.setTotalAmount(total);
    }

    /**
     * 创建订单详情条目列表
     * @param orderId
     * @return
     */
    private List<OrderItem> buildOrderItems(long orderId) {
        ResponseResult<List<CartDTO>> checkedCartItemResult = cartFeignClient.getCheckedCartItem();
        List<CartDTO> cartDTOList = checkedCartItemResult.getData();

        List<OrderItem> orderItemList = cartDTOList.stream().map(item -> {
            OrderItem orderItem = buildOrderItem(item);
            orderItem.setOrderId(orderId);
            orderItem.setOrderSn(orderId + "");
            return orderItem;
        }).collect(Collectors.toList());

        return orderItemList;
    }


    /**
     * 创建订单详情单个条目
     * @param cartDTO 购物车条目
     * @return 订单条目
     */
    private OrderItem buildOrderItem(CartDTO cartDTO) {
        OrderItem orderItem = new OrderItem();
        Long skuId = cartDTO.getSkuId();

        //配置sku信息
        orderItem.setSkuId(skuId);
        orderItem.setSkuName(cartDTO.getSkuName());
        orderItem.setSkuAttrs(cartDTO.getSaleAttr());
        orderItem.setSkuPic(cartDTO.getSkuPic());
        orderItem.setSkuPrice(cartDTO.getPrice());
        orderItem.setQuantity(cartDTO.getQuantity());

        //配置spu信息
        orderItem.setProductId(cartDTO.getProductId());
        orderItem.setCategoryId(0L); //TODO

        return orderItem;
    }

    /**
     * 构建订单
     * @param member
     * @param orderId
     * @return
     */
    private Order buildOrder(MemberDTO member, long orderId, OrderSubmitVO orderSubmitVo) {
        Order order = new Order();
        order.setId(orderId);
        order.setOrderSn(orderId + "");

        //1. 设置用户信息与订单状态
        order.setMemberId(member.getId());
        order.setUsername(member.getUsername());
        order.setOrderStatus(OrderStatusConstants.UNKNOWN);

        //2. 配置收货信息
        MemberAddressDTO memberAddress = memberFeignClient.getMemberAddress(orderSubmitVo.getMemberAddressId());

        order.setReceiverName(memberAddress.getReciverName());
        order.setReceiverPhone(memberAddress.getReciverPhone());
        order.setReceiverPostCode("todo");
        order.setReceiverProvince(memberAddress.getProvince());
        order.setReceiverCity(memberAddress.getCity());
        order.setReceiverRegion(memberAddress.getDistrict());
        order.setReceiverDetailAddress(memberAddress.getDetailAddress());
        order.setCreatedBy(member.getUsername());
        order.setUpdatedBy(member.getUsername());

        //3. 配置支付类型
        order.setPayType(orderSubmitVo.getPayType());

        order.setOrderComment(orderSubmitVo.getOrderComment());
        return order;
    }

    @Override
    public Boolean updateStatus(Long id, Integer orderStatus) {
        Order order = new Order();
        order.setId(id);
        order.setOrderStatus(orderStatus);
        return this.updateById(order);
    }


    /**
     * 汇率换算，TODO
     * @param amount 需要换算的金额
     * @return 换算后的金额
     */
    private BigDecimal rateCompute(BigDecimal amount) {
        return amount;
    }
}
