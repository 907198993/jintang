package com.sk.jintang.module.orderclass.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/10/10.
 */

public class SearchRecordObj extends BaseObj {
    private List<RecentlyListBean> recently_list;
    private List<HottestListBean> hottest_list;

    public List<RecentlyListBean> getRecently_list() {
        return recently_list;
    }

    public void setRecently_list(List<RecentlyListBean> recently_list) {
        this.recently_list = recently_list;
    }

    public List<HottestListBean> getHottest_list() {
        return hottest_list;
    }

    public void setHottest_list(List<HottestListBean> hottest_list) {
        this.hottest_list = hottest_list;
    }

    public static class RecentlyListBean {
        /**
         * search_term : Duycuyciy
         */

        private String search_term;

        public String getSearch_term() {
            return search_term;
        }

        public void setSearch_term(String search_term) {
            this.search_term = search_term;
        }
    }

    public static class HottestListBean {
        /**
         * search_term : 香薰精油
         */

        private String search_term;

        public String getSearch_term() {
            return search_term;
        }

        public void setSearch_term(String search_term) {
            this.search_term = search_term;
        }
    }
}
