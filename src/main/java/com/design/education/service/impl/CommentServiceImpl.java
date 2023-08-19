package com.design.education.service.impl;

import com.design.education.entity.Comment;
import com.design.education.entity.Statistic;
import com.design.education.mapper.CommentMapper;
import com.design.education.mapper.StatisticMapper;
import com.design.education.service.ICommentService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private StatisticMapper statisticMapper;
    @Override
    public PageInfo<Comment> getComments(Integer aid, int page, int count) {
        List<Comment> comments = commentMapper.selectByAid(aid);
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments);
        return commentPageInfo;
    }
    //更新文章评论
    @Override
    public void pushComment(Comment comment) {
        //1.新增一条评论
        commentMapper.insert(comment);
        //2.更新文章统计数据
        Statistic statistic = statisticMapper.selectByAid(comment.getArticleId());
        statistic.setCommentsNum(statistic.getCommentsNum()+1);
        statisticMapper.updateArticleComments(statistic);

    }
}
