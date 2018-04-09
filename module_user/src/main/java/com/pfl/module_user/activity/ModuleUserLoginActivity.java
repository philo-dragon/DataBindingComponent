package com.pfl.module_user.activity;


import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.entity.module_user.User;
import com.pfl.common.imageloader.ImageLoader;
import com.pfl.common.utils.App;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.constant.UserInfoManager;
import com.pfl.module_user.databinding.ModuleUserActivityLoginBinding;
import com.pfl.module_user.di.module_login.DaggerLoginComponent;
import com.pfl.module_user.di.module_login.LoginModule;
import com.pfl.module_user.po.ModuleUserPoUser;
import com.pfl.module_user.view.LoginView;
import com.pfl.module_user.viewmodel.LoginViewModel;

import javax.inject.Inject;


@Route(path = RouteUtils.MODULE_USER_LOGIN_ACTIVITY)
public class ModuleUserLoginActivity extends BaseActivity<ModuleUserActivityLoginBinding> implements LoginView {

    @Inject
    ImageLoader imageLoader;
    @Inject
    LoginViewModel loginViewModel;
    @Inject
    ModuleUserPoUser user;

    @Override
    public int getContentView() {
        return R.layout.module_user_activity_login;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this, this))
                .build()
                .inject(this);

    }

    @Override
    public void initView() {

        mBinding.setUser(user);
        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User();
                user.setName("潘飞龙");
                user.setNickName("philo");
                user.setPhoto("http://g.hiphotos.baidu.com/image/pic/item/c8ea15ce36d3d539f09733493187e950342ab095.jpg");
                user.setType("1");
                UserInfoManager.getInstance().setUser(user);

                mBinding.btnLogin.onStart();
                Toast.makeText(ModuleUserLoginActivity.this.getApplicationContext(), user.toString(), Toast.LENGTH_SHORT).show();
                RouteUtils.actionStart(RouteUtils.MODULE_USER_REGIST_ACTIVITY);

            }
        });
    }

    @Override
    public void setToolBar() {
        setToolBarHasBack(mBinding.inToolbarLayout.titleBar);
    }

    @Override
    public void initData() {
        loginViewModel.requestData();
    }

    @Override
    public void onSuccess(String token) {
        Toast.makeText(App.getInstance(), token, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.btnLogin.onStop();
    }
}
