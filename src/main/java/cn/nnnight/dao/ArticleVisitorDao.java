package cn.nnnight.dao;

import cn.nnnight.entity.ArticleVisitor;

public interface ArticleVisitorDao extends BaseDao<ArticleVisitor> {

    public ArticleVisitor getRecord(Integer articleId, Integer userId, String ipAddr);
}
