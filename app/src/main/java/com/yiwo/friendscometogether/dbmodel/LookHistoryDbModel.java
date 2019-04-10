package com.yiwo.friendscometogether.dbmodel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ljc on 2019/4/10.
 */
@Entity
public class LookHistoryDbModel {
    @Id(autoincrement = true)
    private Long id;
    private String user_id;
    private String title;
    private String pic_url;
    private String look_time;
    private String type; // 0 活动 1 友记
    private String look_id;
    public String getLook_id() {
        return this.look_id;
    }
    public void setLook_id(String look_id) {
        this.look_id = look_id;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getLook_time() {
        return this.look_time;
    }
    public void setLook_time(String look_time) {
        this.look_time = look_time;
    }
    public String getPic_url() {
        return this.pic_url;
    }
    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_id() {
        return this.user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1193898698)
    public LookHistoryDbModel(Long id, String user_id, String title,
            String pic_url, String look_time, String type, String look_id) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.pic_url = pic_url;
        this.look_time = look_time;
        this.type = type;
        this.look_id = look_id;
    }
    @Generated(hash = 1190030004)
    public LookHistoryDbModel() {
    }
}
