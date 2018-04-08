package com.pfl.module_user.di.module_regist;

import com.pfl.common.di.AppComponent;
import com.pfl.common.di.scope.ActivityScope;
import com.pfl.module_user.ModuleUserRegistActivity;

import dagger.Component;

/**
 * Created by rocky on 2018/1/2.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = RegistModule.class)
public interface RegistComponent {

    void inject(ModuleUserRegistActivity activity);
}
