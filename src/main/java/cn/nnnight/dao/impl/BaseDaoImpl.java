package cn.nnnight.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import cn.nnnight.dao.BaseDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import cn.nnnight.util.Pager;

@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

    /**
     * 当前操作到的实体类的类型信息实例
     */
    protected Class<T> clazz;

    /**
     * Session工厂
     */
    @Autowired
    protected SessionFactory sessionFactory;

    /**
     * 构造方法
     */
    public BaseDaoImpl() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class) type.getActualTypeArguments()[0];
    }

    /**
     * 取得当前Session
     */
    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public T get(Serializable id) {
        return getSession().get(clazz, id);
    }

    public T load(Serializable id) {
        return getSession().load(clazz, id);
    }

    public void save(T entity) {
        getSession().save(entity);
    }

    public void update(T entity) {
        getSession().update(entity);
    }

    public void saveOrUpdate(T entity) {
        getSession().saveOrUpdate(entity);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    public void delete(Serializable id) {
        getSession().delete(getSession().load(clazz, id));
    }

    public void delete(Serializable... ids) {
        for (Serializable id : ids) {
            getSession().delete(getSession().load(clazz, id));
        }
    }

    public T getUniqueResult(String param, Object value) {
        Criteria c = createCriteria();
        c.add(Restrictions.eq(param, value));
        return (T) c.uniqueResult();
    }

    public T getUniqueResult(Map<String, ?> values) {
        Criteria c = createCriteria();
        for (String key : values.keySet()) {
            c.add(Restrictions.eq(key, values.get(key)));
        }
        return (T) c.uniqueResult();
    }

    public T getUniqueResultByHql(String hql, Object... values) {
        return (T) createQuery(hql, values).getSingleResult();
    }

    public T getUniqueResultByHql(String hql, Map<String, ?> values) {
        return (T) createQuery(hql, values).getSingleResult();
    }

    public List<T> findAll() {
        return createCriteria().list();
    }

    public List<T> findAll(String order, boolean desc) {
        Criteria c = createCriteria();
        if (desc) {
            c.addOrder(Order.desc(order));
        } else {
            c.addOrder(Order.asc(order));
        }
        return c.list();
    }

    public List<T> findListByProperty(String param, Object value) {
        Criteria c = createCriteria();
        c.add(Restrictions.eq(param, value));
        return c.list();
    }

    public List<T> findListByProperties(Map<String, ?> values) {
        Criteria c = createCriteria();
        for (String key : values.keySet()) {
            c.add(Restrictions.eq(key, values.get(key)));
        }
        return c.list();
    }

    public List<T> findListByProperty(String param, Object value, String order, boolean desc) {
        Criteria c = createCriteria();
        c.add(Restrictions.eq(param, value));
        if (desc) {
            c.addOrder(Order.desc(order));
        } else {
            c.addOrder(Order.asc(order));
        }
        return c.list();
    }

    public List<T> findListByProperties(Map<String, ?> values, String order, boolean desc) {
        Criteria c = createCriteria();
        for (String key : values.keySet()) {
            c.add(Restrictions.eq(key, values.get(key)));
        }
        if (desc) {
            c.addOrder(Order.desc(order));
        } else {
            c.addOrder(Order.asc(order));
        }
        return c.list();
    }

    public List<T> findListByHql(String hql, Object... values) {
        return createQuery(hql, values).getResultList();
    }

    public List<T> findListByHql(String hql, Map<String, ?> values) {
        return createQuery(hql, values).getResultList();
    }

    public List<T> findListByHqlLimitCount(String hql, Map<String, ?> values, int count) {
        return createQuery(hql, values).setMaxResults(count).getResultList();
    }

    public Pager<T> selectPage(int pageNo, int pageSize) {
        Pager<T> p = new Pager<T>();
        StringBuilder sb = new StringBuilder();
        sb.append("select count * from ").append(clazz.getSimpleName());
        Long count = (Long) createQuery(sb.toString()).getSingleResult();
        if (count != null && count.intValue() > 0) {
            Criteria c = createCriteria();
            List<T> list = c.setMaxResults(pageSize).setFirstResult((pageNo - 1) * pageSize).list();
            p = new Pager<T>(pageSize, pageNo, count.intValue(), list);
        }
        return p;
    }

    public Pager<T> selectPage(int pageNo, int pageSize, String order, boolean desc) {
        Pager<T> p = new Pager<T>();
        StringBuilder sb = new StringBuilder();
        sb.append("select count * from ").append(clazz.getSimpleName());
        Long count = (Long) createQuery(sb.toString()).getSingleResult();
        if (count != null && count.intValue() > 0) {
            Criteria c = createCriteria();
            if (desc) {
                c.addOrder(Order.desc(order));
            } else {
                c.addOrder(Order.asc(order));
            }
            List<T> list = c.setMaxResults(pageSize).setFirstResult((pageNo - 1) * pageSize).list();
            p = new Pager<T>(pageSize, pageNo, count.intValue(), list);
        }
        return p;
    }

    public Pager<T> selectPageByHql(int pageNo, int pageSize, String hql, Object... values) {
        Pager<T> p = new Pager<T>();
        StringBuilder sb = new StringBuilder(hql);
        sb.delete(0, sb.indexOf("from")).insert(0, "select count(*) ");
        Long count = (Long) createQuery(sb.toString(), values).getSingleResult();
        if (count != null && count.intValue() > 0) {
            List<T> list = createQuery(hql, values).setMaxResults(pageSize).setFirstResult((pageNo - 1) * pageSize)
                    .getResultList();
            p = new Pager<T>(pageSize, pageNo, count.intValue(), list);
        }
        return p;
    }

    public Pager<T> selectPageByHql(int pageNo, int pageSize, String hql, Map<String, ?> values) {
        Pager<T> p = new Pager<T>();
        StringBuilder sb = new StringBuilder(hql);
        sb.delete(0, sb.indexOf("from")).insert(0, "select count(*) ");
        Long count = (Long) createQuery(sb.toString(), values).getSingleResult();
        if (count != null && count.intValue() > 0) {
            List<T> list = createQuery(hql, values).setMaxResults(pageSize).setFirstResult((pageNo - 1) * pageSize)
                    .getResultList();
            p = new Pager<T>(pageSize, pageNo, count.intValue(), list);
        }
        return p;
    }

    public Pager<T> selectPageByProterty(int pageNo, int pageSize, String param, Object value) {
        Pager<T> p = new Pager<T>();
        int count = countByProperty(param, value);
        if (count > 0) {
            Criteria c = createCriteria();
            c.add(Restrictions.eq(param, value));
            List<T> list = c.setMaxResults(pageSize).setFirstResult((pageNo - 1) * pageSize).list();
            p = new Pager<T>(pageSize, pageNo, count, list);
        }
        return p;
    }

    public Pager<T> selectPageByProterties(int pageNo, int pageSize, Map<String, ?> values) {
        Pager<T> p = new Pager<T>();
        int count = countByProperties(values);
        if (count > 0) {
            Criteria c = createCriteria();
            for (String key : values.keySet()) {
                c.add(Restrictions.eq(key, values.get(key)));
            }
            List<T> list = c.setMaxResults(pageSize).setFirstResult((pageNo - 1) * pageSize).list();
            p = new Pager<T>(pageSize, pageNo, count, list);
        }
        return p;
    }

    public Pager<T> selectPageByProterty(int pageNo, int pageSize, String param, Object value, String order,
                                         boolean desc) {
        Pager<T> p = new Pager<T>();
        int count = countByProperty(param, value);
        if (count > 0) {
            Criteria c = createCriteria();
            c.add(Restrictions.eq(param, value));
            if (desc) {
                c.addOrder(Order.desc(order));
            } else {
                c.addOrder(Order.asc(order));
            }
            List<T> list = c.setMaxResults(pageSize).setFirstResult((pageNo - 1) * pageSize).list();
            p = new Pager<T>(pageSize, pageNo, count, list);
        }
        return p;
    }

    public Pager<T> selectPageByProterties(int pageNo, int pageSize, Map<String, ?> values, String order,
                                           boolean desc) {
        Pager<T> p = new Pager<T>();
        int count = countByProperties(values);
        if (count > 0) {
            Criteria c = createCriteria();
            for (String key : values.keySet()) {
                c.add(Restrictions.eq(key, values.get(key)));
            }
            if (desc) {
                c.addOrder(Order.desc(order));
            } else {
                c.addOrder(Order.asc(order));
            }
            List<T> list = c.setMaxResults(pageSize).setFirstResult((pageNo - 1) * pageSize).list();
            p = new Pager<T>(pageSize, pageNo, count, list);
        }
        return p;
    }

    public Pager<T> selectPageByCriteria(int pageNo, int pageSize, Criteria criteria) {
        Pager<T> p = new Pager<T>();
        int count = criteria.list().size();
        if (count > 0) {
            List<T> list = criteria.setMaxResults(pageSize).setFirstResult((pageNo - 1) * pageSize).list();
            p = new Pager<T>(pageSize, pageNo, count, list);
        }
        return p;
    }

    public long countResult() {
        Criteria c = createCriteria();
        c.setProjection(Projections.rowCount());
        return Integer.parseInt(c.uniqueResult().toString());
    }

    public int countByProperty(String param, Object value) {
        Criteria c = createCriteria();
        c.add(Restrictions.eq(param, value));
        c.setProjection(Projections.rowCount());
        return Integer.parseInt(c.uniqueResult().toString());
    }

    public int countByProperties(Map<String, ?> values) {
        Criteria c = createCriteria();
        for (String key : values.keySet()) {
            c.add(Restrictions.eq(key, values.get(key)));
        }
        c.setProjection(Projections.rowCount());
        return Integer.parseInt(c.uniqueResult().toString());
    }

    @SuppressWarnings("deprecation")
    protected Criteria createCriteria(Criterion... criterions) {
        Criteria criteria = this.getSession().createCriteria(clazz);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    protected Query createQuery(String hql, Map<String, ?> values) {
        Query query = this.getSession().createQuery(hql);
        if (values != null) {
            query.setProperties(values);
        }
        return query;
    }

    protected Query createQuery(String hql, Object... values) {
        Query query = getSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    @SuppressWarnings("deprecation")
    protected String getId() {
        return sessionFactory.getClassMetadata(clazz).getIdentifierPropertyName();
    }

}
