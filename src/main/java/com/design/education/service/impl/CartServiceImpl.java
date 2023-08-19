package com.design.education.service.impl;

import com.design.education.entity.Article;
import com.design.education.entity.Cart;
import com.design.education.entity.User;
import com.design.education.mapper.ArticleMapper;
import com.design.education.mapper.CartMapper;
import com.design.education.mapper.UserMapper;
import com.design.education.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CartMapper cartMapper;
    @Override
    public void insertCart(Integer id, Cart cart) {
        //获得课程id
        Article article = articleMapper.selectById(id);
        cart.setaId(article.getId());
        //获得用户id
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.selectByUsername(username);
        cart.setuId(user.getId());
        //插入表中
        cartMapper.insert(cart);
    }

    @Override
    public int selectCartById() {
        //获得当前登录用户的id
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.selectByUsername(username);
        Cart cart = cartMapper.selectByPrimaryKey(user.getId());
        return cart.getaId();
    }
//根据id获得当前的购物车
    @Override
    public Cart selectCartByaId(Integer id) {
        Cart cart = cartMapper.selectByPrimaryKey(id);
        return cart;
    }
    //根据id删除购物车中的信息

    @Override
    public void deleteCartById(Integer id,Integer uId) {

        cartMapper.deleteById(id,uId);
    }

}
