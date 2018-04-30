package com.sk.jintang.module.orderclass.network.response;

/**
 * Created by administartor on 2017/8/11.
 */

public class CityObj {

    /**
     * id : 1804
     * parent_id : 1770
     * title : 北京市
     * sort_id : 99
     */
    private boolean isSelect;
    private int id;
    private int parent_id;
    private String title;
    private int sort_id;
    private String city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSort_id() {
        return sort_id;
    }

    public void setSort_id(int sort_id) {
        this.sort_id = sort_id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
