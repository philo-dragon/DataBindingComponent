package com.pfl.module_user.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;
import com.pfl.common.utils.RxClickUtil;
import com.pfl.common.weidget.atlas.AtlasLayout;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserActivityMyWalletBinding;

/**
 * 我的钱包
 */
@Route(path = RouteUtils.MODULE_USER_ACTIVITY_MY_WALLET)
public class ModuleUserMyWalletActivity extends BaseActivity<ModuleUserActivityMyWalletBinding> implements View.OnClickListener {

    private AtlasLayout atlasLayout;

    @Override
    public int getContentView() {
        return R.layout.module_user_activity_my_wallet;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

    }

    @Override
    public void initView() {

        RxClickUtil.RxClick(mBinding.btn, this);
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
        atlasLayout = AtlasLayout.Builder.with(this).create();
        atlasLayout.showAtlas();
    }

    @Override
    public void onBackPressed() {
        if (!atlasLayout.handleBackPressed()) {
            super.onBackPressed();
        }
    }
}
