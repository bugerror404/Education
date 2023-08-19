package com.design.education.controller.client;

import com.design.education.entity.Article;
import com.design.education.entity.Cart;
import com.design.education.entity.Order;
import com.design.education.entity.User;
import com.design.education.entity.responseData.ResponseData;
import com.design.education.service.IArticleService;
import com.design.education.service.ICartService;
import com.design.education.service.IOrderService;
import com.design.education.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private IArticleService articleService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IOrderService orderService;
    //跳转到购物车详情页面
    @GetMapping(value = "/cart/{id}")
    public String getArticleById(@PathVariable("id") Integer id, HttpServletRequest request, Cart cart) {
        //根据id值查询购物车中是否已有该课程
        Cart cart1 = cartService.selectCartByaId(id);
        if (cart1!=null){
            return "comm/baomingfail";
        }
        else{
            cartService.insertCart(id,cart);
            //1.获取点击的文章对象
            Article article = articleService.selectArticleById(id);
            if (article != null) {
                request.setAttribute("article", article);
                return "client/cart";
            }
            else {
                 return "comm/error_404";
            }
        }
    }
     //       response.setContentType("text/html;charset=gb2312");
    //        HttpServletResponse response,
    //PrintWriter out = response.getWriter();
    //out.print("<script language=\"javascript\">alert('请勿重复报名！');window.location.reload</script>");
    //购物车内容展示
    @GetMapping("/client/cart")
    public String cartList(@RequestParam(value = "page",defaultValue = "1") int page,
                              @RequestParam(value = "count",defaultValue = "10") int count,
                              HttpServletRequest request) {
        //根据用户名从订单表查询相同用户名的订单信息,从而获得相对应的课程信息
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.selectUserByUsername(username);
        //System.out.println(user.getId());
        PageInfo<Article> pageInfo = articleService.selectCartByuId(page, count, user.getId());
        System.out.println(pageInfo.getPageSize());
        List<Article> articles=articleService.selectCartByuIdm(user.getId());
        //System.out.println(articles);
//        Integer money[]=articles.toArray(new Integer[articles.size()]);
//        int sum=0;
//        for (int i=0;i<=articles.size();i++){
//            sum=sum+articles.get(i).getMoney();
//        }
//       System.out.println(sum);
        request.setAttribute("articles", pageInfo);
       // request.setAttribute("article",sum);
        return "client/cartlist";
    }
    //删除购物车
//    @GetMapping("/cartdetails")
//    public String deleteCart(){
//        return "client/cart";
//    }
    //删除课程
    @GetMapping("/deleteCart/{id}")
    public String deleteCartById(@PathVariable("id") Integer id){
            //找到对应的用户名
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.selectUserByUsername(username);
            cartService.deleteCartById(id,user.getId());
            return "client/deleteSuccess";
    }
    //取消支付后返回购物车页面
    @GetMapping("/back/cart/{id}")
    public String backCart(@PathVariable("id") String id, HttpServletRequest request){
        //根据id值查询订单
        Order order =orderService.selectOrderById(id);
        //1.获取点击的文章对象
            Article article = articleService.selectArticleById(order.getaId());
            if (article != null) {
                request.setAttribute("article", article);
                return "client/cart";
            }
            else {
                return "comm/error_404";
            }
    }
}
