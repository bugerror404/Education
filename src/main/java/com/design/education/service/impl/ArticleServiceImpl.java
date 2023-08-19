package com.design.education.service.impl;

import com.design.education.entity.Article;
import com.design.education.entity.Statistic;
import com.design.education.mapper.ArticleMapper;
import com.design.education.mapper.CommentMapper;
import com.design.education.mapper.StatisticMapper;
import com.design.education.service.IArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CommentMapper commentMapper;

    @Override //分页查询文章列表
    public PageInfo<Article> selectArticelsByPage(Integer page, Integer count) {
        PageHelper.startPage(page,count);
        List<Article> articleList=articleMapper.selectArticles();
        //封装文章的统计数据
        for (Article article:articleList){
            Statistic statistic = statisticMapper.selectByAid(article.getId());
            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
        }
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);
        return pageInfo;
    }

    @Override //获取热度排名前十的文章列表
    public List<Article> getHeatArticle() {
        List<Statistic> list = statisticMapper.getStatistic();
        ArrayList<Article> articleList = new ArrayList<>();
        int i=0;
        for (Statistic statistic:list){
            Article article = articleMapper.selectById(statistic.getArticleId());
            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
            articleList.add(article);
            i++;
            if (i>=9){
                break;
            }
        }
        return articleList;
    }

    @Override
    public Article selectArticleById(Integer id) {
        Article article=null;
        Object o = redisTemplate.opsForValue().get("article_" + id);
        if (o!=null){
            article=(Article)o;
        }else{
            article = articleMapper.selectById(id);
            if (article!=null){
                redisTemplate.opsForValue().set("article_"+id,article);
            }
        }
        return article;
    }

    @Override
    public void publishArticle(Article article) {
        //确定开课时间
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
       //去除文章中的表情
        article.setContent(EmojiParser.parseToAliases(article.getContent()));
        article.setCategories(article.getCategories());
        article.setCreated(new Date());
        article.setClassbegin(c.getTime());
        article.setHits(0);
        article.setCommentsNum(0);
        articleMapper.insert(article);
        statisticMapper.addStatistic(article);
    }
    //方法一：StatisticMapper中新增一个方法addStatistic(Article article)
    //System.out.println("当前日期:"+sf.format(c.getTime()));
    //System.out.println("增加一天后日期:"+sf.format(c.getTime()));
    //方法二：利用系统自动生成的insert方法，构建所需的Statistic对象
//        Statistic statistic=new Statistic();
//        statistic.setCommentsNum(0);
//        statistic.setHits(0);
//        statistic.setArticleId(article.getId());
//        statisticMapper.insert(statistic);
    //修改文章
    @Override
    public void updateArticleById(Article article) {
        article.setModified(new Date());
        articleMapper.updateByPrimaryKeySelective(article);
        redisTemplate.delete("article_"+article.getId());
    }

    @Override  //删除文章
    public void deleteArticleById(int id) {
//1.删除文章
        articleMapper.deleteByPrimaryKey(id);
        //2.删除缓存
        redisTemplate.delete("article_"+id);
        //3.删除统计数据
        statisticMapper.deleteByPrimaryKey(id);
        //4.删除评论数据
        commentMapper.deleteByAId(id);
    }
//根据用户名进行课程分页查询
    @Override
    public PageInfo<Article> selectArticelsByUsername(int page, int count, String username) {
        PageHelper.startPage(page,count);
        List<Article> articles = articleMapper.selectByUsername(username);
        PageInfo<Article> articlePageInfo = new PageInfo<>(articles);
        return articlePageInfo;
    }
//根据标签进行模糊查询
    @Override
    public PageInfo<Article> selectArticleByTag(int page, int count, String search) {
        PageHelper.startPage(page,count);
        List<Article> articles = articleMapper.selectByTag(search);
        PageInfo<Article> articlePageInfo = new PageInfo<>(articles);
        return articlePageInfo;
    }
    //根据用户id进行查询

//根据用户id展示我的收藏
    @Override
    public PageInfo<Article> selectCollectsByuId(int page, int count, String id) {
        PageHelper.startPage(page,count);
        List<Article> articles = articleMapper.selectByuId(id);
        PageInfo<Article> articlePageInfo = new PageInfo<>(articles);
        return articlePageInfo;
    }
    //根据大中小进行分类查询
    @Override
    public PageInfo<Article> selectArticleByCategory(int page, int count, String contents) {
        PageHelper.startPage(page,count);
        List<Article> articles = articleMapper.selectByCategory(contents);
        PageInfo<Article> articlePageInfo = new PageInfo<>(articles);
        return articlePageInfo;
    }
    //根据用户id查找课程
    @Override
    public PageInfo<Article> selectCartByuId(int page, int count, Integer id) {
        PageHelper.startPage(page,count);
        List<Article> articles = articleMapper.selectCartByuId(id);
        PageInfo<Article> articlePageInfo = new PageInfo<>(articles);
        return articlePageInfo;
    }
    //根据购物车进行用户查询
    @Override
    public List<Article> selectCartByuIdm(Integer id) {
        List<Article> articles = articleMapper.selectCartByuIdm(id);
        return articles;
    }

    //购物车的总金额
    @Override
    public int plusMoneyByid(Integer id) {
        int i = articleMapper.plusMoneyById(id);
        return i;
    }
    //添加课程
    @Override
    public void insertClass(Article article) {
//        SimpleDateFormat f=new SimpleDateFormat("yyyy年MM月dd日 E kk点mm分");
//        article.setClassbegin(f.format(article.getClassbegin()));
        article.setAllowComment(true);
        article.setCreated(new Date());
        article.setClassbegin(new Date());
        articleMapper.insert(article);
    }
}