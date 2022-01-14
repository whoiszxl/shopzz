package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.OrderPayTypeConstants;
import com.whoiszxl.constants.OrderStatusConstants;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.dto.*;
import com.whoiszxl.entity.Order;
import com.whoiszxl.entity.OrderItem;
import com.whoiszxl.entity.PayInfoDc;
import com.whoiszxl.entity.PayInfoDcBuilder;
import com.whoiszxl.entity.query.OrderSubmitRequest;
import com.whoiszxl.entity.vo.CartDetailVO;
import com.whoiszxl.entity.vo.CartItemVO;
import com.whoiszxl.entity.vo.OrderCreateInfo;
import com.whoiszxl.entity.vo.OrderPayVO;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.factory.CreateDcAddressFactory;
import com.whoiszxl.feign.MemberFeignClient;
import com.whoiszxl.feign.ProductFeignClient;
import com.whoiszxl.feign.PromotionFeignClient;
import com.whoiszxl.mapper.OrderMapper;
import com.whoiszxl.mq.MQConstants;
import com.whoiszxl.mq.MQEnum;
import com.whoiszxl.mq.MQSender;
import com.whoiszxl.mq.MQSenderFactory;
import com.whoiszxl.service.*;
import com.whoiszxl.state.LoggerOrderStateManager;
import com.whoiszxl.utils.BeanCopierUtils;
import com.whoiszxl.utils.IdWorker;
import com.whoiszxl.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-01-09
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderOperateHistoryService orderOperateHistoryService;

    @Autowired
    private MemberFeignClient memberFeignClient;

    @Autowired
    private PromotionFeignClient promotionFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private CartService cartService;

    @Autowired
    private LoggerOrderStateManager orderStateManager;

    @Autowired
    private MQSenderFactory mqSenderFactory;

    @Autowired
    private PayInfoDcService payInfoDcService;

    @Autowired
    private CreateDcAddressFactory createDcAddressFactory;

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    @Transactional
    public String orderSubmit(OrderSubmitRequest orderSubmitRequest) {
        //TODO 防重校验

        //0. 获取当前登录用户的信息
        ResponseResult<MemberDetailDTO> memberInfoResult = memberFeignClient.getMemberInfo();
        if(!memberInfoResult.isOk()) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("用户异常"));
        }

        MemberDetailDTO memberDetailDTO = memberInfoResult.getData();
        //1. 创建订单和订单详细条目
        OrderCreateInfo orderCreateInfo = createOrderInfo(memberDetailDTO, orderSubmitRequest);

        //2. 订单与订单详细条目入库
        Order order = orderCreateInfo.getOrder();
        this.save(order);
        orderItemService.saveBatch(orderCreateInfo.getOrderItemList());

        //4. 创建一个操作日志订单状态管理器，在订单状态流转到待付款状态时记录操作记录
        orderStateManager.create(order);

        //5. TODO 校验库存，锁定库存
        List<Long> skuIds = orderCreateInfo.getOrderItemList().stream().map(OrderItem::getSkuId).collect(Collectors.toList());
        ResponseResult<Boolean> responseResult = productFeignClient.checkStock(skuIds);
        if(!responseResult.getData()) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("库存不足"));
        }

        //6. 使用优惠券
        if(StringUtils.isNotBlank(orderSubmitRequest.getCouponId())) {
            ResponseResult<Boolean> useCouponResult = promotionFeignClient.useCoupon(orderSubmitRequest.getCouponId());
            if(!useCouponResult.isOk()) {
                String message = useCouponResult.getMessage();
                log.error("优惠券使用失败：{}", message);
                ExceptionCatcher.catchValidateEx(ResponseResult.buildError("优惠券使用失败"));
            }
        }

        //7. 清空购物车中选中的item
        cartService.clearCheckedCart();

        //7. 发送Kafka消息到WMS中心进行处理
        //7.1 通过商品的SKU ID查询到货位库存的明细条目，并进行遍历，一个SKU可能在多个货位上
        //7.2 创建出需要拣货的条目和发货的条目并进行批量入库
        //7.4 更新wms中心的库存
        OrderCreateInfoDTO orderCreateInfoDTO = dozerUtils.map(orderCreateInfo, OrderCreateInfoDTO.class);
        MQSender kafkaSender = mqSenderFactory.get(MQEnum.ROCKETMQ);
        kafkaSender.send(MQConstants.SUBMIT_ORDER_QUEUE, JsonUtil.toJson(orderCreateInfoDTO));
        return order.getOrderSn();
    }

    /**
     * 创建订单和订单条目信息
     * @param memberDetailDTO 用户信息
     * @param orderSubmitVo 提交的订单信息
     * @return
     */
    private OrderCreateInfo createOrderInfo(MemberDetailDTO memberDetailDTO, OrderSubmitRequest orderSubmitVo) {
        MemberDTO member = memberDetailDTO.getMember();
        //1. 生成订单号
        long orderId = idWorker.nextId();

        //2. 创建订单和订单条目的基本信息
        Order order = buildOrder(member, orderId, orderSubmitVo);
        List<OrderItem> orderItems = buildOrderItems(orderId);

        //3. 价格计算，计算订单总额
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
        //获取当前选中的购物车item列表
        CartDetailVO cartDetail = cartService.getCartDetail();
        List<CartItemVO> cartItemVOList = cartDetail.getCartItemVOList();

        if(ObjectUtils.isEmpty(cartItemVOList)) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("购物车不存在选中商品"));
        }

        List<OrderItem> orderItemList = cartItemVOList.stream().map(item -> {
            if(item.getChecked() == 1) {
                OrderItem orderItem = buildOrderItem(item);
                orderItem.setOrderId(orderId);
                orderItem.setOrderSn(orderId + "");
                return orderItem;
            }
            return null;
        }).collect(Collectors.toList());

        return orderItemList;
    }


    /**
     * 创建订单详情单个条目
     * @param cartDTO 购物车条目
     * @return 订单条目
     */
    private OrderItem buildOrderItem(CartItemVO cartDTO) {
        OrderItem orderItem = new OrderItem();
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

    /**
     * 构建订单
     * @param member
     * @param orderId
     * @return
     */
    private Order buildOrder(MemberDTO member, long orderId, OrderSubmitRequest orderSubmitRequest) {
        Order order = new Order();
        order.setId(orderId);
        order.setOrderSn(orderId + "");

        //1. 设置用户信息与订单状态
        order.setMemberId(member.getId());
        order.setUsername(member.getUsername());
        order.setOrderStatus(OrderStatusConstants.UNKNOWN);

        //2. 配置收货信息
        MemberAddressDTO memberAddress = memberFeignClient.getMemberAddress(orderSubmitRequest.getAddressId().toString());

        order.setReceiverName(memberAddress.getReciverName());
        order.setReceiverPhone(memberAddress.getReciverPhone());
        order.setReceiverPostCode("todo");
        order.setPayType(0);
        order.setReceiverProvince(memberAddress.getProvince());
        order.setReceiverCity(memberAddress.getCity());
        order.setReceiverRegion(memberAddress.getDistrict());
        order.setReceiverDetailAddress(memberAddress.getDetailAddress());
        order.setCreatedBy(member.getUsername());
        order.setUpdatedBy(member.getUsername());
        order.setOrderComment(orderSubmitRequest.getOrderComment());
        return order;
    }


    @Override
    public Boolean updateStatus(Long id, Integer orderStatus) {
        Order order = new Order();
        order.setId(id);
        order.setOrderStatus(orderStatus);
        return this.updateById(order);
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
            PayInfoDc payInfoDc = payInfoDcService.getByOrderIdAndMemberId(order.getId(), order.getMemberId());
            if(payInfoDc != null) {
                return ResponseResult.buildSuccess(payInfoDc);
            }

            payInfoDc = PayInfoDcBuilder
                    .get()
                    .init(createDcAddressFactory, orderPayVO.getDcName())
                    .buildBaseData(order)
                    .buildAddress(order)
                    .initStatus()
                    .create();

            //汇率换算
            BigDecimal rateAmount = rateCompute(order.getTotalAmount());
            payInfoDc.setTotalAmount(rateAmount);

            boolean saveFlag = payInfoDcService.save(payInfoDc);
            if(saveFlag) {
                return ResponseResult.buildSuccess(payInfoDc);
            }
            return ResponseResult.buildError("数字货币支付失败");
        }

        //3. TODO 如果是普通支付

        return ResponseResult.buildError("TODO");
    }


    /**
     * 汇率换算，TODO
     * @param amount 需要换算的金额
     * @return 换算后的金额
     */
    private BigDecimal rateCompute(BigDecimal amount) {
        return amount;
    }


    @Override
    public OrderInfoDTO getOrderInfo(Long orderId) {
        //获取订单信息
        Order order = this.getById(orderId);

        //获取订单条目信息
        List<OrderItem> orderItemList = orderItemService.list(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getOrderId, orderId));
        List<OrderItemDTO> orderItemDTOList = dozerUtils.mapList(orderItemList, OrderItemDTO.class);

        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        orderInfoDTO.setOrderId(order.getId());
        orderInfoDTO.setMemberId(order.getMemberId());
        orderInfoDTO.setTotalAmount(order.getTotalAmount());
        orderInfoDTO.setOrderItemDTOList(orderItemDTOList);
        return orderInfoDTO;
    }
}
