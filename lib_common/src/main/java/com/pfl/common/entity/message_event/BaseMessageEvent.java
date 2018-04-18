package com.pfl.common.entity.message_event;

/**
 * Created by rocky on 2018/4/18.
 */

public class BaseMessageEvent<T> {

    private T data;
    private int type;
    private boolean isUpdate;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }
}
