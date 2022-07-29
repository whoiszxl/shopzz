package com.whoiszxl.service;
import com.whoiszxl.constants.KafkaTopicConstants;
import com.whoiszxl.enums.ActionIdEnum;
import com.whoiszxl.entity.*;
import com.whoiszxl.enums.PageIdEnum;
import com.whoiszxl.enums.ItemTypeEnum;
import com.whoiszxl.enums.DisplayTypeEnum;
import com.google.common.collect.Lists;
import com.whoiszxl.common.AppCommonConstants;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.javafaker.Faker;
import com.whoiszxl.cqrs.command.DataGenerateExecuteCommand;
import com.whoiszxl.entity.member.MemberInfoPO;
import com.whoiszxl.entity.member.MemberPO;
import com.whoiszxl.entity.order.Order;
import com.whoiszxl.entity.order.OrderItem;
import com.whoiszxl.entity.product.Sku;
import com.whoiszxl.entity.product.Spu;
import com.whoiszxl.mapper.*;
import com.whoiszxl.utils.IdWorker;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.OrderUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 数据生成服务
 */
@Service
public class DataGeneratorService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    Faker enFaker = new Faker();
    Faker cnFaker = new Faker(new Locale("zh-CN"));



    /**
     * 执行数据生成
     * @param executeCommand 执行命令
     * @return
     */
    public boolean dbExecute(DataGenerateExecuteCommand executeCommand) {
        String moduleName = executeCommand.getModuleName();
        switch (moduleName) {
            case "member":
                memberExecute(executeCommand.getInterval(), executeCommand.getQuantity(), executeCommand.getTimes());
                break;

            case "order":
                orderExecute(executeCommand.getInterval(), executeCommand.getQuantity(), executeCommand.getTimes());
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * 订单维度数据生成
     * @param interval 间隔毫秒数
     * @param quantity 批次数量
     * @param times 生成多少个批次
     */
    private void orderExecute(Integer interval, Integer quantity, Integer times) {
        List<MemberPO> memberPOList = memberMapper.selectList(null);
        List<Spu> spuList = spuMapper.selectList(null);


        List<Order> orderList = new ArrayList<>();
        List<OrderItem> orderItemList = new ArrayList<>();
        Order order;
        OrderItem orderItem;

        for (int i = 0; i < times; i++) {

            for (int j = 0; j < quantity; j++) {
                long id = idWorker.nextId();
                MemberPO memberPO = memberPOList.get(enFaker.number().numberBetween(0, memberPOList.size()));
                Spu spu = spuList.get(enFaker.number().numberBetween(0, spuList.size()));

                String orderNo = OrderUtils.makeOrderNo();
                order = generateOrder(id, orderNo, memberPO);
                orderList.add(order);

                orderItem = generateOrderItem(order, spu);
                orderItemList.add(orderItem);
            }

            orderMapper.insertBatchSomeColumn(orderList);
            orderItemMapper.insertBatchSomeColumn(orderItemList);

            orderList.clear();
            orderItemList.clear();

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private OrderItem generateOrderItem(Order order, Spu spu) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(idWorker.nextId());
        orderItem.setOrderId(order.getId());
        orderItem.setOrderNo(order.getOrderNo());
        orderItem.setProductId(spu.getId());
        orderItem.setCategoryId(spu.getCategoryId());

        List<Sku> skuList = skuMapper.selectList(Wrappers.<Sku>lambdaQuery().eq(Sku::getSpuId, spu.getId()));
        Sku sku = skuList.get(0);
        orderItem.setSkuId(sku.getId());
        orderItem.setSkuName(sku.getSkuName());
        orderItem.setSkuPic("todo");
        orderItem.setSkuPrice(sku.getSalePrice());
        orderItem.setQuantity(enFaker.number().numberBetween(1, 50));
        orderItem.setSkuAttrs("todo");
        orderItem.setPromotionActivityId(0L);
        orderItem.setPromotionAmount(new BigDecimal("0"));
        orderItem.setCouponAmount(new BigDecimal("0"));
        orderItem.setPointAmount(new BigDecimal("0"));
        orderItem.setRealAmount(orderItem.getSkuPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        orderItem.setVersion(0L);
        orderItem.setIsDeleted(0);
        orderItem.setCreatedBy("zxl");
        orderItem.setUpdatedBy("zxl");
        orderItem.setCreatedAt(LocalDateTime.now());
        orderItem.setUpdatedAt(LocalDateTime.now());

        return orderItem;
    }

    private Order generateOrder(long orderId, String orderNo, MemberPO memberPO) {
        Order order = new Order();
        order.setId(orderId);
        order.setOrderNo(orderNo);
        order.setMemberId(memberPO.getId());
        order.setUsername(memberPO.getUsername());
        order.setOrderStatus(enFaker.number().numberBetween(1, 12));
        order.setSnapshotAddress("{\"id\": 1, \"city\": \"" + cnFaker.address().cityName() + "\", \"district\": \"" + cnFaker.address().streetName() + "\", \"memberId\": " + memberPO.getId() + ", \"province\": \""+ cnFaker.address().state() +"\", \"isDefault\": 1, \"receiverName\": \"" + cnFaker.superhero().name() + "\", \"detailAddress\": \"" + cnFaker.address().secondaryAddress() + "\", \"receiverPhone\": \"" + memberPO.getPhone() + "\"}");
        order.setPayType(enFaker.number().numberBetween(1, 3));
        order.setTotalAmount(new BigDecimal(enFaker.number().numberBetween(1, 10000)));
        order.setFreightAmount(new BigDecimal(enFaker.number().numberBetween(0, 10)));
        order.setPromotionAmount(new BigDecimal(enFaker.number().numberBetween(0, 10)));
        order.setPointAmount(new BigDecimal(enFaker.number().numberBetween(0, 10)));
        order.setCouponAmount(new BigDecimal(enFaker.number().numberBetween(0, 10)));
        order.setAdminDiscountAmount(new BigDecimal("0"));
        order.setFinalPayAmount(order.getTotalAmount()
                .subtract(order.getFreightAmount())
                .subtract(order.getPromotionAmount())
                .subtract(order.getPointAmount())
                .subtract(order.getCouponAmount()));
        order.setOrderComment(cnFaker.funnyName().name());
        order.setPaymentTime(LocalDateTime.now());
        order.setDeliveryTime(LocalDateTime.now());
        order.setReceiveTime(LocalDateTime.now());
        order.setCommentTime(LocalDateTime.now());
        order.setVersion(0L);
        order.setIsDeleted(0);
        order.setCreatedBy("zzz");
        order.setUpdatedBy("zzz");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        return order;
    }


    /**
     * 会员维度数据生成
     * @param interval 间隔毫秒数
     * @param quantity 批次数量
     * @param times 生成多少个批次
     */
    private void memberExecute(Integer interval, Integer quantity, Integer times) {
        List<MemberPO> memberList = new ArrayList<>();
        List<MemberInfoPO> memberInfoPOList = new ArrayList<>();
        MemberPO memberPO;
        MemberInfoPO memberInfoPO;


        for (int i = 0; i < times; i++) {

            for (int j = 0; j < quantity; j++) {
                long id = idWorker.nextId();
                memberPO = generateMemberPO(id);
                memberList.add(memberPO);

                memberInfoPO = generateMemberInfoPO(id);
                memberInfoPOList.add(memberInfoPO);
            }

            memberMapper.insertBatchSomeColumn(memberList);
            memberInfoMapper.insertBatchSomeColumn(memberInfoPOList);

            memberList.clear();
            memberInfoPOList.clear();

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    private MemberInfoPO generateMemberInfoPO(long memberId) {
        MemberInfoPO memberInfoPO = new MemberInfoPO();
        memberInfoPO.setId(idWorker.nextId());
        memberInfoPO.setMemberId(memberId);
        memberInfoPO.setGender(cnFaker.number().numberBetween(0, 2));
        memberInfoPO.setBirthday(LocalDateTime.now());
        memberInfoPO.setCountryCode(cnFaker.country().countryCode2());
        memberInfoPO.setCountry(cnFaker.country().name());
        memberInfoPO.setProvince(cnFaker.address().state());
        memberInfoPO.setCity(cnFaker.address().city());
        memberInfoPO.setDistrict(cnFaker.address().streetName());
        memberInfoPO.setGradeLevel(String.valueOf(cnFaker.number().numberBetween(1,5)));
        memberInfoPO.setLoginCount(Long.valueOf(cnFaker.number().numberBetween(1,1000)));
        memberInfoPO.setLoginErrorCount(0L);
        memberInfoPO.setLastLogin(LocalDateTime.now());
        memberInfoPO.setVersion(0L);
        memberInfoPO.setIsDeleted(0);
        memberInfoPO.setCreatedBy("zxl");
        memberInfoPO.setUpdatedBy("zxl");
        memberInfoPO.setCreatedAt(LocalDateTime.now());
        memberInfoPO.setUpdatedAt(LocalDateTime.now());
        return memberInfoPO;
    }

    private MemberPO generateMemberPO(long memberId) {
        MemberPO memberPO = new MemberPO();
        memberPO.setId(memberId);
        memberPO.setUsername(enFaker.superhero().prefix() + enFaker.number().numberBetween(10000, 99999));
        memberPO.setPassword(passwordEncoder.encode("123456"));
        memberPO.setPayPassword(passwordEncoder.encode("123456"));
        memberPO.setGoogleKey("");
        memberPO.setGoogleStatus(0);
        memberPO.setRealName(cnFaker.name().fullName());
        memberPO.setNickName(cnFaker.dog().name());
        memberPO.setAvatar("https://tvax1.sinaimg.cn/crop.0.0.180.180.180/844527a1ly8gdia5nfhtoj2050050mx1.jpg");
        memberPO.setEmail(memberPO.getUsername().toLowerCase() + "@gmail.com");
        memberPO.setPhone(cnFaker.phoneNumber().phoneNumber());
        memberPO.setStatus(1);
        memberPO.setVersion(0L);
        memberPO.setIsDeleted(0);
        memberPO.setCreatedBy("zzz");
        memberPO.setUpdatedBy("zzz");
        memberPO.setCreatedAt(LocalDateTime.now());
        memberPO.setUpdatedAt(LocalDateTime.now());
        return memberPO;
    }

    public boolean logExecute(DataGenerateExecuteCommand executeCommand) {
        List<MemberPO> memberPOList = memberMapper.selectList(null);
        List<Spu> spuList = spuMapper.selectList(null);

        Integer times = executeCommand.getTimes();
        Integer quantity = executeCommand.getQuantity();
        Integer interval = executeCommand.getInterval();

        for (int i = 0; i < times; i++) {

            for (int j = 0; j < quantity; j++) {
                AppMain appMain = new AppMain();
                appMain.setTs(System.currentTimeMillis());

                AppCommon appCommon = buildAppCommon(memberPOList);
                appMain.setAppCommon(appCommon);

                AppPage appPage = buildAppPage(spuList);
                appMain.setAppPage(appPage);

                AppStart appStart = buildAppStart();
                appMain.setAppStart(appStart);

                List<AppDisplay> appDisplayList = buildAppDisplayList();
                appMain.setDisplayList(appDisplayList);

                List<AppAction> appActionList = buildAppActionList();
                appMain.setActionList(appActionList);

                kafkaTemplate.send(KafkaTopicConstants.ODS_LOG_LOG, JsonUtil.toJson(appMain));
            }

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private List<AppAction> buildAppActionList() {
        int random = enFaker.number().numberBetween(1, 5);
        List<AppAction> appActionList = new ArrayList<>();
        for (int i = 0; i < random; i++) {
            AppAction appAction = new AppAction();
            appAction.setActionId(ActionIdEnum.values()[enFaker.number().numberBetween(0, ActionIdEnum.values().length)]);
            appAction.setItemType(ItemTypeEnum.values()[enFaker.number().numberBetween(0, ItemTypeEnum.values().length)]);
            appAction.setItem(enFaker.number().numberBetween(100000, 999999) + "");
            appAction.setTs(System.currentTimeMillis());
            appActionList.add(appAction);
        }

        return appActionList;
    }

    private List<AppDisplay> buildAppDisplayList() {

        int random = enFaker.number().numberBetween(1, 5);
        List<AppDisplay> appDisplayList = new ArrayList<>();
        for (int i = 0; i < random; i++) {
            AppDisplay appDisplay = new AppDisplay();
            appDisplay.setItemType(ItemTypeEnum.values()[enFaker.number().numberBetween(0, ItemTypeEnum.values().length)]);
            appDisplay.setItem(enFaker.number().numberBetween(100000, 999999) + "");
            appDisplay.setDisplayType(DisplayTypeEnum.values()[enFaker.number().numberBetween(0, DisplayTypeEnum.values().length)]);
            appDisplay.setOrder(i);
            appDisplayList.add(appDisplay);
        }
        return appDisplayList;
    }

    private AppStart buildAppStart() {
        AppStart appStart = new AppStart();
        appStart.setEntry(AppCommonConstants.entryList.get(enFaker.number().numberBetween(0, AppCommonConstants.entryList.size())));
        appStart.setOpenAdId((long) enFaker.number().numberBetween(10000, 99999));
        appStart.setOpenAdDuration((long) enFaker.number().numberBetween(5000, 5000));
        appStart.setOpenAdSkipTime((long) enFaker.number().numberBetween(500, 4999));
        return appStart;
    }

    private AppPage buildAppPage(List<Spu> spuList) {
        AppPage appPage = new AppPage();
        appPage.setLastPageId(PageIdEnum.values()[enFaker.number().numberBetween(0, PageIdEnum.values().length)]);
        appPage.setCurrentPageId(PageIdEnum.values()[enFaker.number().numberBetween(0, PageIdEnum.values().length)]);

        if(appPage.getCurrentPageId().equals(PageIdEnum.product_detail)) {
            appPage.setItemType(ItemTypeEnum.spu_id);
            appPage.setItem(spuList.get(enFaker.number().numberBetween(0, spuList.size())).getId().toString());
        }else if(appPage.getCurrentPageId().equals(PageIdEnum.product_list)) {
            appPage.setItemType(ItemTypeEnum.keyword);
            appPage.setItem(AppCommonConstants.keywordList.get(enFaker.number().numberBetween(0, AppCommonConstants.keywordList.size())));
            appPage.setDisplayType(DisplayTypeEnum.search);
        }else if(appPage.getCurrentPageId().equals(PageIdEnum.coupon_list)
                || appPage.getCurrentPageId().equals(PageIdEnum.seckill_list) ) {
            appPage.setDisplayType(DisplayTypeEnum.seckill);
        }

        appPage.setDuringTime(enFaker.number().numberBetween(500, 5000));
        return appPage;
    }

    private AppCommon buildAppCommon(List<MemberPO> memberPOList) {
        AppCommon appCommon = new AppCommon();
        appCommon.setDeviceId(enFaker.number().numberBetween(100000, 999999) + "");

        appCommon.setUid(memberPOList.get(enFaker.number().numberBetween(0, memberPOList.size())).getId().toString());
        appCommon.setVersion(AppCommonConstants.versionList.get(enFaker.number().numberBetween(0, AppCommonConstants.versionList.size())));
        appCommon.setChannel(AppCommonConstants.channelList.get(enFaker.number().numberBetween(0, AppCommonConstants.channelList.size())));
        appCommon.setArea(cnFaker.address().countryCode());
        appCommon.setModel(AppCommonConstants.modelList.get(enFaker.number().numberBetween(0, AppCommonConstants.modelList.size())));
        appCommon.setBrand(AppCommonConstants.brandList.get(enFaker.number().numberBetween(0, AppCommonConstants.brandList.size())));
        appCommon.setIp(cnFaker.internet().ipV4Address());
        appCommon.setLatitude(cnFaker.address().latitude());
        appCommon.setLongitude(cnFaker.address().longitude());
        appCommon.setNet(AppCommonConstants.netList.get(enFaker.number().numberBetween(0, AppCommonConstants.netList.size())));
        return appCommon;
    }
}
