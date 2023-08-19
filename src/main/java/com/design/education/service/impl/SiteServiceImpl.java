package com.design.education.service.impl;

import com.design.education.entity.Article;
import com.design.education.entity.Comment;
import com.design.education.entity.Statistic;
import com.design.education.entity.responseData.StaticticsBo;
import com.design.education.mapper.ArticleMapper;
import com.design.education.mapper.CommentMapper;
import com.design.education.mapper.StatisticMapper;
import com.design.education.service.ISiteService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteServiceImpl implements ISiteService {
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void updateStatistic(Article article) {
        Statistic statistic = statisticMapper.selectByAid(article.getId());
        statistic.setHits(statistic.getHits()+1);
        statisticMapper.updateArticleHits(statistic);
    }

    @Override
    public List<Article> recentArticles(int limit) {
        PageHelper.startPage(1,limit>10||limit<1?10:limit);
        List<Article> articles=articleMapper.selectArticles();
        //封装文章统计数据
        for (Article article:articles){
            Statistic statistic=statisticMapper.selectByAid(article.getId());
            article.setCommentsNum(statistic.getCommentsNum());
        }
        return articles;
    }

    @Override
    public List<Comment> recentComments(int limit) {
        PageHelper.startPage(1,limit>10||limit<1?10:limit);
        List<Comment> comments = commentMapper.selectComments();
        return comments;
    }

    @Override
    public StaticticsBo getStatistics() {
        StaticticsBo staticticsBo=new StaticticsBo();
        Integer articles = articleMapper.countArticle();
        Integer comments = commentMapper.countComment();
        staticticsBo.setArticles(articles);
        staticticsBo.setComments(comments);
        return staticticsBo;
    }
}
