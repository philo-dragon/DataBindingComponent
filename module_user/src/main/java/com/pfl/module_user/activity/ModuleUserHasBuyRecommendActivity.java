package com.pfl.module_user.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserActivityHasBuyRecommendBinding;

/**
 * 购买记录
 */
@Route(path = RouteUtils.MODULE_USER_ACTIVITY_HAS_BUY_RECOMMEND)
public class ModuleUserHasBuyRecommendActivity extends BaseActivity<ModuleUserActivityHasBuyRecommendBinding> {


    @Override
    public int getContentView() {
        return R.layout.module_user_activity_has_buy_recommend;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void setToolBar() {
        mBinding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }
}
