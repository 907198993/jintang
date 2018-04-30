package com.sk.jintang.module.orderclass.network.response;

import java.util.List;

/**
 * Created by administartor on 2017/9/14.
 */

public class GoodsEvaluateObj  {
    /**
     * photo : http://121.40.186.118:5008/upload/201709/12/17091211231873585059.jpg
     * nickname : 哟嚯
     * specifications : 	黄色*100ml
     * img_list : [{"img":"http://121.40.186.118:5008/upload/a.jpg"},{"img":"http://121.40.186.118:5008/upload/a.jpg"}]
     * content : 	商品很好
     * add_time : 2017/8/25
     */

    private String photo;
    private String nickname;
    private String specifications;
    private String content;
    private String add_time;

    private List<ImgListBean> img_list;
    /**
     * after_review : {"appraise_id":72,"content":"好用的商品","img_list":[]}
     */

    private List<AfterReviewBean> after_review;

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

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public List<ImgListBean> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<ImgListBean> img_list) {
        this.img_list = img_list;
    }

    public List<AfterReviewBean> getAfter_review() {
        return after_review;
    }

    public void setAfter_review(List<AfterReviewBean> after_review) {
        this.after_review = after_review;
    }

    public static class ImgListBean {
        /**
         * img : http://121.40.186.118:5008/upload/a.jpg
         */

        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public static class AfterReviewBean {
        /**
         * appraise_id : 72
         * content : 好用的商品
         * img_list : []
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
    }
}
