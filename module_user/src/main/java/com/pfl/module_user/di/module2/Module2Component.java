package com.pfl.module_user.di.module2;

import com.pfl.common.di.AppComponent;
import com.pfl.common.di.scope.ActivityScope;
import com.pfl.module_user.ModuleUserLoginActivity;

import dagger.Component;

/**
 * Created by rocky on 2018/1/2.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = Module2Module.class)
public interface Module2Component {

    void inject(ModuleUserLoginActivity activity);
}
