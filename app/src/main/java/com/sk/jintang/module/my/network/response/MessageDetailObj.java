package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

/**
 * Created by administartor on 2017/9/11.
 */

public class MessageDetailObj extends BaseObj {

    /**
     * id : 1
     * title : 注册成功!
     * content : 恭喜，您已成功注册养御商城会员，系统自动赠送您价值200元的优惠券，快去看看吧～
     */

    private int id;
    private String title;
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
