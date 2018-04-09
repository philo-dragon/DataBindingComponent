package cn.com.topzuqiu;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.entity.module_user.User;
import com.pfl.common.service.ModuleUserRouteService;
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
                RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_LOGIN);
            }
        });
    }

    private void setUserInfo() {
        User userInfo = ModuleUserRouteService.getUserInfo();
        mBinding.tvGoLogin.setText(userInfo == null ? "1111111111" : userInfo.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserInfo();
    }

    @Override
    public void setToolBar() {
        setToolBarNoBack(mBinding.inToolbarLayout.titleBar);
    }

    @Override
    public void initData() {

    }

}
