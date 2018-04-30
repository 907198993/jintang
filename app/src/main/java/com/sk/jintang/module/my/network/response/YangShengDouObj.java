package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/9/11.
 */

public class YangShengDouObj  extends BaseObj{

    /**
     * keeping_bean : 6410
     * keeping_bean_detail_list : [{"remark":"消费","value":600,"add_time":"2017年8月18日"},{"remark":"消费","value":600,"add_time":"2017年8月18日"},{"remark":"消费","value":600,"add_time":"2017年8月18日"},{"remark":"抵扣","value":-500,"add_time":"2017年8月18日"}]
     */

    private int keeping_bean;
    private String keeping_bean_rule;
    private List<KeepingBeanDetailListBean> keeping_bean_detail_list;

    public int getKeeping_bean() {
        return keeping_bean;
    }

    public void setKeeping_bean(int keeping_bean) {
        this.keeping_bean = keeping_bean;
    }

    public List<KeepingBeanDetailListBean> getKeeping_bean_detail_list() {
        return keeping_bean_detail_list;
    }

    public String getKeeping_bean_rule() {
        return keeping_bean_rule;
    }

    public void setKeeping_bean_rule(String keeping_bean_rule) {
        this.keeping_bean_rule = keeping_bean_rule;
    }

    public void setKeeping_bean_detail_list(List<KeepingBeanDetailListBean> keeping_bean_detail_list) {
        this.keeping_bean_detail_list = keeping_bean_detail_list;
    }

    public static class KeepingBeanDetailListBean {
        /**
         * remark : 消费
         * value : 600
         * add_time : 2017年8月18日
         */

        private String remark;
        private int value;
        private String add_time;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
