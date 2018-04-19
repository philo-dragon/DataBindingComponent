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

    private List<ChannelEntity> items;
    private TitleFragmentAdapter adapter;
    private int needShowPosition = -1;
    private String mCurrentTitle;
    private int mCurrentPosition;

    @Override
    public int getContentView() {
        return R.layout.module_user_activity_buy_gold_coins;
    }

    @Override
    public void componentInject(AppComponent appComponent) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseMessageEvent<List<ChannelEntity>> messageEvent) {
        if (messageEvent.isUpdate()) {//频道改变了
            needShowPosition = messageEvent.getType();
            loadData(messageEvent.getData(), true);
        } else {//频道没有改变
            if (ModuleUserChannelManagerActivity.RESULE_TYPE != messageEvent.getType()) {
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
                mCurrentPosition = position;
            }
        });

        List<ChannelEntity> objects = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            ChannelEntity entity = new ChannelEntity();
            entity.setName("频道" + i);
            objects.add(entity);
        }

        mCurrentTitle = objects.get(0).getName();
        loadData(objects, false);
    }

    private void loadData(List<ChannelEntity> channelEntities, boolean isScrollTab) {

        items.clear();
        items.addAll(channelEntities);
        adapter.notifyDataSetChanged();
        if (needShowPosition != -1) {
            mBinding.viewPager.setCurrentItem(needShowPosition);
            needShowPosition = -1;
        } else {
            if (!isScrollTab) {
                return;
            }
            mBinding.tabLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int position = adapter.getPosition(mCurrentTitle);
                    if (position == -1) {
                        if (items.size() >= mCurrentPosition) {
                            position = mCurrentPosition;
                        } else {
                            position = items.size() - 1;
                        }
                    }
                    mBinding.tabLayout.getTabAt(position).select();
                }
            }, 50);
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
