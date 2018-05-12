package com.whoiszxl.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.dao.CartMapper;
import com.whoiszxl.dao.OrderItemMapper;
import com.whoiszxl.dao.OrderMapper;
import com.whoiszxl.dao.PayInfoMapper;
import com.whoiszxl.dao.ProductMapper;
import com.whoiszxl.dao.ShippingMapper;
import com.whoiszxl.entity.Cart;
import com.whoiszxl.entity.Order;
import com.whoiszxl.entity.OrderItem;
import com.whoiszxl.entity.PayInfo;
import com.whoiszxl.entity.Product;
import com.whoiszxl.entity.Shipping;
import com.whoiszxl.service.OrderService;
import com.whoiszxl.utils.BigDecimalUtil;
import com.whoiszxl.utils.DateTimeUtil;
import com.whoiszxl.utils.FTPUtil;
import com.whoiszxl.utils.PropertiesUtil;
import com.whoiszxl.vo.OrderItemVo;
import com.whoiszxl.vo.OrderProductVo;
import com.whoiszxl.vo.OrderVo;
import com.whoiszxl.vo.ShippingVo;

/**
 * 
 * @author whoiszxl
 *
 */
@Service
public class OrderServiceImpl implements OrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	private static  AlipayTradeService tradeService;
    static {

        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	@Autowired
	private PayInfoMapper payInfoMapper;
	
	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
    private ShippingMapper shippingMapper;
	
	
	public ServerResponse createOrder(Integer userId,Integer shippingId) {
		//从cart获取数据
		List<Cart> cartList = cartMapper.selectCheckedCartByUserId(userId);
		
		//计算总价
		ServerResponse response = this.getCartOrderItem(userId, cartList);
		if(!response.isSuccess()) {
			return response;
		}
		
		List<OrderItem> orderItemList = (List<OrderItem>) response.getData();
		BigDecimal payment = this.getOrderTotalPrice(orderItemList);
		
		//生成订单
		Order order = this.assembleOrder(userId, shippingId, payment);
		if(order == null) {
			return ServerResponse.createByErrorMessage("生成订单错误");
		}
		if(CollectionUtils.isEmpty(orderItemList)) {
			return ServerResponse.createByErrorMessage("购物车为空");
		}
		for (OrderItem orderItem : orderItemList) {
			orderItem.setOrderNo(order.getOrderNo());
		}
		
		//mybatis批量插入
		orderItemMapper.batchInsert(orderItemList);
		
		//删减库存
		this.reduceProductStock(orderItemList);
		
		//清空购物车
		this.cleanCart(cartList);
		
		//返回给前端数据
        OrderVo orderVo = assembleOrderVo(order,orderItemList);
        return ServerResponse.createBySuccess(orderVo);
		
	}
	
	private OrderVo assembleOrderVo(Order order,List<OrderItem> orderItemList){
		//创建一个订单前台对象
        OrderVo orderVo = new OrderVo();
        //设置订单编号，支付金额，支付类型，支付的详情
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getValue());

        //设置邮费订单状态状态描述
        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());

        orderVo.setShippingId(order.getShippingId());
        //获取到地址
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if(shipping != null){
        	//设置收件人姓名
            orderVo.setReceiverName(shipping.getReceiverName());
            //设置收件详情vo对象
            orderVo.setShippingVo(assembleShippingVo(shipping));
        }
        
        //设置支付时间，发件时间，结束时间，创建时间，关闭时间
        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));

        //设置图片host
        orderVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        List<OrderItemVo> orderItemVoList = Lists.newArrayList();

        //遍历传入的订单item信息
        for(OrderItem orderItem : orderItemList){
        	//将orderItem转换成vo对象
            OrderItemVo orderItemVo = assembleOrderItemVo(orderItem);
            //添加到list中
            orderItemVoList.add(orderItemVo);
        }
        //添加到ordetVo中
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }
	
	/**
	 * 将orderItem转换成前台使用的orderItemVo类
	 * @param orderItem orderItem对象
	 * @return OrderItemVo
	 */
	private OrderItemVo assembleOrderItemVo(OrderItem orderItem){
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());

        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        return orderItemVo;
    }



    private ShippingVo assembleShippingVo(Shipping shipping){
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        shippingVo.setReceiverPhone(shippingVo.getReceiverPhone());
        return shippingVo;
    }
	
    /**
     * 清空购物车
     * @param cartList
     */
	private void cleanCart(List<Cart> cartList){
        for(Cart cart : cartList){
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }
	
	/**
	 * 删减库存
	 * @param orderItemList
	 */
	private void reduceProductStock(List<OrderItem> orderItemList) {
		for(OrderItem orderItem : orderItemList) {
			Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
			product.setStock(product.getStock() - orderItem.getQuantity());
			productMapper.updateByPrimaryKeySelective(product);
		}
	}
	
	/**
	 * 生成一个订单
	 * @param userId 用户id
	 * @param shippingId 收货地址id
	 * @param payment 支付金额
	 * @return
	 */
	private Order assembleOrder(Integer userId,Integer shippingId,BigDecimal payment) {
		long orderNo = this.generateOrderNo();
		Order order = new Order();
		order.setOrderNo(orderNo);
		order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
		order.setPostage(0);
		order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
		order.setPayment(payment);
		
		order.setUserId(userId);
		order.setShippingId(shippingId);
		int rowCount = orderMapper.insert(order);
		if(rowCount > 0) {
			return order;
		}
		return null;
	}
	
	/**
	 * 生成一个随机订单号
	 * @return 随机订单号
	 */
	private long generateOrderNo() {
		long currentTime = System.currentTimeMillis();
		return currentTime + new Random().nextInt(100);
	}
	
	/**
	 * 获取订单中item的总价
	 * @param orderItemList
	 * @return
	 */
	private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList) {
		BigDecimal payment = new BigDecimal("0");
		for (OrderItem orderItem : orderItemList) {
			payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
		}
		return payment;
	}
	
	/**
	 * 获取购物车列表中所有的订单子对象
	 * @param userId
	 * @param cartList
	 * @return
	 */
	private ServerResponse getCartOrderItem(Integer userId,List<Cart> cartList){
		List<OrderItem> orderItemList = Lists.newArrayList();
		if(CollectionUtils.isEmpty(cartList)) {
			return ServerResponse.createByErrorMessage("购物车为空");
		}
		
		//校验购物车商品状态和数量
		for (Cart cart : cartList) {
			OrderItem orderItem = new OrderItem();
			Product product = productMapper.selectByPrimaryKey(cart.getProductId());
			if(Const.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()) {
				return ServerResponse.createByErrorMessage("产品"+product.getName()+"不在售卖");
			}
			if(cart.getQuantity() > product.getStock()) {
				return ServerResponse.createByErrorMessage("产品"+product.getName()+"库存不足");
			}
			orderItem.setUserId(userId);
			orderItem.setProductId(product.getId());
			orderItem.setProductName(product.getName());
			orderItem.setProductImage(product.getMainImage());
			orderItem.setCurrentUnitPrice(product.getPrice());
			orderItem.setQuantity(cart.getQuantity());
			orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cart.getQuantity()));
			orderItemList.add(orderItem);
		}
		return ServerResponse.createBySuccess(orderItemList);
	}
	
	
	
	
	//backend

	/**
	 * 查询所有订单并分页
	 */
    public ServerResponse<PageInfo> manageList(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.selectAllOrder();
        List<OrderVo> orderVoList = this.assembleOrderVoList(orderList,null);
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    public ServerResponse<OrderVo> manageDetail(Long orderNo){
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order != null){
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(orderNo);
            OrderVo orderVo = assembleOrderVo(order,orderItemList);
            return ServerResponse.createBySuccess(orderVo);
        }
        return ServerResponse.createByErrorMessage("订单不存在");
    }



    public ServerResponse<PageInfo> manageSearch(Long orderNo,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order != null){
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(orderNo);
            OrderVo orderVo = assembleOrderVo(order,orderItemList);

            PageInfo pageResult = new PageInfo(Lists.newArrayList(order));
            pageResult.setList(Lists.newArrayList(orderVo));
            return ServerResponse.createBySuccess(pageResult);
        }
        return ServerResponse.createByErrorMessage("订单不存在");
    }


    public ServerResponse<String> manageSendGoods(Long orderNo){
        Order order= orderMapper.selectByOrderNo(orderNo);
        if(order != null){
            if(order.getStatus() == Const.OrderStatusEnum.PAID.getCode()){
                order.setStatus(Const.OrderStatusEnum.SHIPPED.getCode());
                order.setSendTime(new Date());
                orderMapper.updateByPrimaryKeySelective(order);
                return ServerResponse.createBySuccess("发货成功");
            }
        }
        return ServerResponse.createByErrorMessage("订单不存在");
    }
	
	
	
	
	
	
	
	
	
	public ServerResponse pay(Long orderNo,Integer userId,String path) {
		logger.info("OrderServiceImpl.pay()开始调用了,orderNo={},userId={},path={}",orderNo,userId,path);
		Map<String ,String> resultMap = Maps.newHashMap();
		//通过订单编号和用户id查询到这个订单
        Order order = orderMapper.selectByUserIdAndOrderNo(userId,orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }
        logger.info("当前用户的订单是:"+order.getOrderNo());
        //将订单编号存到返回参数中
        resultMap.put("orderNo",String.valueOf(order.getOrderNo()));



        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo().toString();

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuilder().append("whoiszxl扫码支付,订单号:").append(outTradeNo).toString();
        
        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();


        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("订单").append(outTradeNo).append("购买商品共").append(totalAmount).append("元").toString();


        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");




        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();

        List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(orderNo,userId);
        for(OrderItem orderItem : orderItemList){
            GoodsDetail goods = GoodsDetail.newInstance(
            		orderItem.getProductId().toString(), //商品id
            		orderItem.getProductName(), //商品名称
                    BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(),new Double(100).doubleValue()).longValue(),//商品价格，单位为分
                    orderItem.getQuantity()); //商品数量
            goodsDetailList.add(goods);
        }

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl(PropertiesUtil.getProperty("alipay.callback.url"))//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);


        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                logger.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                File folder = new File(path);
                if(!folder.exists()){
                    folder.setWritable(true);
                    folder.mkdirs();
                }

                // 需要修改为运行机器上的路径
                String qrPath = String.format(path+"/qr-%s.png",response.getOutTradeNo());
                String qrFileName = String.format("qr-%s.png",response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);

                File targetFile = new File(path,qrFileName);
                try {
                    FTPUtil.uploadFile(Lists.newArrayList(targetFile));
                } catch (IOException e) {
                    logger.error("上传二维码异常",e);
                }
                logger.info("qrPath:" + qrPath);
                String qrUrl = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName();
                resultMap.put("qrUrl",qrUrl);
                return ServerResponse.createBySuccess(resultMap);
            case FAILED:
                logger.error("支付宝预下单失败!!!");
                return ServerResponse.createByErrorMessage("支付宝预下单失败!!!");

            case UNKNOWN:
                logger.error("系统异常，预下单状态未知!!!");
                return ServerResponse.createByErrorMessage("系统异常，预下单状态未知!!!");

            default:
                logger.error("不支持的交易状态，交易返回异常!!!");
                return ServerResponse.createByErrorMessage("不支持的交易状态，交易返回异常!!!");
        }
	}
	
	// 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
        	logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
            	logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                    response.getSubMsg()));
            }
            logger.info("body:" + response.getBody());
        }
    }
    
    public ServerResponse aliCallback(Map<String, String> params) {
    	//获取到订单编号，交易编号，交易状态
    	Long orderNo = Long.parseLong(params.get("out_trade_no"));
    	String tradeNo = params.get("trade_no");
    	String tradeStatus = params.get("trade_status");
    	//通过订单编号查询到订单
    	Order order = orderMapper.selectByOrderNo(orderNo);
    	
    	//如果订单不存在和订单的状态已经在付款之后了
    	if(order == null){
            return ServerResponse.createByErrorMessage("非whoiszxl商城的订单,回调忽略");
        }
        if(order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()){
            return ServerResponse.createBySuccess("支付宝重复调用");
        }
        
        //如果状态为success
        if(Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)){
        	//设置订单的支付时间，订单状态为已付款
            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            order.setStatus(Const.OrderStatusEnum.PAID.getCode());
            orderMapper.updateByPrimaryKeySelective(order);
        }

        //创建一个支付信息类并设置内容
        PayInfo payInfo = new PayInfo();
        payInfo.setUserId(order.getUserId());
        payInfo.setOrderNo(order.getOrderNo());
        payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
        payInfo.setPlatformNumber(tradeNo);
        payInfo.setPlatformStatus(tradeStatus);

        payInfoMapper.insert(payInfo);

        return ServerResponse.createBySuccess();
    }
    
    
    public ServerResponse queryOrderPayStatus(Integer userId,Long orderNo){
    	//通过id和用户订单号查询订单
        Order order = orderMapper.selectByUserIdAndOrderNo(userId,orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }
        //获取订单的状态,大于等于已付款就返回成功
        if(order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

	@Override
	public ServerResponse<String> cancel(Integer userId, Long orderNo) {
		Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
		if(order == null){
            return ServerResponse.createByErrorMessage("该用户此订单不存在");
        }
        if(order.getStatus() != Const.OrderStatusEnum.NO_PAY.getCode()){
            return ServerResponse.createByErrorMessage("已付款,无法取消订单");
        }
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setStatus(Const.OrderStatusEnum.CANCELED.getCode());
        
        int rowCount = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if(rowCount > 0) {
        	return ServerResponse.createBySuccess();
        }
		return ServerResponse.createByError();
	}

	/**
	 * 获取到订单的vo对象 (订单的商品集合,缩略图和总价)
	 */
	@Override
	public ServerResponse getOrderCartProduct(Integer userId) {
		OrderProductVo orderProductVo = new OrderProductVo();
		
		//从购物车中获取数据
		List<Cart> cartList = cartMapper.selectCheckedCartByUserId(userId);
		//获取到购物车中的商品并且转换成order的Item对象
		ServerResponse serverResponse = this.getCartOrderItem(userId, cartList);
		if(!serverResponse.isSuccess()) {
			return serverResponse;
		}
		//将数据强转为orderItemList对象
		List<OrderItem> orderItemList =( List<OrderItem> ) serverResponse.getData();

		//准备将item对象转换成vo对象
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        
        BigDecimal payment = new BigDecimal("0");
        //for循环遍历itemlist,计算总价,并且将orderItem转换成orderItemVo并且添加到list中
        for(OrderItem orderItem : orderItemList){
            payment = BigDecimalUtil.add(payment.doubleValue(),orderItem.getTotalPrice().doubleValue());
            orderItemVoList.add(assembleOrderItemVo(orderItem));
        }
        //设置vo对象的总价
        orderProductVo.setProductTotalPrice(payment);
        //设置商品的volist
        orderProductVo.setOrderItemVoList(orderItemVoList);
        //设置图片的前缀
        orderProductVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return ServerResponse.createBySuccess(orderProductVo);
	}

	@Override
	public ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo) {
		//通过userid和orderNo查询到当前订单
		Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
		if(order != null){
			//通过userid和orderNo查询到订单中的商品
			List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(orderNo, userId);
			//转换成vo对象
			OrderVo orderVo = assembleOrderVo(order,orderItemList);
			//返回订单的vo对象
            return ServerResponse.createBySuccess(orderVo);
        }
        return  ServerResponse.createByErrorMessage("没有找到该订单");
	}

	@Override
	public ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize) {
		//开启分页
		PageHelper.startPage(pageNum,pageSize);
		//通过用户id查询到所有的订单
        List<Order> orderList = orderMapper.selectByUserId(userId);
        //将其转换成订单vo对象
        List<OrderVo> orderVoList = assembleOrderVoList(orderList,userId);
        //通过分页的模式返回
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderVoList);
        return ServerResponse.createBySuccess(pageResult);
	}
	
	private List<OrderVo> assembleOrderVoList(List<Order> orderList,Integer userId){
        List<OrderVo> orderVoList = Lists.newArrayList();
        //遍历订单list
        for(Order order : orderList){
            List<OrderItem>  orderItemList = Lists.newArrayList();
            if(userId == null){
                //管理员查询的时候 不需要传userId
                orderItemList = orderItemMapper.getByOrderNo(order.getOrderNo());
            }else{
            	//用户查询时候只能查自己的订单
                orderItemList = orderItemMapper.getByOrderNoUserId(order.getOrderNo(),userId);
            }
            //查询到数据之后转换成vo对象并添加到list中 
            OrderVo orderVo = assembleOrderVo(order,orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }
}

