package com.pfl.module_user.di.module_login;

import com.pfl.common.http.RetrofitService;
import com.pfl.module_user.mvp.login.LoginPersenter;
import com.pfl.module_user.mvp.login.LoginView;
import com.pfl.module_user.po.ModuleUserPoUser;
import com.trello.rxlifecycle2.LifecycleProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rocky on 2018/1/2.
 */

@Module
public class LoginModule {

    private LifecycleProvider lifecycle;
    private LoginView view;

    public LoginModule(LifecycleProvider lifecycle, LoginView module2View) {
        this.lifecycle = lifecycle;
        this.view = module2View;
    }

    @Provides
    LoginView provideModule2View() {
        return view;
    }

    @Provides
    LifecycleProvider provideLifecycleProvider() {
        return lifecycle;
    }

    @Provides
    LoginPersenter provideLoginPersenter(LifecycleProvider lifecycle, RetrofitService service, LoginView view) {

        return new LoginPersenter(lifecycle, service, view);
    }

    @Provides
    ModuleUserPoUser provideUser() {
        return new ModuleUserPoUser();
    }

}
