package com.pfl.module_user.di.module_setting;

import com.pfl.common.http.RetrofitService;
import com.pfl.module_user.po.ModuleUserPoUser;
import com.pfl.module_user.view.RegistView;
import com.pfl.module_user.view.SettingView;
import com.pfl.module_user.viewmodel.RegistViewModel;
import com.pfl.module_user.viewmodel.SettingViewModel;
import com.trello.rxlifecycle2.LifecycleProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rocky on 2018/1/2.
 */

@Module
public class SettingModule {

    private LifecycleProvider lifecycle;
    private SettingView view;

    public SettingModule(LifecycleProvider lifecycle, SettingView registView) {
        this.lifecycle = lifecycle;
        this.view = registView;
    }

    @Provides
    SettingView provideSettingView() {
        return view;
    }

    @Provides
    LifecycleProvider provideLifecycleProvider() {
        return lifecycle;
    }

    @Provides
    SettingViewModel provideRegistViewModel(LifecycleProvider lifecycle, RetrofitService service, SettingView view) {

        return new SettingViewModel(lifecycle, service, view);
    }

    @Provides
    ModuleUserPoUser provideUser() {
        return new ModuleUserPoUser();
    }

}
