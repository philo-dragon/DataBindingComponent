package com.pfl.module_user;


import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.imageloader.ImageLoader;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.databinding.ModuleUserActivityLoginBinding;
import com.pfl.module_user.di.module_login.DaggerLoginComponent;
import com.pfl.module_user.di.module_login.LoginModule;
import com.pfl.module_user.mvp.login.LoginPersenter;
import com.pfl.module_user.mvp.login.LoginView;
import com.pfl.module_user.po.ModuleUserPoUser;

import javax.inject.Inject;


@Route(path = RouteUtils.MODULE_USER_LOGIN_ACTIVITY)
public class ModuleUserLoginActivity extends BaseActivity<ModuleUserActivityLoginBinding> implements LoginView {

    @Inject
    ImageLoader imageLoader;
    @Inject
    LoginPersenter persenter;
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
                mBinding.btnLogin.onStart();
                RouteUtils.actionStart(RouteUtils.MODULE_USER_REGIST_ACTIVITY);
                Toast.makeText(ModuleUserLoginActivity.this.getApplicationContext(), user.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setToolBar() {
        setToolBarHasBack(mBinding.inToolbarLayout.titleBar);
    }

    @Override
    public void initData() {
        persenter.requestData();
    }

    @Override
    public void onSuccess(String token) {
        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.btnLogin.onStop();
    }
}
