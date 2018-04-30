package com.sk.jintang.module.shoppingcart.network.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.sk.jintang.tools.AndroidUtils;

import java.math.BigDecimal;

/**
 * Created by administartor on 2017/8/19.
 */

public class ShoppingCartObj implements Parcelable {

    /**
     * id : 6
     * goods_id : 1
     * goods_name : 【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品
     * goods_image : http://121.40.186.118:5008/upload/goods.png
     * specification : 黄色*100ml
     * specification_id : 1
     * stock : 200
     * number : 4
     * price : 77
     */

    private int id;
    private String goods_id;
    private String goods_name;
    private String goods_image;
    private String specification;
    private int specification_id;
    private int stock;
    private int number;
    private double price;
    private BigDecimal totalPrice;
    private boolean isSelect=true;

    public ShoppingCartObj() {
    }

    protected ShoppingCartObj(Parcel in) {
        id = in.readInt();
        goods_id = in.readString();
        goods_name = in.readString();
        goods_image = in.readString();
        specification = in.readString();
        specification_id = in.readInt();
        stock = in.readInt();
        number = in.readInt();
        price = in.readDouble();
        isSelect = in.readByte() != 0;
    }

    public static final Creator<ShoppingCartObj> CREATOR = new Creator<ShoppingCartObj>() {
        @Override
        public ShoppingCartObj createFromParcel(Parcel in) {
            return new ShoppingCartObj(in);
        }

        @Override
        public ShoppingCartObj[] newArray(int size) {
            return new ShoppingCartObj[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public double getTotalPrice() {
        BigDecimal bdNum=new BigDecimal(getNumber());
        BigDecimal bdPrice=new BigDecimal(getPrice());
        BigDecimal total = bdNum.multiply(bdPrice);
        double round = AndroidUtils.round(total.doubleValue());
        return round;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public int getSpecification_id() {
        return specification_id;
    }

    public void setSpecification_id(int specification_id) {
        this.specification_id = specification_id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(goods_id);
        parcel.writeString(goods_name);
        parcel.writeString(goods_image);
        parcel.writeString(specification);
        parcel.writeInt(specification_id);
        parcel.writeInt(stock);
        parcel.writeInt(number);
        parcel.writeDouble(price);
        parcel.writeByte((byte) (isSelect ? 1 : 0));
    }
}
