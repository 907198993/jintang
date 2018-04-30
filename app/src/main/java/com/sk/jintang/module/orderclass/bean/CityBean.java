package com.sk.jintang.module.orderclass.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class CityBean {
    private int id;
    private String title;
    private String parent_id;
    private List<CityBean> class_list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public List<CityBean> getClass_list() {
        return class_list;
    }

    public void setClass_list(List<CityBean> class_list) {
        this.class_list = class_list;
    }
}
