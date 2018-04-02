package cn.nnnight.dao.impl;

import cn.nnnight.dao.ArticleVisitorDao;
import cn.nnnight.entity.ArticleVisitor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleVisitorDaoImpl extends BaseDaoImpl<ArticleVisitor> implements ArticleVisitorDao {

    @Override
    public ArticleVisitor getRecord(Integer articleId, Integer userId, String ipAddr) {
        StringBuilder hql = new StringBuilder("from ArticleVisitor where (articleId=:articleId and userId=:userId and userId<>0)");
        hql.append(" or (articleId=:articleId and userId=:userId and ipAddress=:ipAddress)");
        Map<String, Object> values = new HashMap<>();
        values.put("articleId", articleId);
        values.put("userId", userId);
        values.put("ipAddress", ipAddr);
        List<ArticleVisitor> list = super.findListByHql(hql.toString(), values);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
}
