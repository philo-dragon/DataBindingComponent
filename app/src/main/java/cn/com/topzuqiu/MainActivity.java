package cn.com.topzuqiu;

import android.graphics.Color;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;

import cn.com.topzuqiu.databinding.ActivityMainBinding;

@Route(path = RouteUtils.APP_MAIN_ACTIVITY)
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initViews() {
        mBinding.inToolbarLayout.titleBar.setTitleColor(Color.WHITE);
        mBinding.inToolbarLayout.titleBar.setTitle("主界面");
        mBinding.tvGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteUtils.actionStart(RouteUtils.MODULE_USER_LOGIN_ACTIVITY);
            }
        });
    }
}
