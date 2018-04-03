package cn.nnnight.service;

import cn.nnnight.entity.Article;
import cn.nnnight.entity.ArticleComment;
import cn.nnnight.entity.ArticleType;
import cn.nnnight.vo.BlogAllVo;

public interface ArticleService {

    public BlogAllVo getArticleIndexInfo(int userId, int typeId, String type, int pageNo, int pageSize);

    public BlogAllVo getArticleMarkInfo(int userId, int articleId);

    public BlogAllVo getArticleDetailInfo(int articleId, String isType, String diaryType);

    public void addViewRecord(int articleId, int userId, String ipAddr);

    public Article saveArticle(Article vo);

    public boolean isTypeExist(String typeName);

    public ArticleType saveArticleType(String typeName);

    public boolean saveComment(ArticleComment articleComment);

    public boolean praiseArticle(int articleId, int state);

    public boolean favoriteArticle(int articleId, int state);

    public boolean delArticle(int articleId);

    public BlogAllVo getIndexInfo(int userId);
}
