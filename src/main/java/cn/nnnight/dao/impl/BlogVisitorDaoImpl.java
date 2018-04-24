package cn.nnnight.dao.impl;

import cn.nnnight.dao.BlogVisitorDao;
import cn.nnnight.entity.BlogVisitor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BlogVisitorDaoImpl extends BaseDaoImpl<BlogVisitor> implements BlogVisitorDao {

    @Override
    public boolean hasVisitors(Map<String, Object> values, Session session) {
        StringBuilder hql = new StringBuilder("from BlogVisitor where userId=:userId");
        hql.append(" and ipAddress=:ip and createTime>:date");
        Query query = session.createQuery(hql.toString());
        if (values != null) {
            query.setProperties(values);
        }
        List<BlogVisitor> list = query.getResultList();
        return list != null && !list.isEmpty();
    }
}
