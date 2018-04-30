package com.sk.jintang.module.my.network.response;

/**
 * Created by administartor on 2017/9/11.
 */

public class VouchersObj  {

    /**
     * coupons_id : 1
     * title : 优惠券
     * face_value : 1
     * available : 20
     * deadline : 2017/8/25~2017/8/29
     */

    private int coupons_id;
    private String title;
    private double face_value;
    private double available;
    private String deadline;

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

    public double getFace_value() {
        return face_value;
    }

    public void setFace_value(double face_value) {
        this.face_value = face_value;
    }

    public double getAvailable() {
        return available;
    }

    public void setAvailable(double available) {
        this.available = available;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
