package com.pfl.module_user.activity;

import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.constant.UserInfoManager;
import com.pfl.module_user.databinding.ModuleUserActivitySettingBinding;
import com.pfl.module_user.di.module_setting.DaggerSettingComponent;
import com.pfl.module_user.di.module_setting.SettingModule;
import com.pfl.module_user.view.SettingView;
import com.pfl.module_user.viewmodel.SettingViewModel;

import javax.inject.Inject;

@Route(path = RouteUtils.MODULE_USER_ACTIVITY_SETTING)
public class ModuleUserSettingActivity extends BaseActivity<ModuleUserActivitySettingBinding> implements SettingView, View.OnClickListener {

    @Inject
    SettingViewModel settingViewModel;


    @Override
    public int getContentView() {
        return R.layout.module_user_activity_setting;
    }

    @Override
    public void componentInject(AppComponent appComponent) {
        DaggerSettingComponent.builder()
                .appComponent(appComponent)
                .settingModule(new SettingModule(this, this))
                .build()
                .inject(this);
    }

    @Override
    public void initView() {

        boolean flag = UserInfoManager.getInstance() == null;
        mBinding.tvAccountInfo.setVisibility(flag ? View.GONE : View.VISIBLE);
        mBinding.line.setVisibility(flag ? View.GONE : View.VISIBLE);


    }

    @Override
    public void setToolBar() {
        setToolBarHasBack(mBinding.inToolbarLayout.titleBar);
    }

    @Override
    public void initData() {
        settingViewModel.requestData();
    }

    @Override
    public void onSuccess(String token) {

        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_ACCOUNT_INFO);
    }
}
