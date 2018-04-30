package com.sk.jintang.module.community.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/9/15.
 */

public class SheQuHuaTiObj extends BaseObj {
    /**
     * img_url : http://121.40.186.118:5008/upload/123.jpg
     * topic_list : [{"topic_id":5,"topic_name":"#按摩棒你要这样挑#"},{"topic_id":1,"topic_name":"#美妆直播季节#"},{"topic_id":4,"topic_name":"#夏日美人心计#"}]
     */

    private String img_url;
    private List<TopicListBean> topic_list;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public List<TopicListBean> getTopic_list() {
        return topic_list;
    }

    public void setTopic_list(List<TopicListBean> topic_list) {
        this.topic_list = topic_list;
    }

    public static class TopicListBean {
        /**
         * topic_id : 5
         * topic_name : #按摩棒你要这样挑#
         */

        private int topic_id;
        private String topic_name;

        public int getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(int topic_id) {
            this.topic_id = topic_id;
        }

        public String getTopic_name() {
            return topic_name;
        }

        public void setTopic_name(String topic_name) {
            this.topic_name = topic_name;
        }
    }
}
