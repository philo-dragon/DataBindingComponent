package com.pfl.module_user.channel_manager;

/**
 * Created by rocky on 2018/4/17.
 */

public class ChannelBean {
    private String tabName;
    private int tabType;
    private int editStatus;

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public int getTabType() {
        return tabType;
    }

    public void setTabType(int tabType) {
        this.tabType = tabType;
    }

    public int getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(int editStatus) {
        this.editStatus = editStatus;
    }
}
