package com.whoiszxl.entity;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.response.RechargeResponse;
import com.whoiszxl.enums.UpchainStatusEnum;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.factory.CreateDcAddressFactory;
import com.whoiszxl.feign.CreateAddressFeignClient;

import java.math.BigDecimal;

/**
 * 数字货币支付信息构建器
 */
public class PayInfoDcBuilder {

    /**
     * 数字货币支付信息实体
     */
    private PayInfoDc dcPayInfo = new PayInfoDc();

    /**
     * 地址创建工厂
     */
    private CreateDcAddressFactory createDcAddressFactory;

    private String dcName;

    private PayInfoDcBuilder() {}

    public PayInfoDcBuilder init(CreateDcAddressFactory createDcAddressFactory, String dcName) {
        this.createDcAddressFactory = createDcAddressFactory;
        this.dcName = dcName.toUpperCase().trim();
        return this;
    }

    /**
     * 获取数字货币支付信息构建器实例对象
     * @return 实例对象
     */
    public static PayInfoDcBuilder get() {
        return new PayInfoDcBuilder();
    }

    /**
     * 构建基础支付信息
     * @param order 订单信息
     * @return
     */
    public PayInfoDcBuilder buildBaseData(Order order) {
        this.dcPayInfo.setOrderId(order.getId());
        this.dcPayInfo.setOrderNo(order.getOrderNo());
        this.dcPayInfo.setMemberId(order.getMemberId());
        return this;
    }

    /**
     * 构建地址信息
     * @return
     */
    public PayInfoDcBuilder buildAddress(Order order) {
        CreateAddressFeignClient createAddressFeignClient = createDcAddressFactory.get(dcName);
        ResponseResult<RechargeResponse> rechargeResponseResult =
                createAddressFeignClient.giveAddress(
                    order.getId().toString(),
                    order.getTotalAmount().toPlainString());

        if(!rechargeResponseResult.isOk()) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("支付创建失败"));
        }

        RechargeResponse rechargeResponse = rechargeResponseResult.getData();

        this.dcPayInfo.setCurrencyName(dcName);
        this.dcPayInfo.setCurrencyId(rechargeResponse.getCurrencyId());
        this.dcPayInfo.setToAddress(rechargeResponse.getAddress());
        this.dcPayInfo.setQrcodeData(rechargeResponse.getQrCodeData());
        return this;
    }

    /**
     * 初始化支付信息状态
     * @return
     */
    public PayInfoDcBuilder initStatus() {
        this.dcPayInfo.setUpchainStatus(UpchainStatusEnum.NOT_UPCHAIN.getCode());
        return this;
    }

    /**
     * 创建数字货币交易信息
     * @return
     */
    public PayInfoDc create() {
        return this.dcPayInfo;
    }


    /**
     * 汇率换算
     * @param order
     * @return
     */
    public PayInfoDcBuilder rateCompute(Order order) {
        BigDecimal finalAmount = BigDecimal.ZERO;
        switch (dcName) {
            case "ETH":
                finalAmount = order.getFinalPayAmount().divide(new BigDecimal(10000));
                break;
            case "BTC":
                finalAmount = order.getFinalPayAmount().divide(new BigDecimal(10000));
                break;
            case "SHOPZZ":
                finalAmount = order.getFinalPayAmount();
                break;
            default:
                finalAmount = order.getFinalPayAmount();
                break;

        }
        dcPayInfo.setTotalAmount(finalAmount);
        return this;
    }
}
