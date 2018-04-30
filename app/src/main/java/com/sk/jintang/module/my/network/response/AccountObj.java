package com.sk.jintang.module.my.network.response;

import java.io.Serializable;

/**
 * Created by administartor on 2017/8/14.
 */

public class AccountObj implements Serializable {

    /**
     * userid : 1
     * id : 1
     * bank_image : http://121.40.186.118:5009/upload/1.png
     * bank_name : 上海银行
     * bank_card : 尾号1785
     * is_default : 0
     */

    private int userid;
    private int id;
    private String bank_image;
    private String bank_name;
    private String bank_card;
    private int is_default;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBank_image() {
        return bank_image;
    }

    public void setBank_image(String bank_image) {
        this.bank_image = bank_image;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }
}
