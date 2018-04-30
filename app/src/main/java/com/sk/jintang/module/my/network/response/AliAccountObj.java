package com.sk.jintang.module.my.network.response;

/**
 * Created by Administrator on 2017/11/18.
 */

public class AliAccountObj {
    /**
     * userid : 4
     * alipay_id : 1
     * alipay_number : 555***5555
     * is_default : 0
     */

    private int userid;
    private int alipay_id;
    private String alipay_number;
    private int is_default;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getAlipay_id() {
        return alipay_id;
    }

    public void setAlipay_id(int alipay_id) {
        this.alipay_id = alipay_id;
    }

    public String getAlipay_number() {
        return alipay_number;
    }

    public void setAlipay_number(String alipay_number) {
        this.alipay_number = alipay_number;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }
}
