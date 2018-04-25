package com.pfl.common.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.pfl.common.R;
import com.pfl.common.listener.IActivity;
import com.pfl.common.utils.App;
import com.pfl.common.utils.StatusBarUtil;
import com.pfl.common.weidget.TitleBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.yan.inflaterauto.InflaterAuto;

/**
 * Created by rocky on 2018/4/2.
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends RxAppCompatActivity implements IActivity {

    protected T mBinding;
    protected Activity mContext;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(InflaterAuto.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.mContext = this;
        super.onCreate(savedInstanceState);
        setContentView();
        drakMode();
        ARouter.getInstance().inject(this);
        componentInject(App.getInstance(BaseApplication.class).getAppComponent());
        initView();
        setToolBar();
        initData();
    }

    private void setContentView() {
        if (isSupportDataBinding()) {
            mBinding = DataBindingUtil.setContentView(this, getContentView());
        } else {
            setContentView(getContentView());
        }
    }

    private void drakMode() {
        if (isDrakMode()) {
            StatusBarUtil.darkMode(this, true);
        } else {
            StatusBarUtil.darkMode(this, false);
        }
    }

    private boolean isDrakMode() {
        return true;
    }

    protected void setToolBarHasBack(TitleBar titleBar) {
        titleBar.setTitle(getTitle());
        titleBar.setTitleColor(getResources().getColor(R.color.title_color));
        titleBar.setLeftText("返回");
        titleBar.setLeftImageResource(R.drawable.common_left_back_arror_selector);
        titleBar.setLeftTextColor(getResources().getColor(R.color.title_color));
        titleBar.setDividerColor(getResources().getColor(R.color.title_divider_color));
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setToolBarNoBack(TitleBar titleBar) {
        titleBar.setTitle(getTitle());
        titleBar.setDividerColor(getResources().getColor(R.color.title_divider_color));
        titleBar.setTitleColor(getResources().getColor(R.color.title_color));
    }

    private boolean isSupportDataBinding() {
        return true;
    }

}
