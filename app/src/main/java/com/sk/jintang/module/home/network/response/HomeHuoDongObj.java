package com.sk.jintang.module.home.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/9/12.
 */

public class HomeHuoDongObj extends BaseObj {
    private List<FlashsaleListBean> flashsale_list;
    private List<ShufflingListBean> shuffling_list;
    private List<ListBean> list;
    private List<HeadlineListBean> headline_list;

    public List<FlashsaleListBean> getFlashsale_list() {
        return flashsale_list;
    }

    public void setFlashsale_list(List<FlashsaleListBean> flashsale_list) {
        this.flashsale_list = flashsale_list;
    }

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

    public List<HeadlineListBean> getHeadline_list() {
        return headline_list;
    }

    public void setHeadline_list(List<HeadlineListBean> headline_list) {
        this.headline_list = headline_list;
    }

    public static class FlashsaleListBean {
        /**
         * title : 限时抢购
         * img_url : http://121.40.186.118:5008/upload/4.png
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

    public static class ShufflingListBean {
        /**
         * title : 今日爆款
         * img_url : http://121.40.186.118:5008/upload/4.png
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

    public static class ListBean {
        /**
         * title : 24小时到
         * img_url : http://121.40.186.118:5008/upload/4.png
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

    public static class HeadlineListBean {
        /**
         * id : 9
         * title : 【震惊】用错精油竟然危害这么大！
         */

        private int id;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
