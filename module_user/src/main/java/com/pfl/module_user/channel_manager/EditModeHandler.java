package com.pfl.module_user.channel_manager;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

/**
 * 这个抽象类主要是抽象了各个模块的点击事件，然后在RecyclerView.Adapter里面统一处理
 */
public abstract class EditModeHandler {
    //开始编辑处理的事件
    public void startEditMode(RecyclerView mRecyclerView) {
    }

    //取消编辑完成状态的事件
    public void cancelEditMode(RecyclerView mRecyclerView) {
    }

    //点击我的分类里面item事件
    public void clickMyChannel(RecyclerView mRecyclerView, ChannelAdapter.ChannelViewHolder holder) {
    }

    //长按我的分类里面item事件
    public void clickLongMyChannel(RecyclerView mRecyclerView, ChannelAdapter.ChannelViewHolder holder) {
    }

    //手机触摸我的分类里面item事件
    public void touchMyChannel(MotionEvent motionEvent, ChannelAdapter.ChannelViewHolder holder) {
    }

    //点击推荐分类里面的item事件
    public void clickRecChannel(RecyclerView mRecyclerView, ChannelAdapter.ChannelViewHolder holder) {
    }
}