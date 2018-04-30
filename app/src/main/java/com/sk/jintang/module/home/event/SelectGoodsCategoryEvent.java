package com.sk.jintang.module.home.event;

/**
 * Created by administartor on 2017/10/12.
 */

public class SelectGoodsCategoryEvent {
    public String typeId;
    public String typeName;

    public SelectGoodsCategoryEvent(String typeId,String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }
}
