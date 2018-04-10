package com.pfl.module_user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;

@Route(path = RouteUtils.MODULE_USER_ACTIVITY_TEST)
public class ModuleUserTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_user_activity_test);
    }
}
