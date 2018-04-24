package cn.nnnight.dao;

import cn.nnnight.entity.UserLoginHistory;
import org.hibernate.Session;

import java.util.Map;

public interface UserLoginHistoryDao extends BaseDao<UserLoginHistory> {

    public boolean hasLogin(Map<String, Object> values, Session session);
}
