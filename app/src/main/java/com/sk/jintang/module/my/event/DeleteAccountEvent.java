package com.sk.jintang.module.my.event;

/**
 * Created by administartor on 2017/9/12.
 */

public class DeleteAccountEvent {
    public boolean isDeleteDefault;
    public DeleteAccountEvent(boolean isDeleteDefault) {
        this.isDeleteDefault = isDeleteDefault;
    }
}
