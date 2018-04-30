package com.sk.jintang.tools.alipay;

/**
 * Created by administartor on 2017/9/5.
 */

public class AliPay {
    /**
     * timeout_express : 30m
     * product_code : QUICK_MSECURITY_PAY
     * total_amount : 0.01
     * subject : 1
     * body : 我是测试数据
     * out_trade_no : +getOutTradeNo()+
     */

    private String product_code;
    private double total_amount;
    private String subject;
    private String body;
    private String out_trade_no;

    public String getProduct_code() {
        return "QUICK_MSECURITY_PAY";
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
}
