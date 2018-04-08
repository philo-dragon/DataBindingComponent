package com.pfl.module_user;

import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.imageloader.ImageLoader;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.databinding.ModuleUserActivityRegistBinding;
import com.pfl.module_user.di.module_regist.DaggerRegistComponent;
import com.pfl.module_user.di.module_regist.RegistModule;
import com.pfl.module_user.mvp.regist.RegistPersenter;
import com.pfl.module_user.mvp.regist.RegistView;
import com.pfl.module_user.po.ModuleUserPoUser;

import javax.inject.Inject;

@Route(path = RouteUtils.MODULE_USER_REGIST_ACTIVITY)
public class ModuleUserRegistActivity extends BaseActivity<ModuleUserActivityRegistBinding> implements RegistView {

    @Inject
    ImageLoader imageLoader;
    @Inject
    RegistPersenter persenter;
    @Inject
    ModuleUserPoUser user;

    @Override
    public int getContentView() {
        return R.layout.module_user_activity_regist;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

        DaggerRegistComponent
                .builder()
                .appComponent(appComponent)
                .registModule(new RegistModule(this, this))
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
                Toast.makeText(ModuleUserRegistActivity.this.getApplicationContext(), user.toString(), Toast.LENGTH_SHORT).show();
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

        mBinding.btnRegist.onStop();
        mBinding.btnCheckCode.onStop();

    }
}