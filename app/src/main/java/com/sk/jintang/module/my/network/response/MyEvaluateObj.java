package com.sk.jintang.module.my.network.response;

import java.util.List;

/**
 * Created by administartor on 2017/9/22.
 */

public class MyEvaluateObj {

    /**
     * appraise_id : 15
     * photo : http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg
     * nickname : 哟嚯
     * goods_id : 5
     * goods_name : 【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品
     * goods_img : http://121.40.186.118:5008/upload/goods.png
     * price : 75
     * content :
     * is_after_review : 1
     * add_time : 2017/9/25
     * after_review : {"appraise_id":15,"content":"第二个","img_list":[{"img":"http://121.40.186.118:5008/upload/201709/25/170925135932624178.jpg"}]}
     */

    private int appraise_id;
    private String photo;
    private String nickname;
    private int goods_id;
    private String goods_name;
    private String goods_img;
    private double price;
    private String content;
    private int is_after_review;
    private String add_time;
    private List<ImgBean> image_list;
    private AfterReviewBean after_review;

    public List<ImgBean> getImage_list() {
        return image_list;
    }

    public void setImage_list(List<ImgBean> image_list) {
        this.image_list = image_list;
    }

    public int getAppraise_id() {
        return appraise_id;
    }

    public void setAppraise_id(int appraise_id) {
        this.appraise_id = appraise_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIs_after_review() {
        return is_after_review;
    }

    public void setIs_after_review(int is_after_review) {
        this.is_after_review = is_after_review;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public AfterReviewBean getAfter_review() {
        return after_review;
    }

    public void setAfter_review(AfterReviewBean after_review) {
        this.after_review = after_review;
    }

    public static class AfterReviewBean {
        /**
         * appraise_id : 15
         * content : 第二个
         * img_list : [{"img":"http://121.40.186.118:5008/upload/201709/25/170925135932624178.jpg"}]
         */

        private int appraise_id;
        private String content;
        private List<ImgListBean> img_list;

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

        public List<ImgListBean> getImg_list() {
            return img_list;
        }

        public void setImg_list(List<ImgListBean> img_list) {
            this.img_list = img_list;
        }

        public static class ImgListBean {
            /**
             * img : http://121.40.186.118:5008/upload/201709/25/170925135932624178.jpg
             */

            private String img;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }

    public class ImgBean {
        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
