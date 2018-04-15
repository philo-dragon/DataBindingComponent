package com.pfl.module_user.fragment;


import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseFragment;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserFragmentMineBinding;


@Route(path = RouteUtils.MODULE_USER_FRAGMENT_MINE)
public class ModuleUserMineFragment extends BaseFragment<ModuleUserFragmentMineBinding> {

    @Override
    public int getContentView() {
        return R.layout.module_user_fragment_mine;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

    }

    @Override
    public void initView() {

        mBinding.inContent.rlMyMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_LOGIN);
            }
        });
        mBinding.inHeader.layoutFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_FOCUS_AND_FANS);
            }
        });
        mBinding.inHeader.layoutFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_FOCUS_AND_FANS);
            }
        });
    }

    @Override
    public void setToolBar() {

    }

    @Override
    public void initData() {

    }
}
