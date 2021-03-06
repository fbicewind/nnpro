package cn.nnnight.dao.impl;

import cn.nnnight.dao.UserLoginHistoryDao;
import cn.nnnight.entity.BlogVisitor;
import cn.nnnight.entity.UserLoginHistory;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserLoginHistoryDaoImpl extends BaseDaoImpl<UserLoginHistory> implements UserLoginHistoryDao {

    @Override
    public boolean hasLogin(Map<String, Object> values, Session session) {
        StringBuilder hql = new StringBuilder("from UserLoginHistory where userId=:userId");
        hql.append(" and loginIp=:ip and loginStatus=:status and loginTime>:date");
        Query query = session.createQuery(hql.toString());
        if (values != null) {
            query.setProperties(values);
        }
        List<BlogVisitor> list = query.getResultList();
        return list != null && !list.isEmpty();
    }
}
