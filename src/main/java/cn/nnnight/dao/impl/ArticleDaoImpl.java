package cn.nnnight.dao.impl;

import cn.nnnight.dao.ArticleDao;
import cn.nnnight.entity.Article;
import cn.nnnight.util.Pager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ArticleDaoImpl extends BaseDaoImpl<Article> implements ArticleDao {

    @Override
    public Pager<Article> selectArticles(Integer pageNo, Integer pageSize, Map<String, Object> values) {
        StringBuilder hql = new StringBuilder("from Article where userId=:userId and draftFlag=:draftFlag");
        hql.append(" and delFlag=:delFlag");
        if (values.containsKey("typeId")) {
            hql.append(" and typeId=:typeId");
        }
        if (values.containsKey("publicFlag")) {
            hql.append(" and publicFlag=:publicFlag");
        }
        hql.append(" order by topFlag desc,updateTime desc");
        return super.selectPageByHql(pageNo, pageSize, hql.toString(), values);
    }

    @Override
    public Pager<Article> selectFavorableArticles(Integer pageNo, Integer pageSize, Map<String, Object> values) {
        StringBuilder hql = new StringBuilder("select b from Article b inner join ArticleFavorite bf on b.id=bf.articleId");
        hql.append(" and b.draftFlag=:draftFlag and b.publicFlag=:publicFlag");
        hql.append(" and b.delFlag=:delFlag and bf.delFlag=:delFlag and bf.userId=:userId");
        hql.append(" order by bf.createTime desc");
        return super.selectPageByHql(pageNo, pageSize, hql.toString(), values);
    }

    @Override
    public Article getPrev(Map<String, Object> values) {
        StringBuilder hql = new StringBuilder("from Article where userId=:userId and topFlag=:topFlag");
        hql.append(" and draftFlag=:draftFlag and publicFlag=:publicFlag");
        hql.append(" and delFlag=:delFlag and updateTime>:updateTime");
        if (values.containsKey("typeId")) {
            hql.append(" and typeId=:typeId");
        }
        hql.append(" order by topFlag asc,updateTime asc");
        List<Article> list = super.findListByHqlLimitCount(hql.toString(), values, 1);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    public Article getTopPrev(Map<String, Object> values) {
        StringBuilder hql = new StringBuilder("from Article where userId=:userId and topFlag=:topFlag");
        hql.append(" and draftFlag=:draftFlag and publicFlag=:publicFlag");
        hql.append(" and delFlag=:delFlag");
        if (values.containsKey("typeId")) {
            hql.append(" and typeId=:typeId");
        }
        hql.append(" order by topFlag asc,updateTime asc");
        List<Article> list = super.findListByHqlLimitCount(hql.toString(), values, 1);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    public Article getNext(Map<String, Object> values) {
        StringBuilder hql = new StringBuilder("from Article where userId=:userId and topFlag=:topFlag");
        hql.append(" and draftFlag=:draftFlag and publicFlag=:publicFlag");
        hql.append(" and delFlag=:delFlag and updateTime<:updateTime");
        if (values.containsKey("typeId")) {
            hql.append(" and typeId=:typeId");
        }
        hql.append(" order by topFlag desc,updateTime desc");
        List<Article> list = super.findListByHqlLimitCount(hql.toString(), values, 1);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    public Article getNoTopNext(Map<String, Object> values) {
        StringBuilder hql = new StringBuilder("from Article where userId=:userId and topFlag=:topFlag");
        hql.append(" and draftFlag=:draftFlag and publicFlag=:publicFlag");
        hql.append(" and delFlag=:delFlag");
        if (values.containsKey("typeId")) {
            hql.append(" and typeId=:typeId");
        }
        hql.append(" order by topFlag desc,updateTime desc");
        List<Article> list = super.findListByHqlLimitCount(hql.toString(), values, 1);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
}
