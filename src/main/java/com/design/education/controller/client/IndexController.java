package com.design.education.controller.client;


import com.design.education.entity.*;
import com.design.education.mapper.CollectMapper;
import com.design.education.service.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private IArticleService articleService;
    @Autowired
    private ISiteService siteService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICollectService collectService;

    //1.跳转到博客首页
    @GetMapping("/")
    public String index(HttpServletRequest request) {

        return this.index(request, 1, 7);
    }

    //2.跳转到文章的第几页
    @GetMapping("/page/{p}")

    public String index(HttpServletRequest request, @PathVariable("p") int page,
                        @RequestParam(value = "count", defaultValue = "7") int count) {
        PageInfo<Article> articles = articleService.selectArticelsByPage(page, count);
        List<Article> articleList = articleService.getHeatArticle();
        request.setAttribute("articles", articles);
        request.setAttribute("articleList", articleList);
        //根据是否登录显示用户是否收藏该课程
        return "client/index";
    }

    //跳转到文章详情页面
    @GetMapping(value = "/article/{id}")
    public String getArticleById(@PathVariable("id") Integer id, HttpServletRequest request) {
        //1.获取点击的文章对象
        Article article = articleService.selectArticleById(id);
        //当点击文章链接后，文章的点击量就改变了，统计数据就改变了
        if (article != null) {
            //更新文章的点击量
            siteService.updateStatistic(article);
            //获取文章对应的评论内容
            //getCommentById(request,article);
            request.setAttribute("article", article);
            return "client/articleDetails";
        } else {
            return "comm/error_404";
        }


    }

    private void getCommentById(HttpServletRequest request, Article article) {
        if (article.getAllowComment()) {
            //cp表示评论页码，从页面传来参数
            String cp = request.getParameter("cp");
            cp = StringUtils.isBlank(cp) ? "1" : cp;
            request.setAttribute("cp", cp);
            PageInfo<Comment> comments = commentService.getComments(article.getId(), Integer.parseInt(cp), 3);
            request.setAttribute("comments", comments);
        }

    }

    @PostMapping("/search")
    public String searchByTag(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "count", defaultValue = "10") int count,
                              @RequestParam("search") String search,
                              HttpServletRequest request) {
        PageInfo<Article> pageInfo = articleService.selectArticleByTag(page, count, search);
        request.setAttribute("articles", pageInfo);
        return "client/searchindex";

    }

    //后台页面
    //跳转到用户信息修改页面
    @GetMapping("/modifyuser")
    public String editUser(HttpServletRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.selectUserByUsername(username);//bug
        //BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        request.setAttribute("users", user);
        request.setAttribute("username", user.getUsername());
        request.setAttribute("password", user.getPassword());
        request.setAttribute("identity", user.getIdentity());
        return "back/user_edit";
    }

    //用户修改后的信息保存
    @PostMapping("/usermodifynext")
    public String UserModify(User user) {

        userService.updateUserById(user);
        return "back/index";
    }

    @GetMapping(value = "/collect/{id}")
    public String insertCollect(@PathVariable("id") Integer id, Collect collect) {
        //根据id值查找收藏表中是由已存在课程
        Collect collect1 = collectService.selectCollectById(id);
        if (collect1 != null) {
            //插入表中
            return "client/fail";
        }
        collectService.insertCollect(id, collect);
        return "client/Success";
    }

    //根据课程分类分页显示课程
    @GetMapping("/class/selectClassByCategory")
    public String selectClassByCategory(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "count", defaultValue = "10") int count,
                                        HttpServletRequest request) {
        String contents = "小学";
        PageInfo<Article> pageInfo = articleService.selectArticleByCategory(page, count, contents);
        System.out.println(pageInfo);
        request.setAttribute("articles", pageInfo);
        return "client/searchindex";
    }

    @GetMapping("/class/selectClassByCategory1")
    public String selectClassByCategory1(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "count", defaultValue = "10") int count,
                                         HttpServletRequest request) {
        String contents = "中学";
        PageInfo<Article> pageInfo = articleService.selectArticleByCategory(page, count, contents);
        System.out.println(pageInfo);
        request.setAttribute("articles", pageInfo);
        return "client/searchindex";
    }

    @GetMapping("/class/selectClassByCategory2")
    public String selectClassByCategory2(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "count", defaultValue = "10") int count,
                                         HttpServletRequest request) {
        String contents = "高中";
        PageInfo<Article> pageInfo = articleService.selectArticleByCategory(page, count, contents);
        System.out.println(pageInfo);
        request.setAttribute("articles", pageInfo);
        return "client/searchindex";
    }

    @GetMapping("/class/selectClassByCategory3")
    public String selectClassByCategory3(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "count", defaultValue = "10") int count,
                                         HttpServletRequest request) {
        String contents = "大学";
        PageInfo<Article> pageInfo = articleService.selectArticleByCategory(page, count, contents);
        System.out.println(pageInfo);
        request.setAttribute("articles", pageInfo);
        return "client/searchindex";
    }

}
