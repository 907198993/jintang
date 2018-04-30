package com.sk.jintang.module.community.network.request;

/**
 * Created by administartor on 2017/10/13.
 */

public class FaBuTieZiItem {
    /**
     * body : {"user_id":1,"title":"sample string 2","content":"sample string 3","img_url":"sample string 4"}
     */

    private BodyBean body;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * user_id : 1
         * title : sample string 2
         * content : sample string 3
         * img_url : sample string 4
         */

        private String user_id;
        private String title;
        private String content;
        private String img_url;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }
}
