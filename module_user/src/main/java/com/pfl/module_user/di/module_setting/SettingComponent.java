package com.pfl.module_user.di.module_setting;

import com.pfl.common.di.AppComponent;
import com.pfl.common.di.scope.ActivityScope;
import com.pfl.module_user.activity.ModuleUserSettingActivity;

import dagger.Component;

/**
 * Created by rocky on 2018/1/2.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = SettingModule.class)
public interface SettingComponent {

    void inject(ModuleUserSettingActivity activity);
}
