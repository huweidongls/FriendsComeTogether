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
    private Boolean isSelected = false;
    private int choose_num = -1;
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

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public int getChoose_num() {
        return choose_num;
    }

    public void setChoose_num(int choose_num) {
        this.choose_num = choose_num;
    }
}
