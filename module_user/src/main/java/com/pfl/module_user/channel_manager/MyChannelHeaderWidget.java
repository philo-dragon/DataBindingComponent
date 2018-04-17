package com.pfl.module_user.channel_manager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pfl.module_user.R;

/**
 * 我的分类头部模块
 */
public class MyChannelHeaderWidget implements IChannelType {
    private RecyclerView mRecyclerView;
    private EditModeHandler editModeHandler;

    public MyChannelHeaderWidget(EditModeHandler handler) {
        this.editModeHandler = handler;
    }

    @Override
    public ChannelAdapter.ChannelViewHolder createViewHolder(LayoutInflater mInflater, ViewGroup parent) {
        mRecyclerView = (RecyclerView) parent;
        MyChannelHeaderWidget.MyChannelHeaderViewHolder viewHolder = new MyChannelHeaderWidget.MyChannelHeaderViewHolder(mInflater.inflate(R.layout.module_user_item_channel_manager_my_header, parent, false));
        return viewHolder;
    }

    @Override
    public void bindViewHolder(final ChannelAdapter.ChannelViewHolder holder, int position, ChannelBean data) {
        final MyChannelHeaderViewHolder viewHolder = (MyChannelHeaderViewHolder) holder;

        viewHolder.idEditMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!viewHolder.idEditMode.isSelected()) {
                    if (editModeHandler != null)
                        editModeHandler.startEditMode(mRecyclerView);
                    viewHolder.idEditMode.setText("完成");
                } else {
                    if (editModeHandler != null)
                        editModeHandler.cancelEditMode(mRecyclerView);
                    viewHolder.idEditMode.setText("编辑");
                }
                viewHolder.idEditMode.setSelected(!viewHolder.idEditMode.isSelected());
            }
        });

    }

    public class MyChannelHeaderViewHolder extends ChannelAdapter.ChannelViewHolder {

        public TextView idEditMode;

        public MyChannelHeaderViewHolder(View itemView) {
            super(itemView);
            idEditMode = itemView.findViewById(R.id.id_edit_mode);
        }
    }
}