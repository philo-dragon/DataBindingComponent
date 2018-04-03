package com.pfl.common.utils;

import android.graphics.Color;

import com.pfl.common.R;


/**
 * Created by rocky on 2018/2/6.
 */

public class AppConfig {

    private static final boolean DEFAULT_SWIPE_RELATE_ENABLE = true;
    private static final boolean DEFAULT_SWIPE_BACK_ENABLE = true;
    private static final boolean DEFAULT_IMMERSIVE = true;
    private static final boolean DEFAULT_NEED_BACK = true;
    private static final boolean DEFAULT_SUPPORT_DATA_BINDIND = false;

    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;

    private boolean isSwipeRelateEnable = DEFAULT_SWIPE_RELATE_ENABLE;
    private boolean isSwipeBackEnable = DEFAULT_SWIPE_BACK_ENABLE;
    private boolean isImmersive = DEFAULT_IMMERSIVE;
    private boolean isNeedBack = DEFAULT_NEED_BACK;
    private boolean isSupportDataBindind = DEFAULT_SUPPORT_DATA_BINDIND;
    private int actionTextColor = DEFAULT_TEXT_COLOR;
    private int leftImageResource = R.drawable.ic_clear_black_24dp;
    private int backGroundColor = Color.parseColor("#3F51B5");
    private int leftTextColor = DEFAULT_TEXT_COLOR;
    private int titleColor = DEFAULT_TEXT_COLOR;
    private int toolBarDividerColor = Color.WHITE;

    private AppConfig(Builder builder) {
        isSupportDataBindind = builder.isSupportDataBindind;
        isSwipeRelateEnable = builder.isSwipeRelateEnable;
        isSwipeBackEnable = builder.isSwipeBackEnable;
        isImmersive = builder.isImmersive;
        isNeedBack = builder.isNeedBack;
        actionTextColor = builder.actionTextColor;
        leftImageResource = builder.leftImageResource;
        backGroundColor = builder.backGroundColor;
        leftTextColor = builder.leftTextColor;
        titleColor = builder.titleColor;
        toolBarDividerColor = builder.toolBarDividerColor;
    }


    public boolean isSwipeRelateEnable() {
        return isSwipeRelateEnable;
    }

    public boolean isSwipeBackEnable() {
        return isSwipeBackEnable;
    }

    public boolean isImmersive() {
        return isImmersive;
    }

    public boolean isNeedBack() {
        return isNeedBack;
    }

    public int getActionTextColor() {
        return actionTextColor;
    }

    public int getLeftImageResource() {
        return leftImageResource;
    }

    public int getBackGroundColor() {
        return backGroundColor;
    }

    public int getLeftTextColor() {
        return leftTextColor;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public int getToolBarDividerColor() {
        return toolBarDividerColor;
    }

    public static final class Builder {
        private boolean isSwipeRelateEnable = DEFAULT_SWIPE_RELATE_ENABLE;
        private boolean isSwipeBackEnable = DEFAULT_SWIPE_BACK_ENABLE;
        private boolean isImmersive = DEFAULT_IMMERSIVE;
        private boolean isNeedBack = DEFAULT_NEED_BACK;
        private boolean isSupportDataBindind = DEFAULT_SUPPORT_DATA_BINDIND;
        private int actionTextColor = DEFAULT_TEXT_COLOR;
        private int leftImageResource = R.drawable.ic_clear_black_24dp;
        private int backGroundColor = Color.parseColor("#3F51B5");
        private int leftTextColor = DEFAULT_TEXT_COLOR;
        private int titleColor = DEFAULT_TEXT_COLOR;
        private int toolBarDividerColor = Color.WHITE;

        public Builder() {
        }

        public Builder isSwipeRelateEnable(boolean val) {
            isSwipeRelateEnable = val;
            return this;
        }

        public Builder isSwipeBackEnable(boolean val) {
            isSwipeBackEnable = val;
            return this;
        }

        public Builder isImmersive(boolean val) {
            isImmersive = val;
            return this;
        }

        public Builder isNeedBack(boolean val) {
            isNeedBack = val;
            return this;
        }

        public Builder actionTextColor(int val) {
            actionTextColor = val;
            return this;
        }

        public Builder leftImageResource(int val) {
            leftImageResource = val;
            return this;
        }

        public Builder backGroundColor(int val) {
            backGroundColor = val;
            return this;
        }

        public Builder leftTextColor(int val) {
            leftTextColor = val;
            return this;
        }

        public Builder titleColor(int val) {
            titleColor = val;
            return this;
        }

        public Builder toolBarDividerColor(int val) {
            toolBarDividerColor = val;
            return this;
        }

        public Builder setSupportDataBindind(boolean supportDataBindind) {
            isSupportDataBindind = supportDataBindind;
            return this;
        }

        public AppConfig build() {
            return new AppConfig(this);
        }
    }
}
