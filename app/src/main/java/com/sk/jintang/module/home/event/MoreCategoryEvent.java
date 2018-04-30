package com.sk.jintang.module.home.event;

/**
 * Created by administartor on 2017/9/15.
 */

public class MoreCategoryEvent {
    public String typeId;
    public String typeName;

    public MoreCategoryEvent(String typeId,String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }
}
