package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/9/25.
 */

public class EvaluateDetailObj extends BaseObj {

    /**
     * appraise_id : 15
     * goods_img : http://121.40.186.118:5008/upload/goods.png
     * number_stars : 4
     * type : 好
     * content :
     * img_list : []
     * after_review : {"appraise_id":15,"content":"第二个","img_list":[{"img":"http://121.40.186.118:5008/upload/201709/25/170925135932624178.jpg"}]}
     */

    private int appraise_id;
    private String goods_img;
    private int number_stars;
    private String type;
    private String content;
    private AfterReviewBean after_review;
    private List<?> img_list;

    public int getAppraise_id() {
        return appraise_id;
    }

    public void setAppraise_id(int appraise_id) {
        this.appraise_id = appraise_id;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public int getNumber_stars() {
        return number_stars;
    }

    public void setNumber_stars(int number_stars) {
        this.number_stars = number_stars;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AfterReviewBean getAfter_review() {
        return after_review;
    }

    public void setAfter_review(AfterReviewBean after_review) {
        this.after_review = after_review;
    }

    public List<?> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<?> img_list) {
        this.img_list = img_list;
    }

    public static class AfterReviewBean {
        /**
         * appraise_id : 15
         * content : 第二个
         * img_list : [{"img":"http://121.40.186.118:5008/upload/201709/25/170925135932624178.jpg"}]
         */

        private int appraise_id;
        private String content;
        private List<EvaluateDetailObj> img_list;

        public int getAppraise_id() {
            return appraise_id;
        }

        public void setAppraise_id(int appraise_id) {
            this.appraise_id = appraise_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<EvaluateDetailObj> getImg_list() {
            return img_list;
        }

        public void setImg_list(List<EvaluateDetailObj> img_list) {
            this.img_list = img_list;
        }
    }
}
