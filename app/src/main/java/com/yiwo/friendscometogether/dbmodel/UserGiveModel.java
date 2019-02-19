package com.yiwo.friendscometogether.dbmodel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ljc on 2019/2/19.
 */
@Entity
public class UserGiveModel {
    @Id(autoincrement = true)
    private Long id;
    /** 用户id */
    public String userId;
    /** 文章id */
    public String  articleId;
    /** 点赞状态 */
    public String  remarkState;
    /** 文章类型 */
    public String  articleType;
    public String getArticleType() {
        return this.articleType;
    }
    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }
    public String getRemarkState() {
        return this.remarkState;
    }
    public void setRemarkState(String remarkState) {
        this.remarkState = remarkState;
    }
    public String getArticleId() {
        return this.articleId;
    }
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1274510639)
    public UserGiveModel(Long id, String userId, String articleId,
            String remarkState, String articleType) {
        this.id = id;
        this.userId = userId;
        this.articleId = articleId;
        this.remarkState = remarkState;
        this.articleType = articleType;
    }
    @Generated(hash = 370269943)
    public UserGiveModel() {
    }

}
