package com.yiwo.friendscometogether.create_friendremember;

/**
 * Created by ljc on 2019/4/29.
 */

public class PicBean {
    public PicBean(String path,int size,String displayName){
        this.displayName =displayName;
        this.path =path;
        this.size =size;
    }
    private String path;
    private int size;
    private String displayName;
    private Boolean isChoose = false;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getChoose() {
        return isChoose;
    }

    public void setChoose(Boolean choose) {
        isChoose = choose;
    }
}
