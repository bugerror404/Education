package com.design.education.service;

import com.design.education.entity.Cart;
import org.springframework.stereotype.Service;

@Service
public interface ICartService {
    void insertCart(Integer id, Cart cart);

    //根据id获得购物车信息
    int selectCartById();

    //根据id获得购物车信息
    Cart selectCartByaId(Integer id);

    //删除购物车中的课程
    void deleteCartById(Integer id, Integer uId);
}


