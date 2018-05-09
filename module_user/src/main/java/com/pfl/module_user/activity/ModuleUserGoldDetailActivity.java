package com.pfl.module_user.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.dialog.BaseBottomSheetDialog;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserActivityGoldDetailBinding;
import com.pfl.module_user.dialog.ModuleUserExitAppDialog;

/**
 * 金币明细
 */
@Route(path = RouteUtils.MODULE_USER_ACTIVITY_GOLD_DETAIL)
public class ModuleUserGoldDetailActivity extends BaseActivity<ModuleUserActivityGoldDetailBinding> {


    @Override
    public int getContentView() {
        return R.layout.module_user_activity_gold_detail;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

    }

    @Override
    public void initView() {
        mBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    @Override
    public void setToolBar() {
        setToolBarHasBack(mBinding.inToolbarLayout.titleBar);
    }

    @Override
    public void initData() {

    }

    private void showDialog() {
        //ModuleUserExitAppDialog dialog = new ModuleUserExitAppDialog();
        //dialog.show(getSupportFragmentManager(),ModuleUserExitAppDialog.class.getSimpleName());

        BaseBottomSheetDialog dialog = new BaseBottomSheetDialog(this);
        dialog.setContentView(R.layout.bottom_sheet);
        dialog.show();

    }

}
