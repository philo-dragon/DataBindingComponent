package com.pfl.module_user;


import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.entity.module_user.User;
import com.pfl.common.imageloader.ImageLoader;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.databinding.ModuleUserActivityLoginBinding;
import com.pfl.module_user.di.module2.DaggerModule2Component;
import com.pfl.module_user.di.module2.Module2Module;
import com.pfl.module_user.mvp.module2.Module2Persenter;
import com.pfl.module_user.mvp.module2.Module2View;

import javax.inject.Inject;


@Route(path = RouteUtils.MODULE_USER_LOGIN_ACTIVITY)
public class ModuleUserLoginActivity extends BaseActivity<ModuleUserActivityLoginBinding> implements Module2View {

    @Inject
    ImageLoader imageLoader;
    @Inject
    Module2Persenter persenter;
    @Inject
    User user;

    @Override
    public int getContentView() {
        return R.layout.module_user_activity_login;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

        DaggerModule2Component
                .builder()
                .appComponent(appComponent)
                .module2Module(new Module2Module(this, this))
                .build()
                .inject(this);

    }

    @Override
    public void initView() {
        /*imageLoader.loadImage(this, ImageConfigImpl.
                builder().url("http://g.hiphotos.baidu.com/image/pic/item/c8ea15ce36d3d539f09733493187e950342ab095.jpg").
                imageView(mBinding.imgUser).
                build());*/

        mBinding.setUser(user);
        mBinding.btnCheckCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.btnCheckCode.onStart();
            }
        });
        mBinding.btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.btnRegist.onStart();
                Toast.makeText(ModuleUserLoginActivity.this.getApplicationContext(), user.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setToolBar() {
        setToolBarHasBack(mBinding.inToolbarLayout.titleBar, "注册");
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

        mBinding.btnRegist.onStop();
        mBinding.btnCheckCode.onStop();

    }
}
