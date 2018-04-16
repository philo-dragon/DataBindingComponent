package com.pfl.module_user.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;
import com.pfl.common.utils.RxClickUtil;
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
        RxClickUtil.RxClick(mBinding.rlPhone, this);
        RxClickUtil.RxClick(mBinding.rlUserName, this);
        RxClickUtil.RxClick(mBinding.rlSingnatrue, this);
        RxClickUtil.RxClick(mBinding.rlLoginPassword, this);
        RxClickUtil.RxClick(mBinding.rlRechargePassword, this);
        RxClickUtil.RxClick(mBinding.rlPhone, this);
        RxClickUtil.RxClick(mBinding.rlWechat, this);
        RxClickUtil.RxClick(mBinding.rlQq, this);
        RxClickUtil.RxClick(mBinding.rlSina, this);
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
        int i = v.getId();
        if (i == R.id.rl_photo) {
        } else if (i == R.id.rl_user_name) {
        } else if (i == R.id.rl_singnatrue) {
        } else if (i == R.id.rl_login_password) {
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_UPDATE_PASSWORD);
        } else if (i == R.id.rl_recharge_password) {
        } else if (i == R.id.rl_phone) {
        } else if (i == R.id.rl_wechat) {
        } else if (i == R.id.rl_qq) {
        } else if (i == R.id.rl_sina) {
        }
    }
}
