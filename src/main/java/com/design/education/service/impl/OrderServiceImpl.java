package com.design.education.service.impl;


import com.design.education.entity.Article;
import com.design.education.entity.Order;
import com.design.education.entity.Statistic;
import com.design.education.mapper.OrderMapper;
import com.design.education.service.IOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PageInfo<Order> selectordersByPage(int page, int count) {
        PageHelper.startPage(page,count);
        List<Order> orderList=orderMapper.selectOrders();
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        return pageInfo;
    }

    @Override
    public void deleteOrderByoId(long oId) {
        //1.删除文章
        orderMapper.deleteByPrimaryKey(oId);
//        //2.删除缓存
//        redisTemplate.delete("order_"+oId);
    }

    @Override
    public PageInfo<Order> selectordersByUsername(int page, int count, String username) {
      PageHelper.startPage(page,count);
        List<Order> orders = orderMapper.selectByUsername(username);
        PageInfo<Order> orderPageInfo = new PageInfo<>(orders);
        return orderPageInfo;
    }

//新形成的订单插入订单表中
    /**
     * 最大支持1-9个集群的机器部署
     */
    public static final int MACHINE_ID = 1;
    @Override
    public void insertOrder(Order order) {
        order.setOrdertime(new Date());
        order.setPaystate(0);
        order.setReceiverAddress("已取消");
        /**
         * 生成订单号
         */
        int machineId = MACHINE_ID;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        System.out.println("hashCodeV：" + hashCodeV);
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正整型
        //edit---20181203 hzj
        Date date1=new Date();
        order.setId(machineId + String.format("%015d", hashCodeV)+date1.getTime());
        orderMapper.insert(order);
    }

//    @Override
//    public void insertOrder1(Order order) {
//        orderMapper.insert(order);
//    }

    //根据id查询订单
    @Override
    public Order selectOrderByoId(Integer id) {
        long oId = (int) id;
        Order order = orderMapper.selectByoId(oId);
        return order;
    }

    @Override
    public Order selectOrderByoId1(long oId) {
        Order order=orderMapper.selectByoId(oId);
        return order;
    }
    //修改订单信息

    @Override
    public void updateOrder(Order order) {
        order.setReceiverAddress("完成");
        order.setPaystate(1);
        orderMapper.updateOrder(order);
    }

    @Override
    public void updateOrderState(Order order) {
        order.setId(order.getId());
        System.out.println(order.getId());
        order.setReceiverAddress("已取消");
        orderMapper.updateOrder(order);
    }

    @Override
    public Order selectOrderById(String id) {
        Order order = orderMapper.selectById(id);
        return order;
    }
    //根据订单状态进行查询

    @Override
    public PageInfo<Order> selectOrderByState(int page, int count, String search) {
        PageHelper.startPage(page,count);
        List<Order> orders = orderMapper.selectByState(search);
        PageInfo<Order> orderPageInfo = new PageInfo<>(orders);
        return orderPageInfo;
    }
}
