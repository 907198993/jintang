package com.sk.jintang.module.my.network.request;

import java.util.List;

/**
 * Created by administartor on 2017/9/25.
 */

public class AgainEvaluateItem {
    private List<BodyBean> body;

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * image_url : sample string 1
         */

        private String image_url;

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}
