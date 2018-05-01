package cn.com.topzuqiu.ui.fragment;

import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseFragment;
import com.pfl.common.di.AppComponent;
import com.pfl.common.entity.message_event.BaseMessageEvent;
import com.pfl.common.tablayout.TabLayout;
import com.pfl.common.utils.EventBusUtil;
import com.pfl.common.utils.RouteUtils;
import com.pfl.common.utils.RxClickUtil;
import com.pfl.module_user.activity.ModuleUserChannelManagerActivity;
import com.pfl.module_user.channel_manager.ChannelEntity;
import com.pfl.module_user.channel_manager.ChannelManagerFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.com.topzuqiu.R;
import cn.com.topzuqiu.adapter.AppChannelFragmentAdapter;
import cn.com.topzuqiu.databinding.AppFragmentFastNewsBinding;

/**
 * 快讯
 */
@Route(path = RouteUtils.APP_FRAGMENT_FAST_NEWS)
public class FastNewsFragment extends BaseFragment<AppFragmentFastNewsBinding> {

    private AppChannelFragmentAdapter pageAdapter;
    private ArrayList<ChannelEntity> channels;

    private int needShowPosition = -1;
    private String mCurrentTitle;
    private int mCurrentPosition;

    @Override
    public int getContentView() {
        return R.layout.app_fragment_fast_news;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseMessageEvent<List<ChannelEntity>> messageEvent) {
        if (messageEvent.isUpdate()) {//频道改变了
            needShowPosition = messageEvent.getType();
            setData(messageEvent.getData(), true);
        } else {//频道没有改变
            if (ModuleUserChannelManagerActivity.RESULE_TYPE != messageEvent.getType()) {
                mBinding.appViewPager.setCurrentItem(messageEvent.getType());
            }
        }
    }

    @Override
    public void initView() {
        EventBusUtil.regist(this);
        channels = new ArrayList<>();
        pageAdapter = new AppChannelFragmentAdapter(getChildFragmentManager(), channels);
        mBinding.appViewPager.setAdapter(pageAdapter);
        mBinding.appTabLayout.setNeedSwitchAnimation(true);
        mBinding.appTabLayout.setIndicatorWidthWrapContent(true);
        mBinding.appTabLayout.setupWithViewPager(mBinding.appViewPager);

        mBinding.appViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mCurrentTitle = channels.get(position).getName();
                mCurrentPosition = position;
            }
        });

        RxClickUtil.RxClick(mBinding.tvMoreChannels, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChannelManagerFragment fragment = new ChannelManagerFragment();
                fragment.show(getChildFragmentManager().beginTransaction(), ChannelManagerFragment.class.getSimpleName());
            }
        });

        List<ChannelEntity> objects = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            ChannelEntity entity = new ChannelEntity();
            entity.setName("频道" + i);
            objects.add(entity);
        }

        mCurrentTitle = objects.get(0).getName();
        setData(objects, false);

    }

    /**
     * 设置数据
     *
     * @param channelEntities 频道列表
     * @param isScrollTab     是否滚动Tab(矫正tab选中效果)
     */
    private void setData(List<ChannelEntity> channelEntities, boolean isScrollTab) {

        channels.clear();
        channels.addAll(channelEntities);
        pageAdapter.notifyDataSetChanged();
        if (needShowPosition != -1) {
            mBinding.appViewPager.setCurrentItem(needShowPosition);
            needShowPosition = -1;
        } else {
            if (!isScrollTab) {
                return;
            }
            mBinding.appTabLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int position = pageAdapter.getPosition(mCurrentTitle);
                    if (position == -1) {
                        if (channels.size() >= mCurrentPosition) {
                            position = mCurrentPosition;
                        } else {
                            position = channels.size() - 1;
                        }
                    }
                    mBinding.appTabLayout.getTabAt(position).select();
                }
            }, 50);
        }

    }

    @Override
    public void setToolBar() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregist(this);
    }
}
