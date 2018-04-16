package com.pfl.module_user.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserActivityMyMesageBinding;

/**
 * 我的消息
 */
@Route(path = RouteUtils.MODULE_USER_ACTIVITY_MY_MESSAGE)
public class ModuleUserMyMesageActivity extends BaseActivity<ModuleUserActivityMyMesageBinding> {


    @Override
    public int getContentView() {
        return R.layout.module_user_activity_my_mesage;
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
