package com.yiwo.friendscometogether.dbmodel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ljc on 2019/9/26.
 */
@Entity
public class MyGameCardDbModel {
    @Id(autoincrement = true)
    private Long id;
    //huodong
    private String userId;
    private String title;
    private String group_No;
    private String myNo;
    //duizhang captainBean
    private String captain_user_ID;
    private String captain_userpic;
    private String captain_username;
    private String captain_group_No;
    //GroupInfoBean
    private String group_user_ID;
    private String group_game_num;
    private String group_userpic;
    private String group_username;
    public String getGroup_username() {
        return this.group_username;
    }
    public void setGroup_username(String group_username) {
        this.group_username = group_username;
    }
    public String getGroup_userpic() {
        return this.group_userpic;
    }
    public void setGroup_userpic(String group_userpic) {
        this.group_userpic = group_userpic;
    }
    public String getGroup_game_num() {
        return this.group_game_num;
    }
    public void setGroup_game_num(String group_game_num) {
        this.group_game_num = group_game_num;
    }
    public String getGroup_user_ID() {
        return this.group_user_ID;
    }
    public void setGroup_user_ID(String group_user_ID) {
        this.group_user_ID = group_user_ID;
    }
    public String getCaptain_group_No() {
        return this.captain_group_No;
    }
    public void setCaptain_group_No(String captain_group_No) {
        this.captain_group_No = captain_group_No;
    }
    public String getCaptain_username() {
        return this.captain_username;
    }
    public void setCaptain_username(String captain_username) {
        this.captain_username = captain_username;
    }
    public String getCaptain_userpic() {
        return this.captain_userpic;
    }
    public void setCaptain_userpic(String captain_userpic) {
        this.captain_userpic = captain_userpic;
    }
    public String getCaptain_user_ID() {
        return this.captain_user_ID;
    }
    public void setCaptain_user_ID(String captain_user_ID) {
        this.captain_user_ID = captain_user_ID;
    }
    public String getGroup_No() {
        return this.group_No;
    }
    public void setGroup_No(String group_No) {
        this.group_No = group_No;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getMyNo() {
        return this.myNo;
    }
    public void setMyNo(String myNo) {
        this.myNo = myNo;
    }
    @Generated(hash = 556133279)
    public MyGameCardDbModel(Long id, String userId, String title, String group_No,
            String myNo, String captain_user_ID, String captain_userpic,
            String captain_username, String captain_group_No, String group_user_ID,
            String group_game_num, String group_userpic, String group_username) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.group_No = group_No;
        this.myNo = myNo;
        this.captain_user_ID = captain_user_ID;
        this.captain_userpic = captain_userpic;
        this.captain_username = captain_username;
        this.captain_group_No = captain_group_No;
        this.group_user_ID = group_user_ID;
        this.group_game_num = group_game_num;
        this.group_userpic = group_userpic;
        this.group_username = group_username;
    }
    @Generated(hash = 1099059775)
    public MyGameCardDbModel() {
    }
}
