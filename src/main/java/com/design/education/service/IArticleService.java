package com.design.education.service;

import com.design.education.entity.Article;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IArticleService {
    //分页查询文章列表
    public PageInfo<Article> selectArticelsByPage(Integer page,Integer count);
    //获取热度排名前十的文章列表
    public List<Article> getHeatArticle();
    //根据id查询文章
    public Article selectArticleById(Integer id);
    //发布文章
    public void publishArticle(Article article);
    //修改文章
    public void updateArticleById(Article article);
    //删除文章
    public void deleteArticleById(int id);
//根据用户名查询订单信息从而获得课程信息
    PageInfo<Article> selectArticelsByUsername(int page, int count, String username);
//根据搜索框进行查询
    PageInfo<Article> selectArticleByTag(int page, int count, String search);
//根据用户id将收藏表和课程表一起查
    PageInfo<Article> selectCollectsByuId(int page, int count, String id);
//根据分类进行课程查询
    PageInfo<Article> selectArticleByCategory(int page, int count, String contents);
//根据用户名查找课程
    PageInfo<Article> selectCartByuId(int page, int count, Integer id);

    int plusMoneyByid(Integer id);

    void insertClass(Article article);
//根据用户id进行购物车查询
    List<Article> selectCartByuIdm(Integer id);


    //PageInfo<Article> selectArticelsByUsername1(int page, int count, String username);

}
