package com.whoiszxl.entity.vo;

import com.whoiszxl.dto.CartDTO;
import com.whoiszxl.dto.MemberAddressDTO;
import com.whoiszxl.dto.MemberCouponDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单确认页面的返回数据
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@Data
@ApiModel("订单确认页面的返回数据")
public class OrderConfirmVO {

    @ApiModelProperty("会员的收货地址列表")
    private List<MemberAddressDTO> memberAddressDTOS;

    @ApiModelProperty("选中的购物车条目")
    private List<CartDTO> checkItem;

    @ApiModelProperty("库存情况")
    private Map<Long, Integer> stocks;

    @ApiModelProperty("我的优惠券")
    private List<MemberCouponDTO> myCoupons;

    /**
     * 获取总支付金额
     * @return 总支付金额
     */
    public BigDecimal getTotalAmount() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (ObjectUtils.isNotEmpty(checkItem)) {
            for (CartDTO item : checkItem) {
                BigDecimal itemPrice = item.getPrice().multiply(new BigDecimal(item.getQuantity().toString()));
                totalAmount = totalAmount.add(itemPrice);
            }
        }
        return totalAmount;
    }
}
