package com.pfl.module_user.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserActivityHomePageBinding;

/**
 * 用户主页
 */
@Route(path = RouteUtils.MODULE_USER_ACTIVITY_HOME_PAGE)
public class ModuleUserHomePageActivity extends BaseActivity<ModuleUserActivityHomePageBinding> {

    @Override
    public int getContentView() {
        return R.layout.module_user_activity_home_page;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

    }

    @Override
    public void initView() {
        setToolBarHasBack(mBinding.inToolbarLayout.titleBar);
    }

    @Override
    public void setToolBar() {

    }

    @Override
    public void initData() {

    }
}
