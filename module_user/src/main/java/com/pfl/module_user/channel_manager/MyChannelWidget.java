package com.pfl.module_user.channel_manager;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pfl.module_user.R;

/**
 * 我的分类模块
 */
public class MyChannelWidget implements IChannelType {
    private RecyclerView mRecyclerView;
    private EditModeHandler editModeHandler;

    public MyChannelWidget(EditModeHandler editModeHandler) {
        this.editModeHandler = editModeHandler;
    }

    @Override
    public ChannelAdapter.ChannelViewHolder createViewHolder(LayoutInflater mInflater, ViewGroup parent) {
        mRecyclerView = (RecyclerView) parent;
        MyChannelWidget.MyChannelWidgetViewHolder viewHolder = new MyChannelWidget.MyChannelWidgetViewHolder(mInflater.inflate(R.layout.module_user_item_channel_manager_my_widget, parent, false));
        return viewHolder;
    }

    @Override
    public void bindViewHolder(final ChannelAdapter.ChannelViewHolder holder, final int position, final ChannelBean data) {
        final MyChannelWidgetViewHolder myHolder = (MyChannelWidgetViewHolder) holder;

        myHolder.mChannelTitleTv.setText(data.getTabName());
        int textSize = data.getTabName().length() >= 4 ? 14 : 16;
        myHolder.mChannelTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        myHolder.mChannelTitleTv.setBackgroundResource(data.getTabType() == 0 || data.getTabType() == 1 ? R.drawable.module_user_channel_fixed_bg_shape : R.drawable.module_user_channel_my_bg_shape);
        myHolder.mChannelTitleTv.setTextColor(data.getTabType() == 0 ? Color.RED : data.getTabType() == 1 ? Color.parseColor("#666666") : Color.parseColor("#333333"));
        myHolder.mDeleteIv.setVisibility(data.getEditStatus() == 1 ? View.VISIBLE : View.INVISIBLE);
        myHolder.mChannelTitleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editModeHandler != null && data.getTabType() == IChannelType.TYPE_MY_CHANNEL) {
                    editModeHandler.clickMyChannel(mRecyclerView, holder);
                }
            }
        });

        myHolder.mChannelTitleTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (editModeHandler != null && data.getTabType() == IChannelType.TYPE_MY_CHANNEL) {
                    editModeHandler.touchMyChannel(motionEvent, holder);
                }
                return false;
            }
        });

        myHolder.mChannelTitleTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (editModeHandler != null && data.getTabType() == IChannelType.TYPE_MY_CHANNEL) {
                    editModeHandler.clickLongMyChannel(mRecyclerView, holder);
                }
                return true;
            }
        });

    }

    public static class MyChannelWidgetViewHolder extends ChannelAdapter.ChannelViewHolder {

        public TextView mChannelTitleTv;
        public ImageView mDeleteIv;

        private MyChannelWidgetViewHolder(View itemView) {
            super(itemView);
            mChannelTitleTv = itemView.findViewById(R.id.id_channel_title);
            mDeleteIv = itemView.findViewById(R.id.id_delete_icon);
        }
    }
}