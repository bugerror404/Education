package com.design.education.service;

import com.design.education.entity.Comment;
import com.github.pagehelper.PageInfo;

public interface ICommentService {
    //分页显示评论（如果数据是需要分页展示，从Service层开始体现分页）
    public PageInfo<Comment> getComments(Integer aid, int page, int count);
    //用户发表评论
    public void pushComment(Comment comment);
}
