package cn.nnnight.dao;

import cn.nnnight.util.Pager;
import org.hibernate.Criteria;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao<T> {

    public T get(Serializable id);

    public T load(Serializable id);

    // 添加实体
    public void save(T entity);

    // 更新实体
    public void update(T entity);

    // 保存或更新实体
    public void saveOrUpdate(T entity);

    // 删除实体
    public void delete(T entity);

    // 根据id删除实体
    public void delete(Serializable id);

    // 根据多个id删除实体
    public void delete(Serializable... ids);

    // 根据某参数查找唯一结果
    public T getUniqueResult(String param, Object value);

    // 根据多个参数查找唯一结果
    public T getUniqueResult(Map<String, ?> values);

    // hql查找唯一结果
    public T getUniqueResultByHql(String hql, Object... values);

    // hql查找唯一结果
    public T getUniqueResultByHql(String hql, Map<String, ?> values);

    // 获取list
    public List<T> findAll();

    // 获取排序后的list
    public List<T> findAll(String order, boolean desc);

    // 根据某一参数查找list
    public List<T> findListByProperty(String param, Object value);

    // 根据多个参数查找list
    public List<T> findListByProperties(Map<String, ?> values);

    // 根据某一参数查找list，带排序
    public List<T> findListByProperty(String param, Object value, String order, boolean desc);

    // 根据多个参数查找list，带排序
    public List<T> findListByProperties(Map<String, ?> values, String order, boolean desc);

    // hql查找list
    public List<T> findListByHql(String hql, Object... values);

    // hql查找list
    public List<T> findListByHql(String hql, Map<String, ?> values);

    // hql查找规定数量的list
    public List<T> findListByHqlLimitCount(String hql, Map<String, ?> values, int count);

    // 简单分页查找
    public Pager<T> selectPage(int pageNo, int pageSize);

    // 待排序的简单分页查找
    public Pager<T> selectPage(int pageNo, int pageSize, String order, boolean desc);

    // 根据hql分页查找，参数为Object...
    public Pager<T> selectPageByHql(int pageNo, int pageSize, String hql, Object... values);

    // 根据hql分页查找，参数为Map
    public Pager<T> selectPageByHql(int pageNo, int pageSize, String hql, Map<String, ?> values);

    // 根据某个参数分页查找
    public Pager<T> selectPageByProterty(int pageNo, int pageSize, String param, Object value);

    // 根据参数Map分页查找
    public Pager<T> selectPageByProterties(int pageNo, int pageSize, Map<String, ?> values);

    // 根据某个参数分页查找，并排序
    public Pager<T> selectPageByProterty(int pageNo, int pageSize, String param, Object value, String order, boolean desc);

    // 根据参数Map分页查找，并排序
    public Pager<T> selectPageByProterties(int pageNo, int pageSize, Map<String, ?> values, String order, boolean desc);

    // 根据Criteria分页查询
    public Pager<T> selectPageByCriteria(int pageNo, int pageSize, Criteria criteria);

    // 计数
    public long countResult();

    // 根据某个参数计数
    public int countByProperty(String param, Object value);

    // 根据多个参数计数
    public int countByProperties(Map<String, ?> values);
}
