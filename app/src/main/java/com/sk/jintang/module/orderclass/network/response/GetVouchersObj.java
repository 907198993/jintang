package com.sk.jintang.module.orderclass.network.response;

/**
 * Created by administartor on 2017/9/27.
 */

public class GetVouchersObj {
    /**
     * id : 5
     * title : 全场商品可用
     * face_value : 15
     * available : 50
     * deadline : 2017/8/20~2017/9/20
     * status : 1
     */

    private int id;
    private String title;
    private double face_value;
    private double available;
    private String deadline;
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
