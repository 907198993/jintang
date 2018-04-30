package com.sk.jintang.module.orderclass.network.response;

/**
 * Created by Administrator on 2018/2/6.
 */

public class GoodsQuestionObj  {
    /**
     * question_id : 5
     * goods_id : 36
     * question_content : 测试问题
     * add_time : 1517897615
     */

    private int question_id;
    private int goods_id;
    private String question_content;
    private long add_time;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }
}
