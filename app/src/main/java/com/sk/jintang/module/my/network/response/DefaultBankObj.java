package com.sk.jintang.module.my.network.response;

/**
 * Created by administartor on 2017/9/12.
 */

public class DefaultBankObj {

    /**
     * id : 2
     * bank_name : 工商银行   6456
     * bank_image : http://121.40.186.118:5008/upload/66.png
     */

    private int id;
    private String bank_name;
    private String bank_image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_image() {
        return bank_image;
    }

    public void setBank_image(String bank_image) {
        this.bank_image = bank_image;
    }
}
