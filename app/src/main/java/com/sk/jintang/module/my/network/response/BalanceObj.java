package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/9/12.
 */

public class BalanceObj extends BaseObj {
    /**
     * balance : 54668
     * balance_detail_list : [{"remark":"提现","value":-8000,"add_time":"2017年9月1日"},{"remark":"交易","value":-128,"add_time":"2017年8月18日"},{"remark":"提现","value":-100,"add_time":"2017年8月18日"},{"remark":"充值","value":200,"add_time":"2017年8月18日"},{"remark":"提现","value":-100,"add_time":"2017年8月18日"}]
     */

    private double balance;
    private List<BalanceDetailListBean> balance_detail_list;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<BalanceDetailListBean> getBalance_detail_list() {
        return balance_detail_list;
    }

    public void setBalance_detail_list(List<BalanceDetailListBean> balance_detail_list) {
        this.balance_detail_list = balance_detail_list;
    }

    public static class BalanceDetailListBean {
        /**
         * remark : 提现
         * value : -8000
         * add_time : 2017年9月1日
         */

        private String remark;
        private double value;
        private String add_time;

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

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
