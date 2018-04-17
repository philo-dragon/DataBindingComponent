package com.pfl.module_user.channel_manager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pfl.module_user.R;

/**
 * Created by rocky on 2018/4/17.
 */

public class RecChannelHeaderWidget implements IChannelType {

    private RecyclerView mRecyclerView;
    private EditModeHandler editModeHandler;

    public RecChannelHeaderWidget(EditModeHandler handler) {
        this.editModeHandler = handler;
    }

    @Override
    public ChannelAdapter.ChannelViewHolder createViewHolder(LayoutInflater mInflater, ViewGroup parent) {

        mRecyclerView = (RecyclerView) parent;
        RecChannelHeaderWidget.RecChannelHeaderViewHolder viewHolder = new RecChannelHeaderWidget.RecChannelHeaderViewHolder(mInflater.inflate(R.layout.module_user_item_channel_manager_rec_header, parent, false));
        return viewHolder;

    }

    @Override
    public void bindViewHolder(final ChannelAdapter.ChannelViewHolder holder, int position, ChannelBean data) {
        final MyChannelHeaderWidget.MyChannelHeaderViewHolder viewHolder = (MyChannelHeaderWidget.MyChannelHeaderViewHolder) holder;

    }

    public class RecChannelHeaderViewHolder extends ChannelAdapter.ChannelViewHolder {
        private TextView mEditModeTv;

        public RecChannelHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

}
