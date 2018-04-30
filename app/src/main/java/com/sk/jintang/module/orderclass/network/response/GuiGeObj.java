package com.sk.jintang.module.orderclass.network.response;

/**
 * Created by administartor on 2017/9/13.
 */

public class GuiGeObj {
    /**
     * id : 1
     * images : http://121.40.186.118:5008/upload/goods.png
     * price : 77
     * stock : 200
     * specification : 黄色*100ml
     */

    private int id;
    private String images;
    private double price;
    private int stock;
    private String specification;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
}
