package com.pfl.module_user.di.module_login;

import com.pfl.common.http.RetrofitService;
import com.pfl.module_user.po.ModuleUserPoUser;
import com.pfl.module_user.view.LoginView;
import com.pfl.module_user.viewmodel.LoginViewModel;
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

    public LoginModule(LifecycleProvider lifecycle, LoginView loginView) {
        this.lifecycle = lifecycle;
        this.view = loginView;
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
    LoginViewModel provideLoginViewModel(LifecycleProvider lifecycle, RetrofitService service, LoginView view) {

        return new LoginViewModel(lifecycle, service, view);
    }

    @Provides
    ModuleUserPoUser provideUser() {
        return new ModuleUserPoUser();
    }

}
