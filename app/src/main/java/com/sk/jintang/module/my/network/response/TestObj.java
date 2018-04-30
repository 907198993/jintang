package com.sk.jintang.module.my.network.response;

import java.util.List;

/**
 * Created by administartor on 2017/9/22.
 */

public class TestObj {
    /**
     * Total : 2
     * ErrCode : 0
     * ErrMsg :
     * Response : [{"appraise_id":8,"photo":"http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg","nickname":"哟嚯","goods_id":1,"goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","goods_img":"http://121.40.186.118:5008/upload/goods.png","price":78,"content":"","is_after_review":0,"add_time":"2017/9/22","after_review":null},{"appraise_id":7,"photo":"http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg","nickname":"哟嚯","goods_id":1,"goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","goods_img":"http://121.40.186.118:5008/upload/goods.png","price":76,"content":"","is_after_review":0,"add_time":"2017/9/22","after_review":null}]
     */

    private int Total;
    private int ErrCode;
    private String ErrMsg;
    private List<ResponseBean> Response;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public int getErrCode() {
        return ErrCode;
    }

    public void setErrCode(int ErrCode) {
        this.ErrCode = ErrCode;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String ErrMsg) {
        this.ErrMsg = ErrMsg;
    }

    public List<ResponseBean> getResponse() {
        return Response;
    }

    public void setResponse(List<ResponseBean> Response) {
        this.Response = Response;
    }

    public static class ResponseBean {
        /**
         * appraise_id : 8
         * photo : http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg
         * nickname : 哟嚯
         * goods_id : 1
         * goods_name : 【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品
         * goods_img : http://121.40.186.118:5008/upload/goods.png
         * price : 78
         * content :
         * is_after_review : 0
         * add_time : 2017/9/22
         * after_review : null
         */

        private int appraise_id;
        private String photo;
        private String nickname;
        private int goods_id;
        private String goods_name;
        private String goods_img;
        private int price;
        private String content;
        private int is_after_review;
        private String add_time;
        private Object after_review;

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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
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

        public Object getAfter_review() {
            return after_review;
        }

        public void setAfter_review(Object after_review) {
            this.after_review = after_review;
        }
    }
}
