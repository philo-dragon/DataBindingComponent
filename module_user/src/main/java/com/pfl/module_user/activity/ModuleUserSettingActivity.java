package com.pfl.module_user.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.CacheUtils;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.App;
import com.pfl.common.utils.BaseUrlManager;
import com.pfl.common.utils.RouteUtils;
import com.pfl.common.utils.RxClickUtil;
import com.pfl.module_user.R;
import com.pfl.module_user.constant.UserInfoManager;
import com.pfl.module_user.databinding.ModuleUserActivitySettingBinding;
import com.pfl.module_user.di.module_setting.DaggerSettingComponent;
import com.pfl.module_user.di.module_setting.SettingModule;
import com.pfl.module_user.view.SettingView;
import com.pfl.module_user.viewmodel.SettingViewModel;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * 设置界面
 */
@Route(path = RouteUtils.MODULE_USER_ACTIVITY_SETTING)
public class ModuleUserSettingActivity extends BaseActivity<ModuleUserActivitySettingBinding> implements SettingView, View.OnClickListener {

    @Inject
    SettingViewModel settingViewModel;


    @Override
    public int getContentView() {
        return R.layout.module_user_activity_setting;
    }

    @Override
    public void componentInject(AppComponent appComponent) {
        DaggerSettingComponent.builder()
                .appComponent(appComponent)
                .settingModule(new SettingModule(this, this))
                .build()
                .inject(this);
    }

    @Override
    public void initView() {

        boolean flag = UserInfoManager.getInstance() == null;
        mBinding.tvAccountInfo.setVisibility(flag ? View.GONE : View.VISIBLE);
        mBinding.line.setVisibility(flag ? View.GONE : View.VISIBLE);

        RxClickUtil.RxClick(mBinding.tvAbout, this);
        RxClickUtil.RxClick(mBinding.tvAccountInfo, this);
        RxClickUtil.RxClick(mBinding.tvClearCache, this);
        RxClickUtil.RxClick(mBinding.tvMzsm, this);
        RxClickUtil.RxClick(mBinding.tvPf, this);
        RxClickUtil.RxClick(mBinding.tvTkys, this);
        RxClickUtil.RxClick(mBinding.rlHistoryVersion, this);
    }

    @Override
    public void setToolBar() {
        setToolBarHasBack(mBinding.inToolbarLayout.titleBar);
    }

    @Override
    public void initData() {
        settingViewModel.requestData();
    }

    @Override
    public void onSuccess(String token) {

        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.tv_account_info) {
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_ACCOUNT_INFO);
        } else if (i == R.id.rl_clear_cache) {
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_CHANNEL_MANAGER);
        } else if (i == R.id.tv_pf) {
            launchAppDetail();
        } else if (i == R.id.tv_tkys) {
            HashMap<String, String> parameter = new HashMap<>();
            parameter.put("title", "使用条款与隐私政策");
            parameter.put("url", BaseUrlManager.getBaseUrl() + "help/privacy");
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_H5, parameter);
        } else if (i == R.id.tv_mzsm) {
            HashMap<String, String> parameter = new HashMap<>();
            parameter.put("title", "免责声明");
            parameter.put("url", BaseUrlManager.getBaseUrl() + "help/proclaim");
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_H5, parameter);
        } else if (i == R.id.tv_about) {
            HashMap<String, String> parameter = new HashMap<>();
            parameter.put("title", "关于我们");
            parameter.put("url", BaseUrlManager.getBaseUrl() + "help/aboutme");
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_H5, parameter);
        } else if (i == R.id.rl_history_version) {
        } else {
        }

    }

    /**
     * 跳转到应用市场app详情界面
     */
    public void launchAppDetail() {
        try {
            try {
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(App.getInstance(), "您的手机没有安装Android应用市场", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
