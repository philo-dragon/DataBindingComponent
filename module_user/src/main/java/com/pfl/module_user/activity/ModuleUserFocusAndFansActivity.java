package com.pfl.module_user.activity;

import android.view.View;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserActivityFocusAndFansBinding;

@Route(path = RouteUtils.MODULE_USER_ACTIVITY_FOCUS_AND_FANS)
public class ModuleUserFocusAndFansActivity extends BaseActivity<ModuleUserActivityFocusAndFansBinding> {

    @Override
    public int getContentView() {
        return R.layout.module_user_activity_focus_and_fans;
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

        mBinding.rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtn_my_focus) {

                } else {

                }
            }
        });
    }

    @Override
    public void initData() {

    }
}
