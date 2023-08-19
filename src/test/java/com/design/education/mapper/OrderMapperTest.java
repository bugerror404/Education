package com.design.education.mapper;

import com.design.education.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderMapperTest {
    @Autowired
    private OrderMapper orderMapper;
    @Test
    void a(){
        List<Order> uu = orderMapper.selectByUsername("uu");
        System.out.println(uu);
    }
}