package com.pfl.common.entity.base;

import android.databinding.BaseObservable;

/**
 * Created by rocky on 2018/4/8.
 */

public abstract class BaseEntyty extends BaseObservable {

    public static final int BASE_ITEM_TYPE = 0x4d;

    private int itemType;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
