package com.sk.jintang.module.my.network.response;

import java.io.Serializable;

/**
 * Created by administartor on 2017/9/12.
 */

public class AddressObj implements Serializable{

    /**
     * id : 4
     * recipient : xiaoming
     * phone : 15738504592
     * shipping_address : 北京市,北京市,东城区
     * shipping_address_details : Fheoifhwoifwe
     * is_default : 1
     */

    private int id;
    private String recipient;
    private String phone;
    private String shipping_address;
    private String shipping_address_details;
    private int is_default;

    private String province;
    private String city;
    private String zone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getShipping_address_details() {
        return shipping_address_details;
    }

    public void setShipping_address_details(String shipping_address_details) {
        this.shipping_address_details = shipping_address_details;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }
}
