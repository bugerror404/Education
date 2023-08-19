package com.design.education.service;

import com.design.education.entity.User;
import com.github.pagehelper.PageInfo;

public interface IUserService {
    //用户注册
    public void RegisterUser(User user);
    //根据id查找用户信息
    public User selectUserById(Integer id);
    //分页查询用户列表
    public PageInfo<User> selectUsersByPage(Integer page, Integer count);
    //删除用户
   public void deleteUserById(int id);
    //修改用户信息
    public void updateUserById(User user);
//保存修改后的信息
    public void publishUser(User user);
//根据用户名获得当前的user
    User selectUserByUsername(String username);

}
