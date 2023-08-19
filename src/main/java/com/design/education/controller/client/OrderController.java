package com.design.education.controller.client;

import com.design.education.entity.Article;
import com.design.education.entity.Order;
import com.design.education.entity.User;
import com.design.education.service.IArticleService;
import com.design.education.service.IOrderService;
import com.design.education.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class OrderController {
//    @Autowired
//    private IUserService userService;
//    @GetMapping(value = "/order/{id}")
//    public String getUserById(@PathVariable("id") Integer id, HttpServletRequest request) {
//        //1.获取当前登录的用户
//        User user=userService.selectById(id);
//        if (user!=null){
//            request.setAttribute("user", user);
//            return "client/orderDetails";
//        }
//        else {
//            return "comm/error_404";
//        }
//    }
//    @RequestMapping("/order")
//    public String goOrderDetails(){
//
//        return "client/orderDetails";
//    }
@Autowired
private IArticleService articleService;
@Autowired
private IOrderService orderService;
    //跳转到订单详情页面
    @GetMapping(value = "/order/{id}")
    public String getArticleById(@PathVariable("id") Integer id, HttpServletRequest request) {
        //1.获取点击的文章对象
        Article article = articleService.selectArticleById(id);
        if (article != null) {
            request.setAttribute("article", article);
            return "client/orderDetails";
        } else {
            return "comm/error_404";
        }
    }
    //向支付页面跳转，形成新的订单信息并写入数据库
//    @RequestMapping("/submitOrder")
//    public String pay(Order order,HttpServletRequest request){
//        //将新的订单信息插入订单信息表中
//        orderService.insertOrder(order);
//        //System.out.println(order.getoId());
//
//       return "client/payDetails";
//    }
    @RequestMapping("/submitOrder/{id}")
    public String pay(@PathVariable("id") Integer id, Order order,HttpServletRequest request){
        //将新的订单信息插入订单信息表中
        orderService.insertOrder(order);
        //根据订单信息表获得当前的订单号传入前台
        //long oId=(int)id;
        Order order1=orderService.selectOrderByoId(id);
        if (order1 != null) {
            request.setAttribute("order", order1);
            return "client/payDetails";
        } else {
            return "comm/error_404";
        }
    }
    //向支付成功页面跳转
    @PostMapping("/pay/success")
    public String paysuccess(Order order){
        //根据id查询当前的订单
        //Order order1=orderService.selectOrderByoId1(oId);
        //修改表中的数据
        orderService.updateOrder(order);

        return "client/paySuccess";
    }

    //修改订单表的用户信息
    @GetMapping("/order/updateOrder")
    public String updateOrder(){
        //修改数据
        return "client/payfail";
    }


}
