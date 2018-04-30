package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/9/26.
 */

public class WuLiuObj extends BaseObj {
    /**
     * courier_number : 3333514758796
     * courier_name : 申通快递
     * courier_phone : 400-100-8800
     * courier_list : [{"time":"2017-07-14 20:59:11","context":"交易成功"},{"time":"2017-07-10 12:35:06","context":"已签收,签收人是草签"},{"time":"2017-07-10 09:03:58","context":"【上海宝山公司】的派件员【任冬冬】正在派件 电话:18917056371"},{"time":"2017-07-10 09:03:58","context":"【上海宝山公司】已收入"},{"time":"2017-07-10 06:40:00","context":"快件已到达【上海宝山公司】 扫描员是【广粤到件】上一站是【】"},{"time":"2017-07-10 06:02:40","context":"快件已到达【上海宝山公司】 扫描员是【广粤到件】上一站是【】"},{"time":"2017-07-09 19:43:45","context":"快件已到达【上海宝山公司】 扫描员是【宝山总部操作】上一站是【】"},{"time":"2017-07-09 19:43:02","context":"由【上海中转部】发往【上海宝山公司】"},{"time":"2017-07-09 08:49:14","context":"由【浙江杭州中转部】发往【上海中转部】"},{"time":"2017-07-09 08:49:14","context":"【浙江杭州中转部】正在进行【装车】扫描"},{"time":"2017-07-08 18:58:02","context":"由【浙江安吉公司】发往【浙江杭州中转部】"},{"time":"2017-07-08 18:58:02","context":"【浙江安吉公司】正在进行【装车】扫描"},{"time":"2017-07-08 18:56:58","context":"【浙江安吉公司】的收件员【席饰多旗舰店】已收件"},{"time":"2017-07-07 20:11:17","context":"卖家发货"},{"time":"2017-07-07 20:10:38","context":"您的包裹已出库"},{"time":"2017-07-07 19:42:43","context":"您的订单待配货"}]
     */

    private String courier_number;
    private String courier_name;
    private String courier_phone;
    private List<CourierListBean> courier_list;

    public String getCourier_number() {
        return courier_number;
    }

    public void setCourier_number(String courier_number) {
        this.courier_number = courier_number;
    }

    public String getCourier_name() {
        return courier_name;
    }

    public void setCourier_name(String courier_name) {
        this.courier_name = courier_name;
    }

    public String getCourier_phone() {
        return courier_phone;
    }

    public void setCourier_phone(String courier_phone) {
        this.courier_phone = courier_phone;
    }

    public List<CourierListBean> getCourier_list() {
        return courier_list;
    }

    public void setCourier_list(List<CourierListBean> courier_list) {
        this.courier_list = courier_list;
    }

    public static class CourierListBean {
        /**
         * time : 2017-07-14 20:59:11
         * context : 交易成功
         */

        private String time;
        private String context;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }
    }
}
