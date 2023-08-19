package com.design.education.service.impl;

import com.design.education.entity.Article;
import com.design.education.entity.Collect;
import com.design.education.entity.User;
import com.design.education.mapper.ArticleMapper;
import com.design.education.mapper.CollectMapper;
import com.design.education.mapper.UserMapper;
import com.design.education.service.ICollectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CollectServiceImpl implements ICollectService {
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    /**
     * 最大支持1-9个集群的机器部署
     */
    public static final int MACHINE_ID = 1;
    @Override
    public void insertCollect(Integer id, Collect collect) {
        //随机生成id
//        int machineId = MACHINE_ID;
//        int hashCodeV = UUID.randomUUID().toString().hashCode();
//        //System.out.println("hashCodeV：" + hashCodeV);
//        if (hashCodeV < 0) {
//            hashCodeV = -hashCodeV;
//        }
//        // 0 代表前面补充0
//        // 4 代表长度为4
//        // d 代表参数为正整型
//        //edit---20181203 hzj
//        Date date1=new Date();
//        collect.setId(Integer.valueOf((machineId + String.format("%015d", hashCodeV)+date1.getTime())));
        //获得课程id
        Article article = articleMapper.selectById(id);
        collect.setaId(article.getId());
        //获得用户id
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.selectByUsername(username);
        collect.setuId(user.getId());
        collectMapper.insert(collect);
    }

    @Override
    public Collect selectCollectById(Integer id) {
        Collect collect = collectMapper.selectByPrimaryKey(id);
        return collect;
    }
//根据用户id查询收藏表

    @Override
    public Collect selectCollectByuId(Integer id) {
        Collect collect = collectMapper.selectByuId(id);
        return collect;
    }
    //删除收藏的课程

    @Override
    public void deleteClassByaId(Integer id) {
        collectMapper.deleteByPrimaryKey(id);
    }
}
