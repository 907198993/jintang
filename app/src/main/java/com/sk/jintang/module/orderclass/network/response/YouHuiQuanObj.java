package com.sk.jintang.module.orderclass.network.response;

/**
 * Created by administartor on 2017/9/19.
 */

public class YouHuiQuanObj {
    /**
     * coupons_id : 1
     * title : 优惠券
     * available : 20
     * face_value : 1
     * begin_time : 2017/8/25
     * end_time : 2017/8/29
     */

    private int coupons_id;
    private String title;
    private int available;
    private int face_value;
    private String begin_time;
    private String end_time;

    public int getCoupons_id() {
        return coupons_id;
    }

    public void setCoupons_id(int coupons_id) {
        this.coupons_id = coupons_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getFace_value() {
        return face_value;
    }

    public void setFace_value(int face_value) {
        this.face_value = face_value;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
