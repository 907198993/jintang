package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

/**
 * Created by administartor on 2017/9/21.
 */

public class YaoQingCodeObj extends BaseObj {
    /**
     * content : 将您的邀请码分享到社交媒体，被邀请用户注册时填写邀请码，可成为邀请会员的下级，查看自己的下级人员,当下级购买商品时，可获得设定的佣金。
     * distribution_yard : VL4V8R2X
     */

    private String content;
    private String distribution_yard;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDistribution_yard() {
        return distribution_yard;
    }

    public void setDistribution_yard(String distribution_yard) {
        this.distribution_yard = distribution_yard;
    }
}
