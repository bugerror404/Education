package com.design.education.mapper;

import com.design.education.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectById(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    //文章分页查询
    public List<Article> selectArticles();
    //站点服务统计,统计文章数量
    public Integer countArticle();

    List<Article> selectByUsername(String username);

    List<Article> selectByTag(String search);

    List<Article> selectByuId(String id);
//根据大中小分类查询
    List<Article> selectByCategory(String contents);
//根据用户id进行购物车查询
    List<Article> selectCartByuId(Integer id);

    int plusMoneyById(Integer id);

    List<Article> selectCartByuIdm(Integer id);

}