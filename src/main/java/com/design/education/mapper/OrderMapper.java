package com.design.education.mapper;

import com.design.education.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Long oId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long oId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    //文章分页查询
    public List<Order> selectOrders();

    List<Order> selectByUsername(String username);

    Order selectByoId(long oId);

    int updateOrder(Order record);

    Order selectById(String id);

    List<Order> selectByState(String search);



//根据生成时间查询最新订单
    //Order selectByTime();
}