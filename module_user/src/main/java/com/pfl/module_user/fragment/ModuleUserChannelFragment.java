package com.pfl.module_user.fragment;


import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseFragment;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserFragmentChannelBinding;
import com.pfl.module_user.channel_manager.ChannelManagerFragment;

/**
 * 频道界面
 */
@Route(path = RouteUtils.MODULE_USER_FRAGMENT_CHANNEL)
public class ModuleUserChannelFragment extends BaseFragment<ModuleUserFragmentChannelBinding> {

    @Autowired
    String title;

    @Override
    public int getContentView() {
        return R.layout.module_user_fragment_channel;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

    }

    @Override
    public void initView() {

        mBinding.tvTitle.setText(title == null ? "" : title);
        mBinding.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChannelManagerFragment fragment = new ChannelManagerFragment();
                fragment.show(getChildFragmentManager().beginTransaction(),ChannelManagerFragment.class.getSimpleName());
                //RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_CHANNEL_MANAGER);
            }
        });
    }

    @Override
    public void setToolBar() {

    }

    @Override
    public void initData() {

    }

    public String getTitle() {
        return title;
    }
}
