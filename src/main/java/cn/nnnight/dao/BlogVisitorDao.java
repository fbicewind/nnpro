package cn.nnnight.dao;

import cn.nnnight.entity.BlogVisitor;
import org.hibernate.Session;

import java.util.Map;

public interface BlogVisitorDao extends BaseDao<BlogVisitor> {

    public boolean hasVisitors(Map<String, Object> values, Session session);
}
