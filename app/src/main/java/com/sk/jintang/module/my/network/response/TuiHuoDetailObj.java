package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

/**
 * Created by administartor on 2017/9/21.
 */

public class TuiHuoDetailObj extends BaseObj {
    /**
     * order_no : P170919145918958891
     * refund_type : 我要退款
     * refund_reason : 7天没有理由退换货
     * refund_amount : 77
     * refund_voucher1 : http://121.40.186.118:5008/upload/201709/20/170920163715246684.jpg
     * refund_voucher2 :
     * refund_voucher3 :
     * refund_status : 1
     * refund_remark : 商家正在处理中，请耐心等待！
     * refund_add_time : 2017/9/20
     */

    private String order_no;
    private String refund_type;
    private String refund_reason;
    private double refund_amount;
    private String refund_voucher1;
    private String refund_voucher2;
    private String refund_voucher3;
    private int refund_status;
    private String refund_remark;
    private String refund_add_time;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getRefund_type() {
        return refund_type;
    }

    public void setRefund_type(String refund_type) {
        this.refund_type = refund_type;
    }

    public String getRefund_reason() {
        return refund_reason;
    }

    public void setRefund_reason(String refund_reason) {
        this.refund_reason = refund_reason;
    }

    public double getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(double refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getRefund_voucher1() {
        return refund_voucher1;
    }

    public void setRefund_voucher1(String refund_voucher1) {
        this.refund_voucher1 = refund_voucher1;
    }

    public String getRefund_voucher2() {
        return refund_voucher2;
    }

    public void setRefund_voucher2(String refund_voucher2) {
        this.refund_voucher2 = refund_voucher2;
    }

    public String getRefund_voucher3() {
        return refund_voucher3;
    }

    public void setRefund_voucher3(String refund_voucher3) {
        this.refund_voucher3 = refund_voucher3;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }

    public String getRefund_remark() {
        return refund_remark;
    }

    public void setRefund_remark(String refund_remark) {
        this.refund_remark = refund_remark;
    }

    public String getRefund_add_time() {
        return refund_add_time;
    }

    public void setRefund_add_time(String refund_add_time) {
        this.refund_add_time = refund_add_time;
    }
}
