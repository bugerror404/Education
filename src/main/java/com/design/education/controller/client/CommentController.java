package com.design.education.controller.client;

import com.design.education.entity.Comment;
import com.design.education.entity.responseData.ResponseData;
import com.design.education.service.ICommentService;
import com.design.education.utils.MyUtils;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @PostMapping("/publish")
    @ResponseBody
    public ResponseData pushComment(HttpServletRequest request, @RequestParam Integer aid, @RequestParam String text){
        //1.去除评论内容框中的js脚本
        text= MyUtils.cleanXSS(text);
        text= EmojiParser.parseToAliases(text);
        //2.获取当前登录用户
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //3.封装评论实体信息
        Comment comment=new Comment();
        comment.setArticleId(aid);
        comment.setAuthor(user.getUsername());
        comment.setContent(text);
        comment.setCreated(new Date());
        //改进后的代码
        comment.setStatus("approved");//表中的Status不能为空
        comment.setIp(request.getRemoteAddr());//获取ip地址
        //4.执行发布评论，返回响应数据
        try{
            commentService.pushComment(comment);
            return ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }

}
