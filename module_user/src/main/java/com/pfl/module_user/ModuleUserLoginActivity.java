package com.pfl.module_user;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.imageloader.ImageLoader;
import com.pfl.common.imageloader.glide.ImageConfigImpl;
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

    @Override
    protected int getContentView() {
        return R.layout.module_user_activity_login;
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerModule2Component
                .builder()
                .appComponent(appComponent)
                .module2Module(new Module2Module(this, this))
                .build()
                .inject(this);

    }

    @Override
    protected void initViews() {
        mBinding.inToolbarLayout.toolbar.setTitle("登录界面");
        imageLoader.loadImage(this, ImageConfigImpl.
                builder().url("http://g.hiphotos.baidu.com/image/pic/item/c8ea15ce36d3d539f09733493187e950342ab095.jpg").
                imageView(mBinding.imgUser).
                build());

        persenter.requestData();

    }

    @Override
    public void onSuccess(String token) {
        mBinding.tvToken.setText(token);
    }
}
