package com.sk.jintang.module.community.network.response;

/**
 * Created by administartor on 2017/9/18.
 */

public class MoreHuaTiObj {
    /**
     * topic_id : 5
     * topic_name : #按摩棒你要这样挑#
     */

    private int topic_id;
    private String topic_name;
    private String add_time;

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }
}
