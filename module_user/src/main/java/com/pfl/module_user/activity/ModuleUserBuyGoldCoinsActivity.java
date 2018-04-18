package com.pfl.module_user.activity;

import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jakewharton.rxbinding2.view.RxView;
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
import java.util.logging.Handler;

/**
 * 购买金币
 */
@Route(path = RouteUtils.MODULE_USER_ACTIVITY_BUY_GOLD_COINS)
public class ModuleUserBuyGoldCoinsActivity extends BaseActivity<ModuleUserActivityBuyGoldCoinsBinding> {

    private List<ChannelEntity> items;
    private TitleFragmentAdapter adapter;
    private int needShowPosition = -1;
    private String mCurrentTitle;

    @Override
    public int getContentView() {
        return R.layout.module_user_activity_buy_gold_coins;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseMessageEvent<List<ChannelEntity>> messageEvent) {

        if (messageEvent.isUpdate()) {
            needShowPosition = messageEvent.getType();
            loadData(messageEvent.getData());
            if (-1 == messageEvent.getType()) {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.viewPager.setCurrentItem(items.size()-1);
                    }
                }, 500);
            }

        } else {
            if (-1 != messageEvent.getType()) {
                mBinding.viewPager.setCurrentItem(messageEvent.getType());
            }
        }
    }

    @Override
    public void initView() {

        EventBusUtil.regist(this);

        items = new ArrayList<>();
        adapter = new TitleFragmentAdapter(getSupportFragmentManager(), items);
        mBinding.viewPager.setAdapter(adapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);//将TabLayout和ViewPager关联起来。

        mBinding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mCurrentTitle = items.get(position).getName();
            }
        });

        List<ChannelEntity> objects = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            ChannelEntity entity = new ChannelEntity();
            entity.setName("频道" + i);
            objects.add(entity);
        }

        mCurrentTitle = objects.get(0).getName();

        loadData(objects);
    }

    private void loadData(List<ChannelEntity> channelEntities) {

        items.clear();
        items.addAll(channelEntities);
        adapter.notifyDataSetChanged();
        if (needShowPosition != -1) {
            mBinding.viewPager.setCurrentItem(needShowPosition);
            needShowPosition = -1;
        }
    }

    @Override
    public void setToolBar() {
        setToolBarHasBack(mBinding.inToolbarLayout.titleBar);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregist(this);
    }
}
