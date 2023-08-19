package com.design.education.controller.client;

import com.design.education.entity.Article;
import com.design.education.entity.Collect;
import com.design.education.entity.Order;
import com.design.education.entity.User;
import com.design.education.entity.responseData.ResponseData;
import com.design.education.service.IArticleService;
import com.design.education.service.ICollectService;
import com.design.education.service.IOrderService;
import com.design.education.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICollectService collectService;
    //跳转到课程管理页面
    @GetMapping("/client/userinformation")
    public String userinformation(@RequestParam(value = "page",defaultValue = "1") int page,
                        @RequestParam(value = "count",defaultValue = "10") int count,
                        HttpServletRequest request){
        //根据用户名从订单表查询相同用户名的订单信息,从而获得相对应的课程信息
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PageInfo<Article> pageInfo = articleService.selectArticelsByUsername(page, count,username);
        request.setAttribute("articles",pageInfo);
        return "client/article_list1";
    }
    //跳转到订单列表
    @GetMapping("/client/order")
    public String index1(@RequestParam(value = "page",defaultValue = "1") int page,
                         @RequestParam(value = "count",defaultValue = "10") int count,
                         HttpServletRequest request){
        //根据用户名从订单表查询相同用户名的订单信息
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        PageInfo<Order> pageInfo = orderService.selectordersByUsername(page, count,username);

        request.setAttribute("orders",pageInfo);
        return "client/order_list1";
    }
    //删除订单
    @PostMapping("/client/order/delete")
    @ResponseBody
    public ResponseData deleteOrder(@RequestParam long oId){
        try{
            orderService.deleteOrderByoId(oId);
            return ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }
    //删除收藏的课程
    @PostMapping("/client/article/delete")
    @ResponseBody
    public ResponseData deleteClass(@RequestParam Integer id){
        try{
            collectService.deleteClassByaId(id);
            return ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }
    //跳转到用户信息修改页面
    @GetMapping("/client/modifyuser")
    public String editUser(HttpServletRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.selectUserByUsername(username);//bug
        //BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        request.setAttribute("users",user);
        request.setAttribute("username",user.getUsername());
        request.setAttribute("password",user.getPassword());
        request.setAttribute("identity",user.getIdentity());
        return "client/user_edit1";
    }
    //用户修改后的信息保存
    @PostMapping("/usermodify")
    public String UserModify(User user){

        userService.updateUserById(user);
        return "client/userindex";
    }
    //跳转到我的收藏页面
    @GetMapping("/client/collect")
    public String collectList(@RequestParam(value = "page",defaultValue = "1") int page,
                              @RequestParam(value = "count",defaultValue = "10") int count,
                              HttpServletRequest request){
        //根据用户名从收藏表查询相同用户名的订单信息,从而获得相对应的课程信息
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.selectUserByUsername(username);
        System.out.println(user.getId());
        String id= Integer.toString(user.getId());
        PageInfo<Article> pageInfo = articleService.selectCollectsByuId(page, count,id);
        request.setAttribute("articles",pageInfo);
        return "client/collect_list";
    }

}
