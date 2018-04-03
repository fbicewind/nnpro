package cn.nnnight.service.impl;

import cn.nnnight.common.Constants;
import cn.nnnight.dao.*;
import cn.nnnight.entity.*;
import cn.nnnight.security.AuthUtil;
import cn.nnnight.service.ArticleService;
import cn.nnnight.util.MyBeanUtils;
import cn.nnnight.util.Pager;
import cn.nnnight.vo.BlogAllVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    public static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleTypeDao articleTypeDao;
    @Autowired
    private ArticleVisitorDao articleVisitorDao;
    @Autowired
    private ArticleFavoriteDao articleFavoriteDao;
    @Autowired
    private ArticlePraiseDao articlePraiseDao;
    @Autowired
    private ArticleCommentDao articleCommentDao;

    @Override
    public BlogAllVo getArticleIndexInfo(int userId, int typeId, String type, int pageNo, int pageSize) {
        BlogAllVo vo = new BlogAllVo();
        Map<Integer, String> typeMap = new HashMap<>();
        vo.setUserId(userId);
        vo.setUser(userDao.get(userId));
        vo.setTypeId(typeId);
        Map<String, Object> values = new HashMap<>();
        values.put("delFlag", Constants.NO);
        values.put("userId", userId);
        List<ArticleType> types = getUserArticleTypes(userId);
        vo.setTypes(types);
        for (ArticleType articleType : types) {
            typeMap.put(articleType.getId(), articleType.getTypeName());
        }
        if (Constants.DRAFTDIARY.equals(type)) {
            values.put("draftFlag", Constants.YES);
        } else if (Constants.PRIVATEDIARY.equals(type)) {
            values.put("publicFlag", Constants.NO);
            values.put("draftFlag", Constants.NO);
        } else {
            values.put("draftFlag", Constants.NO);
            values.put("publicFlag", Constants.YES);
        }
        if (typeId != 0) {
            values.put("typeId", typeId);
        }
        Pager<Article> articles = null;
        if (Constants.FAVORABLEDIARY.equals(type)) {
            articles = articleDao.selectFavorableArticles(pageNo, pageSize, values);
        } else {
            articles = articleDao.selectArticles(pageNo, pageSize, values);
        }
        if (articles.getDatalist() != null) {
            for (Article article : articles.getDatalist()) {
                article.setArticleType(typeMap.get(article.getTypeId()));
            }
        }
        vo.setArticles(articles);
        setNewAndViewArticle(vo, userId);
        return vo;
    }

    @Override
    public BlogAllVo getArticleMarkInfo(int userId, int articleId) {
        BlogAllVo vo = new BlogAllVo();
        vo.setUserId(userId);
        vo.setUser(userDao.get(userId));
        List<ArticleType> types = getUserArticleTypes(userId);
        vo.setTypes(types);
        if (articleId != 0) {
            vo.setArticle(articleDao.get(articleId));
        }
        setNewAndViewArticle(vo, userId);
        return vo;
    }

    @Override
    public BlogAllVo getArticleDetailInfo(int articleId, String isType, String diaryType) {
        BlogAllVo vo = new BlogAllVo();
        Article article = articleDao.get(articleId);
        int userId = article.getUserId();
        List<ArticleType> types = getUserArticleTypes(userId);
        vo.setTypes(types);
        Map<Integer, String> typeMap = new HashMap<>();
        for (ArticleType articleType : types) {
            typeMap.put(articleType.getId(), articleType.getTypeName());
        }
        Map<String, Object> values = new HashMap<>();
        int currentId = AuthUtil.getUserId();
        values.put("userId", currentId);
        values.put("articleId", articleId);
        values.put("delFlag", Constants.NO);
        List<ArticleFavorite> favoriteList = articleFavoriteDao.findListByProperties(values);
        List<ArticlePraise> praiseList = articlePraiseDao.findListByProperties(values);
        article.setFavoriteFlag((favoriteList == null || favoriteList.isEmpty()) ? Constants.NO : Constants.YES);
        article.setPraiseFlag((praiseList == null || praiseList.isEmpty()) ? Constants.NO : Constants.YES);
        article.setArticleType(typeMap.get(article.getTypeId()));
        vo.setArticle(article);
        vo.setUserId(userId);
        vo.setUser(userDao.get(userId));
        vo.setIsType(isType);
        vo.setDiaryType(diaryType);
        setNewAndViewArticle(vo, userId);
        setPrevAndNextArticle(vo, article);
        return vo;
    }

    @Override
    public void addViewRecord(int articleId, int userId, String ipAddr) {
        Map<String, Object> values = new HashMap<>();
        values.put("articleId", articleId);
        values.put("userId", userId);
        ArticleVisitor articleVisitor = articleVisitorDao.getRecord(articleId, userId, ipAddr);
        if (articleVisitor != null) {
            articleVisitor.setCreateTime(new Date());
            articleVisitor.setDelFlag(Constants.NO);
            articleVisitor.setIpAddress(ipAddr);
        } else {
            articleVisitor = new ArticleVisitor();
            articleVisitor.setCreateTime(new Date());
            articleVisitor.setArticleId(articleId);
            articleVisitor.setUserId(userId);
            articleVisitor.setIpAddress(ipAddr);
            Article article = articleDao.get(articleId);
            article.setReadCount(article.getReadCount() + 1);
            articleDao.update(article);
        }
        articleVisitorDao.saveOrUpdate(articleVisitor);
    }

    @Override
    public Article saveArticle(Article vo) {
        Article article = null;
        Date now = new Date();
        if (vo.getId() == 0) {
            article = new Article();
            MyBeanUtils.copyProperties(vo, article);
            article.setCreateTime(now);
            article.setUpdateTime(now);
        } else {
            article = articleDao.get(vo.getId());
            MyBeanUtils.copyProperties(vo, article);
            article.setUpdateTime(now);
        }
        article.setUserId(AuthUtil.getUserId());
        article.setDelFlag(Constants.NO);
        articleDao.saveOrUpdate(article);
        int userId = AuthUtil.getUserId();
        updateArticleCount(userId);
        return article;
    }

    @Override
    public boolean isTypeExist(String typeName) {
        Map<String, Object> values = new HashMap<>();
        values.put("typeName", typeName);
        values.put("userId", AuthUtil.getUserId());
        values.put("delFlag", Constants.NO);
        List<ArticleType> list = articleTypeDao.findListByProperties(values);
        return list != null && !list.isEmpty();
    }

    @Override
    public ArticleType saveArticleType(String typeName) {
        ArticleType articleType = new ArticleType();
        articleType.setCreateTime(new Date());
        articleType.setTypeName(typeName);
        articleType.setUserId(AuthUtil.getUserId());
        articleType.setDelFlag(Constants.NO);
        articleTypeDao.save(articleType);
        return articleType;
    }

    @Override
    public boolean saveComment(ArticleComment articleComment) {
        boolean flag = false;
        try {
            ArticleComment comment = new ArticleComment();
            BeanUtils.copyProperties(articleComment, comment);
            comment.setCreateTime(new Date());
            comment.setDelFlag(Constants.NO);
            articleCommentDao.save(comment);
            Article article = articleDao.get(articleComment.getArticleId());
            article.setCommentCount(article.getCommentCount() + 1);
            articleDao.update(article);
            flag = true;
        } catch (BeansException e) {
            logger.error("Save comment error: ", e);
        }
        return flag;
    }

    @Override
    public boolean praiseArticle(int articleId, int state) {
        boolean flag = false;
        try {
            Date now = new Date();
            int userId = AuthUtil.getUserId();
            Map<String, Object> values = new HashMap<>();
            values.put("articleId", articleId);
            values.put("userId", userId);
            List<ArticlePraise> praiseList = articlePraiseDao.findListByProperties(values);
            ArticlePraise articlePraise = (praiseList == null || praiseList.isEmpty()) ? new ArticlePraise() : praiseList.get(0);
            articlePraise.setArticleId(articleId);
            articlePraise.setUserId(userId);
            Article article = articleDao.get(articleId);
            if (state == 1) {
                articlePraise.setDelFlag(Constants.NO);
                articlePraise.setCreateTime(now);
                article.setPraiseCount(article.getPraiseCount() + 1);
            } else {
                articlePraise.setDelFlag(Constants.YES);
                articlePraise.setDeleteId(userId);
                articlePraise.setDeleteTime(now);
                article.setPraiseCount(article.getPraiseCount() - 1);
            }
            articlePraiseDao.saveOrUpdate(articlePraise);
            articleDao.update(article);
            flag = true;
        } catch (Exception e) {
            logger.error("Praise error: ", e);
        }
        return flag;
    }

    @Override
    public boolean favoriteArticle(int articleId, int state) {
        boolean flag = false;
        try {
            Date now = new Date();
            int userId = AuthUtil.getUserId();
            Map<String, Object> values = new HashMap<>();
            values.put("articleId", articleId);
            values.put("userId", userId);
            List<ArticleFavorite> favoriteList = articleFavoriteDao.findListByProperties(values);
            ArticleFavorite articleFavorite = (favoriteList == null || favoriteList.isEmpty()) ? new ArticleFavorite() : favoriteList.get(0);
            articleFavorite.setArticleId(articleId);
            articleFavorite.setUserId(userId);
            Article article = articleDao.get(articleId);
            if (state == 1) {
                articleFavorite.setDelFlag(Constants.NO);
                articleFavorite.setCreateTime(now);
                article.setFavoriteCount(article.getFavoriteCount() + 1);
            } else {
                articleFavorite.setDelFlag(Constants.YES);
                articleFavorite.setDeleteId(userId);
                articleFavorite.setDeleteTime(now);
                article.setFavoriteCount(article.getFavoriteCount() - 1);
            }
            articleFavoriteDao.saveOrUpdate(articleFavorite);
            articleDao.update(article);
            flag = true;
        } catch (Exception e) {
            logger.error("Favorite error: ", e);
        }
        return flag;
    }

    @Override
    public boolean delArticle(int articleId) {
        boolean flag = false;
        try {
            Article article = articleDao.get(articleId);
            int userId = AuthUtil.getUserId();
            if (article.getUserId() == userId) {
                article.setDelFlag(Constants.YES);
                article.setDeleteId(userId);
                article.setDeleteTime(new Date());
                articleDao.update(article);
                updateArticleCount(userId);
                flag = true;
            }
        } catch (Exception e) {
            logger.error("Delete article error: ", e);
        }
        return flag;
    }

    @Override
    public BlogAllVo getIndexInfo(int userId) {
        BlogAllVo vo = new BlogAllVo();
        vo.setUser(userDao.get(userId));
        List<ArticleType> types = getUserArticleTypes(userId);
        Map<Integer, String> typeMap = new HashMap<>();
        for (ArticleType articleType : types) {
            typeMap.put(articleType.getId(), articleType.getTypeName());
        }
        Map<String, Object> values = new HashMap<>();
        values.put("recommendFlag", Constants.YES);
        values.put("draftFlag", Constants.NO);
        values.put("delFlag", Constants.NO);
        values.put("publicFlag", Constants.YES);
        values.put("userId", userId);
        Pager<Article> articles = articleDao.selectPageByProterties(1, 20, values, "updateTime", true);
        if (articles.getDatalist() != null) {
            for (Article article : articles.getDatalist()) {
                article.setArticleType(typeMap.get(article.getTypeId()));
            }
        }
        vo.setArticles(articles);
        values.clear();
        values.put("delFlag", Constants.NO);
        values.put("draftFlag", Constants.NO);
        values.put("userId", userId);
        values.put("publicFlag", Constants.YES);
        vo.setNewArticles(articleDao.selectPageByProterties(1, 10, values, "createTime", true));
        return vo;
    }

    private void setNewAndViewArticle(BlogAllVo vo, int userId) {
        Map<String, Object> values = new HashMap<>();
        values.put("delFlag", Constants.NO);
        values.put("draftFlag", Constants.NO);
        values.put("userId", userId);
        values.put("publicFlag", Constants.YES);
        vo.setNewArticles(articleDao.selectPageByProterties(1, 10, values, "createTime", true));
        vo.setViewArticles(articleDao.selectPageByProterties(1, 10, values, "readCount", true));
    }

    private void setPrevAndNextArticle(BlogAllVo vo, Article article) {
        Article prev = null;
        Article next = null;
        Map<String, Object> values = new HashMap<>();
        values.put("draftFlag", Constants.NO);
        values.put("publicFlag", article.getPublicFlag());
        values.put("delFlag", Constants.NO);
        values.put("userId", article.getUserId());
        values.put("topFlag", Constants.NO);
        values.put("updateTime", article.getUpdateTime());
        if (!Constants.STRING_ZERO.equals(vo.getIsType())) {
            values.put("typeId", article.getTypeId());
        }
        if (Constants.NO.equals(article.getTopFlag())) {// 不置顶：上一篇可能会置顶
            prev = articleDao.getPrev(values);
            next = articleDao.getNext(values);
            if (prev == null) {
                values.put("topFlag", Constants.YES);
                prev = articleDao.getTopPrev(values);
            }
        } else {// 置顶：上一篇肯定置顶
            values.put("topFlag", Constants.YES);
            prev = articleDao.getPrev(values);
            next = articleDao.getNext(values);
            if (next == null) {
                values.put("topFlag", Constants.NO);
                next = articleDao.getNoTopNext(values);
            }
        }
        vo.setPrev(prev);
        vo.setNext(next);
    }

    private List<ArticleType> getUserArticleTypes(int userId) {
        Map<String, Object> values = new HashMap<>();
        values.put("delFlag", Constants.NO);
        values.put("userId", userId);
        List<ArticleType> types = articleTypeDao.findListByProperties(values, "createTime", false);
        ArticleType type = articleTypeDao.get(1);
        types.add(0, type);
        return types;
    }

    private void updateArticleCount(int userId) {
        Map<String, Object> values = new HashMap<>();
        values.put("delFlag", Constants.NO);
        values.put("draftFlag", Constants.NO);
        values.put("publicFlag", Constants.YES);
        values.put("userId", userId);
        User user = userDao.get(userId);
        user.setArticleCount(articleDao.countByProperties(values));
        userDao.update(user);
    }

}
