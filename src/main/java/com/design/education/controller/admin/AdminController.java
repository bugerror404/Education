package com.design.education.controller.admin;

import com.design.education.entity.Article;
import com.design.education.entity.Comment;
import com.design.education.entity.Order;
import com.design.education.entity.User;
import com.design.education.entity.responseData.ResponseData;
import com.design.education.entity.responseData.StaticticsBo;
import com.design.education.service.IArticleService;
import com.design.education.service.IOrderService;
import com.design.education.service.ISiteService;
import com.design.education.service.IUserService;
import com.github.pagehelper.PageInfo;
//import com.sun.tools.corba.se.idl.constExpr.Or;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ISiteService siteService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserService userService;
    @GetMapping(value = {"","/index"})
    public String index(HttpServletRequest request){
        //获取后台主页所需的数据
        List<Article> articles=siteService.recentArticles(5);
        List<Comment> comments = siteService.recentComments(5);
        StaticticsBo staticticsBo=siteService.getStatistics();
        //向request中存放前台所需的数据
        request.setAttribute("articles",articles);
        request.setAttribute("comments",comments);
        request.setAttribute("statictics",staticticsBo);
        return "back/index";
    }

    //跳转到文章的首页
    @GetMapping("/article/toEditPage")
    public String newArticle(){

        return "back/article_edit";
    }
    //发布文章
    @PostMapping("/article/publish")
    @ResponseBody
    public ResponseData publishArticle(Article article){
        try{
            articleService.publishArticle(article);
            return ResponseData.ok();

        }catch (Exception e){
            return ResponseData.ok();
        }
    }
    //跳转到后台课程列表页面
    @GetMapping("/article")
    public String index(@RequestParam(value = "page",defaultValue = "1") int page,
                        @RequestParam(value = "count",defaultValue = "10") int count,
                        HttpServletRequest request){
        PageInfo<Article> pageInfo = articleService.selectArticelsByPage(page, count);
        request.setAttribute("articles",pageInfo);
        return "back/article_list";
    }
    //跳转到后台订单列表页面
    @GetMapping("/order")
    public String index1(@RequestParam(value = "page",defaultValue = "1") int page,
                        @RequestParam(value = "count",defaultValue = "10") int count,
                        HttpServletRequest request){
        PageInfo<Order> pageInfo = orderService.selectordersByPage(page, count);
        request.setAttribute("orders",pageInfo);
        return "back/order_list";
    }
    //按照类别查询订单
    @PostMapping("/searchOrder")
    public String searchByOrderState(@RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "count", defaultValue = "10") int count,
                                     @RequestParam("search") String search,
                                     HttpServletRequest request) {
        PageInfo<Order> pageInfo = orderService.selectOrderByState(page, count, search);
        request.setAttribute("orders", pageInfo);
        return "back/order_list";
    }
    //删除订单
    @PostMapping("/order/delete")
    @ResponseBody
    public ResponseData deleteOrder(@RequestParam long oId){
        try{
            orderService.deleteOrderByoId(oId);
            return ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }
    //跳转到用户列表页面
    @GetMapping("/user")
    public String index2(@RequestParam(value = "page",defaultValue = "1") int page,
                         @RequestParam(value = "count",defaultValue = "10") int count,
                         HttpServletRequest request){
        PageInfo<User> pageInfo = userService.selectUsersByPage(page, count);
        request.setAttribute("users",pageInfo);
        return "back/user_list";
    }
    //跳转到用户信息修改页面
    @GetMapping("/modifyuser")
    public String editUser( HttpServletRequest request){
        User user = userService.selectUserById(1);//bug
        //BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        request.setAttribute("contents",user);
        request.setAttribute("username",user.getUsername());
        request.setAttribute("password",user.getPassword());
        request.setAttribute("identity",user.getIdentity());
        return "back/user_edit";

    }
    //修改用户信息
    @PostMapping("/user/modify")
    @ResponseBody
    public ResponseData modifyUser(User user){
        try{
            userService.updateUserById(user);
            return  ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }
    //保存修改后的信息
    @PostMapping("/user/publish")
    @ResponseBody
    public ResponseData publishUser(User user){
        try{
            userService.publishUser(user);
            return ResponseData.ok();

        }catch (Exception e){
            return ResponseData.ok();
        }
    }
    //跳转到文章修改页面
    @GetMapping("/article/{id}")
    public String editArticle(@PathVariable("id") Integer id,HttpServletRequest request){
        Article article = articleService.selectArticleById(id);
        request.setAttribute("articles",article);
        request.setAttribute("categories",article.getCategories());
        return "back/article_edit";

    }
    //修改文章
    @PostMapping("/article/modify")
    @ResponseBody
    public ResponseData modifyArticle(Article article){
        try{
            articleService.updateArticleById(article);
            return  ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }
    //删除文章
    @PostMapping("/article/delete")
    @ResponseBody
    public ResponseData deleteArticle(@RequestParam int id){
        try{
            articleService.deleteArticleById(id);
            return ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }

    //删除用户
    @PostMapping("/user/delete")
    @ResponseBody
    public ResponseData deleteUser(@RequestParam int id){
        try{
            userService.deleteUserById(id);
            return ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }
//    //添加课程页面
//    @PostMapping("/insertClass")
//    public String insertClass(Article article){
//        articleService.insertClass(article);
//
//    }
    //按类别查询课程
      @PostMapping("/searchClass")
     public String searchByTag(@RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "count", defaultValue = "10") int count,
                          @RequestParam("search") String search,
                          HttpServletRequest request) {
         PageInfo<Article> pageInfo = articleService.selectArticleByTag(page, count, search);
         request.setAttribute("articles", pageInfo);
         return "back/article_list";
      }


}
