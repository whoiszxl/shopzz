package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.dto.CartDTO;
import com.whoiszxl.zero.vo.MemberAddressVO;
import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author zhouxiaolong
 * @date 2021/4/27
 */
@Data
public class OrderConfirmVO {

    List<MemberAddressVO> addressList;

    List<CartDTO> checkedItem;

    Integer canUsePoint;

}
