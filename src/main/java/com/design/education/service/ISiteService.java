package com.design.education.service;


import com.design.education.entity.Article;
import com.design.education.entity.Comment;
import com.design.education.entity.responseData.StaticticsBo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ISiteService{
    //更新某个文章的统计数据
    public void updateStatistic(Article article);
    //获取最新文章
    public List<Article> recentArticles(int limit);
    //获取最新评论
    public List<Comment> recentComments(int limit);
    //获取统计信息的总数
    public StaticticsBo getStatistics();

}
