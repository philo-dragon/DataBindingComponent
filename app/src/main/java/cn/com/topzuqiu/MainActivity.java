package cn.com.topzuqiu;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;

import cn.com.topzuqiu.databinding.ActivityMainBinding;

@Route(path = RouteUtils.APP_MAIN_ACTIVITY)
public class MainActivity extends BaseActivity<ActivityMainBinding> {


    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

    }

    @Override
    public void initView() {
        mBinding.tvGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteUtils.actionStart(RouteUtils.MODULE_USER_LOGIN_ACTIVITY);
            }
        });
    }

    @Override
    public void setToolBar() {
        setToolBarNoBack(mBinding.inToolbarLayout.titleBar);
    }

    @Override
    public void initData() {

    }

}
