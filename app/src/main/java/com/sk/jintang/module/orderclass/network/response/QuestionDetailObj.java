package com.sk.jintang.module.orderclass.network.response;

/**
 * Created by Administrator on 2018/2/6.
 */

public class QuestionDetailObj {
    /**
     * question_id : 1
     * answer_id : 9
     * answer_content : 我在上海大学美术??
     * add_time : 1517901467
     */

    private int question_id;
    private int answer_id;
    private String answer_content;
    private long add_time;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    public String getAnswer_content() {
        return answer_content;
    }

    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }
}
