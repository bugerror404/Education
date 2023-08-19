package com.design.education.service;
import com.design.education.entity.Order;
import com.github.pagehelper.PageInfo;

public interface IOrderService {
    //分页查询文章列表
   public PageInfo<Order> selectordersByPage(int page, int count);

    public void deleteOrderByoId(long oId);
    //根据用户名查询订单
    PageInfo<Order> selectordersByUsername(int page, int count, String username);
//插入新的订单信息
    void insertOrder(Order order);
//根据订单生成时间获得订单号
    //Order selectOrderByTime();
    Order selectOrderByoId(Integer id);
//更新订单表

    void updateOrder(Order order);

    Order selectOrderByoId1(long oId);
//修改订单表
    void updateOrderState(Order order);
//根据id查询订单
    Order selectOrderById(String id);

    PageInfo<Order> selectOrderByState(int page, int count, String search);



//根据课程id查询订单
   //Order selectOrderByoId(Integer id);
    //根据id查询文章
    //public Order selectOrderById(Integer id);

}
