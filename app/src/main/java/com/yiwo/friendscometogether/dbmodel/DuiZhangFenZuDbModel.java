package com.yiwo.friendscometogether.dbmodel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ljc on 2019/9/23.
 */
@Entity
public class DuiZhangFenZuDbModel {
    @Id(autoincrement = true)
    private Long id;
    private String pfID;
    private String phase_id;
    
    private String group_No;
    private String gameNum;
    private String userID;
    private String user_ID;
    private String username;
    private String userpic;

    public String getUserpic() {
        return this.userpic;
    }
    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUser_ID() {
        return this.user_ID;
    }
    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }
    public String getUserID() {
        return this.userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getGameNum() {
        return this.gameNum;
    }
    public void setGameNum(String gameNum) {
        this.gameNum = gameNum;
    }
    public String getGroup_No() {
        return this.group_No;
    }
    public void setGroup_No(String group_No) {
        this.group_No = group_No;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPhase_id() {
        return this.phase_id;
    }
    public void setPhase_id(String phase_id) {
        this.phase_id = phase_id;
    }
    public String getPfID() {
        return this.pfID;
    }
    public void setPfID(String pfID) {
        this.pfID = pfID;
    }
    @Generated(hash = 1578733698)
    public DuiZhangFenZuDbModel(Long id, String pfID, String phase_id,
            String group_No, String gameNum, String userID, String user_ID,
            String username, String userpic) {
        this.id = id;
        this.pfID = pfID;
        this.phase_id = phase_id;
        this.group_No = group_No;
        this.gameNum = gameNum;
        this.userID = userID;
        this.user_ID = user_ID;
        this.username = username;
        this.userpic = userpic;
    }
    @Generated(hash = 168087428)
    public DuiZhangFenZuDbModel() {
    }
}
