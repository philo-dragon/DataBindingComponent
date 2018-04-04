package com.pfl.common.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.view.View;

import com.pfl.common.R;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.App;
import com.pfl.common.utils.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.yan.inflaterauto.InflaterAuto;

/**
 * Created by rocky on 2018/4/2.
 */

public abstract class BaseActivity<T> extends RxAppCompatActivity {

    protected T mBinding;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(InflaterAuto.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        componentInject(App.getInstance(BaseApplication.class).getAppComponent());
        initViews();
    }

    private void setContentView() {
        if (isSupportDataBinding()) {
            mBinding = (T) DataBindingUtil.setContentView(this, getContentView());
        } else {
            setContentView(getContentView());
        }
    }

    private boolean isSupportDataBinding() {
        return true;
    }

    protected abstract int getContentView();

    protected abstract void componentInject(AppComponent appComponent);

    protected abstract void initViews();

}
