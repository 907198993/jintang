package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/9/21.
 */

public class YongJinObj extends BaseObj {
    /**
     * commission : 800
     * commission_detail_list : [{"remark":"下级分销完成订单￥6000.00","value":60},{"remark":"下级分销完成订单￥6000.00","value":60},{"remark":"下级分销完成订单￥6000.00","value":60},{"remark":"提现","value":-60}]
     */

    private double commission;
    private List<CommissionDetailListBean> commission_detail_list;

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public List<CommissionDetailListBean> getCommission_detail_list() {
        return commission_detail_list;
    }

    public void setCommission_detail_list(List<CommissionDetailListBean> commission_detail_list) {
        this.commission_detail_list = commission_detail_list;
    }

    public static class CommissionDetailListBean {
        /**
         * remark : 下级分销完成订单￥6000.00
         * value : 60
         */

        private String remark;
        private double value;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
