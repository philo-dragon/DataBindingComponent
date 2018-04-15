package com.pfl.module_user.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserActivityAccountInfoBinding;

/**
 * 账户信息
 */
@Route(path = RouteUtils.MODULE_USER_ACTIVITY_ACCOUNT_INFO)
public class ModuleUserAccountInfoActivity extends BaseActivity<ModuleUserActivityAccountInfoBinding> implements View.OnClickListener {

    @Override
    public int getContentView() {
        return R.layout.module_user_activity_account_info;
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

    @Override
    public void onClick(View v) {
        RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_UPDATE_PASSWORD);
    }
}
