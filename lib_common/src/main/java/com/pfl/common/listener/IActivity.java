package com.pfl.common.listener;

import android.view.View;

import com.pfl.common.di.AppComponent;

/**
 * Created by rocky on 2018/1/3.
 */

/**
 * Activity公共函数接口
 * 按方法书写顺序执行
 */
public interface IActivity {


    int getContextView();

    void componentInject(AppComponent appComponent);

    void initView(View view);

    void initEvent();

    void initData();

}
