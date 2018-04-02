package cn.nnnight.dao;

import cn.nnnight.entity.Article;
import cn.nnnight.util.Pager;

import java.util.Map;

public interface ArticleDao extends BaseDao<Article> {

    public Pager<Article> selectArticles(Integer pageNo, Integer pageSize, Map<String, Object> values);

    public Pager<Article> selectFavorableArticles(Integer pageNo, Integer pageSize, Map<String, Object> values);

    public Article getPrev(Map<String, Object> values);

    public Article getTopPrev(Map<String, Object> values);

    public Article getNext(Map<String, Object> values);

    public Article getNoTopNext(Map<String, Object> values);

}
