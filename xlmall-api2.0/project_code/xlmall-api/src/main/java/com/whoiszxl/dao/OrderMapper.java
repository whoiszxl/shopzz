package com.whoiszxl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.whoiszxl.entity.Order;

public interface OrderMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
    Order selectByUserIdAndOrderNo(@Param("userId")Integer userId,@Param("orderNo")Long orderNo);
    
    Order selectByOrderNo(Long orderNo);
    
    
    List<Order> selectByUserId(Integer userId);

    List<Order> selectAllOrder();
    
    /**
     * 查询两小时之前的未支付的订单
     * @param status
     * @param date
     * @return
     */
    List<Order> selectOrderStatusByCreateTime(@Param("status") Integer status, @Param("date") String date);
    
    /**
     * 通过订单id关闭订单
     * @param id
     * @return
     */
    int closeOrderByOrderId(Integer id);
}