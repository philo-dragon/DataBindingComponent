package com.pfl.module_user.activity;

import android.support.v7.widget.helper.ItemTouchHelper;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.channel_manager.ItemDragHelperCallback;
import com.pfl.module_user.channel_manager.ItemDragListener;
import com.pfl.module_user.databinding.ModuleUserActivityChannelManagerBinding;

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

    }

    @Override
    public void setToolBar() {

    }

    @Override
    public void initData() {

    }

}
