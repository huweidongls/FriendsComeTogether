package com.yiwo.friendscometogether.dbmodel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ljc on 2019/9/18.
 */
@Entity
public class DuiZhangDeHuoDongDbModel {
    @Id(autoincrement = true)
    private Long id;
    //pfpic 活动图片
    public String duiZhangID;
    public String pfpic;
    public String pfID;
    public String pftitle;
    public String phase_num;
    public String phase_begin_time;
    public String phase_id;

    public String getPftitle() {
        return this.pftitle;
    }
    public void setPftitle(String pftitle) {
        this.pftitle = pftitle;
    }
    public String getPfID() {
        return this.pfID;
    }
    public void setPfID(String pfID) {
        this.pfID = pfID;
    }
    public String getPfpic() {
        return this.pfpic;
    }
    public void setPfpic(String pfpic) {
        this.pfpic = pfpic;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDuiZhangID() {
        return this.duiZhangID;
    }
    public void setDuiZhangID(String duiZhangID) {
        this.duiZhangID = duiZhangID;
    }
    public String getPhase_id() {
        return this.phase_id;
    }
    public void setPhase_id(String phase_id) {
        this.phase_id = phase_id;
    }
    public String getPhase_begin_time() {
        return this.phase_begin_time;
    }
    public void setPhase_begin_time(String phase_begin_time) {
        this.phase_begin_time = phase_begin_time;
    }
    public String getPhase_num() {
        return this.phase_num;
    }
    public void setPhase_num(String phase_num) {
        this.phase_num = phase_num;
    }
    @Generated(hash = 655901446)
    public DuiZhangDeHuoDongDbModel(Long id, String duiZhangID, String pfpic,
            String pfID, String pftitle, String phase_num, String phase_begin_time,
            String phase_id) {
        this.id = id;
        this.duiZhangID = duiZhangID;
        this.pfpic = pfpic;
        this.pfID = pfID;
        this.pftitle = pftitle;
        this.phase_num = phase_num;
        this.phase_begin_time = phase_begin_time;
        this.phase_id = phase_id;
    }
    @Generated(hash = 131152728)
    public DuiZhangDeHuoDongDbModel() {
    }
}
