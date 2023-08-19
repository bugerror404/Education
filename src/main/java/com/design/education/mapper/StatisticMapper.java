package com.design.education.mapper;

import com.design.education.entity.Article;
import com.design.education.entity.Statistic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticMapper {
    int deleteByPrimaryKey(Integer articleId);

    int insert(Statistic record);

    int insertSelective(Statistic record);

    Statistic selectByAid(Integer articleId);

    int updateByPrimaryKeySelective(Statistic record);

    int updateByPrimaryKey(Statistic record);

    //添加：统计文章总访问量
    long getTotalHit();
    //添加：统计文章总评论量
    long getTotalComment();
    //添加：统计文章热度信息（排序规则：先按点击量；再按评论数）
    List<Statistic> getStatistic();
    //更新文章点击量
    int updateArticleHits(Statistic statistic);
    //更新文章评论
    int updateArticleComments(Statistic statistic);
    //新增文章对应的统计信息
    int addStatistic(Article article);
}