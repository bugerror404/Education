package com.design.education.controller.client;

import com.design.education.entity.Article;
import com.design.education.entity.User;
import com.design.education.entity.responseData.ResponseData;
import com.design.education.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController {
    @Autowired
    private IUserService userService;
   @RequestMapping("/register")
    public String register(){
       return "comm/register";
    }
    //注册
    @PostMapping("/register1")
    @ResponseBody
    public ResponseData RegisterUser(User user){
        try{
            userService.RegisterUser(user);
            return ResponseData.ok();
        }catch (Exception e){
            return ResponseData.ok();
        } }}
