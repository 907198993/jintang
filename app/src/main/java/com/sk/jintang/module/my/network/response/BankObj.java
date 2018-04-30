package com.sk.jintang.module.my.network.response;

/**
 * Created by administartor on 2017/8/11.
 */

public class BankObj {

    /**
     * bank_id : 1
     * image_url : http://121.40.186.118:5009/upload/4.png
     * bank_name : 中国银行
     */

    private int bank_id;
    private String image_url;
    private String bank_name;

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }
}
