package com.pfl.module_user.channel_manager;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pfl.module_user.R;

/**
 * Created by rocky on 2018/4/17.
 */

public class RecChannelWidget implements IChannelType {

    private RecyclerView mRecyclerView;
    private EditModeHandler editModeHandler;

    public RecChannelWidget(EditModeHandler handler) {
        this.editModeHandler = handler;
    }

    @Override
    public ChannelAdapter.ChannelViewHolder createViewHolder(LayoutInflater mInflater, ViewGroup parent) {
        mRecyclerView = (RecyclerView) parent;
        RecChannelWidget.RecChannelWidgetViewHolder viewHolder = new RecChannelWidget.RecChannelWidgetViewHolder(mInflater.inflate(R.layout.module_user_item_channel_manager_rec_widget, parent, false));
        return viewHolder;
    }

    @Override
    public void bindViewHolder(final ChannelAdapter.ChannelViewHolder holder, int position, ChannelBean data) {
        RecChannelWidget.RecChannelWidgetViewHolder viewHolder = (RecChannelWidget.RecChannelWidgetViewHolder) holder;
        viewHolder.mChannelTitleTv.setText(data.getTabName());
        int textSize = data.getTabName().length() >= 4 ? 14 : 16;
        viewHolder.mChannelTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        viewHolder.mChannelTitleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editModeHandler.clickRecChannel(mRecyclerView, holder);
            }
        });


    }

    public class RecChannelWidgetViewHolder extends ChannelAdapter.ChannelViewHolder {
        TextView mChannelTitleTv;

        public RecChannelWidgetViewHolder(View itemView) {
            super(itemView);
            mChannelTitleTv = itemView.findViewById(R.id.tv_my_category);
        }
    }

}
