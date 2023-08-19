package com.design.education.mapper;

import com.design.education.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);

    //根据文章的id查询评论
    List<Comment> selectByAid(Integer aid);
    //获取所有评论数，按照新的时间进行排序
    List<Comment> selectComments();
    //获取总评论数
    Integer countComment();
    //文章删除
    int deleteByAId(Integer aid);
}