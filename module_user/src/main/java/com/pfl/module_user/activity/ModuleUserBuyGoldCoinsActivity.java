package com.pfl.module_user.activity;

import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.entity.message_event.BaseMessageEvent;
import com.pfl.common.utils.EventBusUtil;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.adapter.TitleFragmentAdapter;
import com.pfl.module_user.channel_manager.ChannelEntity;
import com.pfl.module_user.databinding.ModuleUserActivityBuyGoldCoinsBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 购买金币
 */
@Route(path = RouteUtils.MODULE_USER_ACTIVITY_BUY_GOLD_COINS)
public class ModuleUserBuyGoldCoinsActivity extends BaseActivity<ModuleUserActivityBuyGoldCoinsBinding> {


    @Override
    public int getContentView() {
        return R.layout.module_user_activity_buy_gold_coins;
    }

    @Override
    public void componentInject(AppComponent appComponent) {
    }


    @Override
    public void initView() {

    }



    @Override
    public void setToolBar() {
        setToolBarHasBack(mBinding.inToolbarLayout.titleBar);
    }

    @Override
    public void initData() {

    }

}
