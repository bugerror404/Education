package com.design.education.service.impl;

import com.design.education.entity.Article;

import com.design.education.entity.User;
import com.design.education.entity.UserAuthority;

import com.design.education.mapper.UserAuthorityMapper;
import com.design.education.mapper.UserMapper;
import com.design.education.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAuthorityMapper userAuthorityMapper;
    @Override
    public void RegisterUser(User user) {
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreated(new Date());
        user.setValid(true);
        userMapper.insert(user);
        User user1=userMapper.selectByName(user.getUsername());
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setUserId(user1.getId());
        userAuthority.setAuthorityId(2);
        userAuthorityMapper.insert(userAuthority);
    }
//分页查询
    @Override
    public PageInfo<User> selectUsersByPage(Integer page, Integer count) {
        PageHelper.startPage(page,count);
        List<User> userList=userMapper.selectUsers();
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }
//删除用户
    @Override
    public void deleteUserById(int id) {
        userMapper.deleteByPrimaryKey(id);
    }
    //修改用户信息
    @Override
    public User selectUserById(Integer id) {
        User user=null;
        user = userMapper.selectById(id);
        return user;
    }
//保存普通用户的修改后的信息
    @Override
    public void updateUserById(User user) {
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userMapper.updateByPrimaryKeySelective(user);
    }
//保存信息
    @Override
    public void publishUser(User user) {
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        userMapper.insert(user);

    }
    //根据用户名获得id

    @Override
    public User selectUserByUsername(String username) {
        User user=userMapper.selectByUsername(username);
        return user;
    }
}
