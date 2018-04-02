package cn.nnnight.vo;

import cn.nnnight.entity.Article;
import cn.nnnight.entity.ArticleType;
import cn.nnnight.entity.User;
import cn.nnnight.util.Pager;

import java.util.List;

public class BlogAllVo {

    private int userId;
    private int typeId;
    private User user;
    private Article article;
    private Pager<Article> articles;
    private List<ArticleType> types;
    private Pager<Article> viewArticles;
    private Pager<Article> newArticles;
    private Article prev;
    private Article next;
    private String isType;
    private String diaryType;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Pager<Article> getArticles() {
        return articles;
    }

    public void setArticles(Pager<Article> articles) {
        this.articles = articles;
    }

    public List<ArticleType> getTypes() {
        return types;
    }

    public void setTypes(List<ArticleType> types) {
        this.types = types;
    }

    public Pager<Article> getViewArticles() {
        return viewArticles;
    }

    public void setViewArticles(Pager<Article> viewArticles) {
        this.viewArticles = viewArticles;
    }

    public Pager<Article> getNewArticles() {
        return newArticles;
    }

    public void setNewArticles(Pager<Article> newArticles) {
        this.newArticles = newArticles;
    }

    public Article getPrev() {
        return prev;
    }

    public void setPrev(Article prev) {
        this.prev = prev;
    }

    public Article getNext() {
        return next;
    }

    public void setNext(Article next) {
        this.next = next;
    }

    public String getIsType() {
        return isType;
    }

    public void setIsType(String isType) {
        this.isType = isType;
    }

    public String getDiaryType() {
        return diaryType;
    }

    public void setDiaryType(String diaryType) {
        this.diaryType = diaryType;
    }
}
