package com.pfl.module_user.activity;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.entity.message_event.BaseMessageEvent;
import com.pfl.common.utils.EventBusUtil;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.channel_manager.ChannelAdapter;
import com.pfl.module_user.channel_manager.ChannelEntity;
import com.pfl.module_user.databinding.ModuleUserActivityChannelManagerBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道管理
 */
@Route(path = RouteUtils.MODULE_USER_ACTIVITY_CHANNEL_MANAGER)
public class ModuleUserChannelManagerActivity extends BaseActivity<ModuleUserActivityChannelManagerBinding> {

    @Override
    public int getContentView() {
        return R.layout.module_user_activity_channel_manager;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

    }

    @Override
    public void initView() {


        final List<ChannelEntity> items = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            ChannelEntity entity = new ChannelEntity();
            entity.setName("频道" + i);
            items.add(entity);
        }
        final List<ChannelEntity> otherItems = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ChannelEntity entity = new ChannelEntity();
            entity.setName("其他" + i);
            otherItems.add(entity);
        }

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mBinding.recyclerView.setLayoutManager(manager);
        final ChannelAdapter adapter = new ChannelAdapter(this, items, otherItems);
        mBinding.recyclerView.setAdapter(adapter);


        adapter.setOnMyChannelItemClickListener(new ChannelAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(boolean update, int position) {

                BaseMessageEvent<List<ChannelEntity>> messageEvent = new BaseMessageEvent<>();
                messageEvent.setData(adapter.getMyChannelItems());
                messageEvent.setUpdate(update);
                messageEvent.setType(position);
                EventBusUtil.postMessage(messageEvent);
                finish();
            }

            @Override
            public void onFinish(boolean update) {
                BaseMessageEvent<List<ChannelEntity>> messageEvent = new BaseMessageEvent<>();
                messageEvent.setType(-1);
                messageEvent.setData(adapter.getMyChannelItems());
                messageEvent.setUpdate(update);
                EventBusUtil.postMessage(messageEvent);
                finish();
            }
        });

    }

    @Override
    public void setToolBar() {
        setToolBarHasBack(mBinding.inToolbarLayout.titleBar);
    }

    @Override
    public void initData() {

    }

}
