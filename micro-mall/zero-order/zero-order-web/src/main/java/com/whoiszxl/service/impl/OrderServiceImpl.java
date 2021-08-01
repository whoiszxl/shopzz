package com.whoiszxl.service.impl;
import java.math.BigDecimal;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoisxl.constants.OrderStatusConstants;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dto.*;
import com.whoiszxl.entity.Order;
import com.whoiszxl.entity.OrderItem;
import com.whoiszxl.entity.vo.OrderConfirmVO;
import com.whoiszxl.entity.vo.OrderCreateInfo;
import com.whoiszxl.entity.vo.OrderSubmitVO;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.feign.*;
import com.whoiszxl.mapper.OrderMapper;
import com.whoiszxl.service.OrderItemService;
import com.whoiszxl.service.OrderService;
import com.whoiszxl.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
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

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private OrderItemService orderItemService;

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
    public String submitOrder(OrderSubmitVO orderSubmitVo) {
        //0. 获取当前登录用户的信息
        Long memberId = StpUtil.getLoginIdAsLong();
        MemberDetailDTO memberDetailDTO = memberFeignClient.getMemberInfo();
        //1. 创建订单和订单详细条目
        OrderCreateInfo orderCreateInfo = createOrderInfo(memberDetailDTO, orderSubmitVo);

        //2. 验证提交的价格和计算的价格是否一致
        if(orderSubmitVo.getTotalAmount().compareTo(orderCreateInfo.getPayPrice()) != 0) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("价格计算异常"));
        }

        //3. 订单与订单详细条目入库
        this.save(orderCreateInfo.getOrder());
        orderItemService.saveBatch(orderCreateInfo.getOrderItemList());

        //4. 创建一个操作日志订单状态管理器，在订单状态流转到待付款状态时记录操作记录


        //5. 发送Kafka消息通知库存中心订单提交了，更新库存中心的SKU库存


        //6. 发送Kafka消息到调度中心，进行调度销售出库
        //6.1 通过商品的SKU ID查询到货位库存的明细条目，并进行遍历，一个SKU可能在多个货位上
        //6.2 创建出需要拣货的条目和发货的条目并进行批量入库
        //6.3 更新调度中心的库存
        //6.4 更新wms中心的库存



        return null;
    }

    /**
     * 创建订单和订单条目信息
     * @param memberDetailDTO 用户信息
     * @param orderSubmitVo 提交的订单信息
     * @return
     */
    private OrderCreateInfo createOrderInfo(MemberDetailDTO memberDetailDTO, OrderSubmitVO orderSubmitVo) {
        MemberDTO member = memberDetailDTO.getMemberDTO();
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
     * @param item 购物车条目
     * @return 订单条目
     */
    private OrderItem buildOrderItem(CartDTO item) {
        OrderItem orderItem = new OrderItem();
        Long skuId = item.getSkuId();

        //配置sku信息
        orderItem.setSkuId(skuId);
        orderItem.setSkuName(orderItem.getSkuName());
        orderItem.setSkuAttrs(orderItem.getSkuAttrs());
        orderItem.setSkuPic(item.getSkuPic());
        orderItem.setSkuPrice(item.getPrice());
        orderItem.setQuantity(item.getQuantity());

        //配置spu信息
        orderItem.setProductId(item.getProductId());
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
}
