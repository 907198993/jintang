package com.sk.jintang.module.home.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/9/12.
 */

public class HomeBannerObj extends BaseObj {
    //shuffling_list 轮播图[img_url 图片路径],list 签到三个图标[title 名称，img_url 图片]
    private List<ShufflingListBean> shuffling_list;
    private List<ListBean> list;

    public List<ShufflingListBean> getShuffling_list() {
        return shuffling_list;
    }

    public void setShuffling_list(List<ShufflingListBean> shuffling_list) {
        this.shuffling_list = shuffling_list;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ShufflingListBean {
        /**
         * img_url : http://121.40.186.118:5008/upload/123.jpg
         */

        private String img_url;
        private String goods_id;
        //(0商品不存在 1普通商品 2限时抢购 3团购),status商品状态(0商品不存在或者活动已结束 1商品存在活动没结束)],
        private int code;
        private int status;

        public int getCode() {
            return code;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }

    public static class ListBean {
        /**
         * title : 签到送积分
         * img_url : http://121.40.186.118:5008/upload/1.png
         */

        private String title;
        private String img_url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }
}
