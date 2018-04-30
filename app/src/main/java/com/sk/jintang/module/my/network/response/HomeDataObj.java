package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/8/19.
 */

public class HomeDataObj extends BaseObj {

    private List<InformationListBean> information_list;
    private List<EppocenterListBean> eppocenter_list;

    public List<InformationListBean> getInformation_list() {
        return information_list;
    }

    public void setInformation_list(List<InformationListBean> information_list) {
        this.information_list = information_list;
    }

    public List<EppocenterListBean> getEppocenter_list() {
        return eppocenter_list;
    }

    public void setEppocenter_list(List<EppocenterListBean> eppocenter_list) {
        this.eppocenter_list = eppocenter_list;
    }

    public static class InformationListBean {
        /**
         * id : 39
         * title : 农业部经管司长诠释土地制度改革:“三权分置”“有别于“联产承包”
         * image_url : http://121.40.186.118:5009/upload/1234.jpg
         * page_view : 235
         * add_time : 2017/8/9
         */

        private int id;
        private String title;
        private String image_url;
        private int page_view;
        private String add_time;

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

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public int getPage_view() {
            return page_view;
        }

        public void setPage_view(int page_view) {
            this.page_view = page_view;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }

    public static class EppocenterListBean {
        /**
         * id : 10
         * title : 要把特色优势转化为经济优势和发展优势
         * image_url : http://121.40.186.118:5009/upload/1234.jpg
         * add_time : 2017/8/9
         */

        private int id;
        private String title;
        private String image_url;
        private String add_time;

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

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
