package com.pfl.module_user.di.module_regist;

import com.pfl.common.http.RetrofitService;
import com.pfl.module_user.mvp.regist.Module2Persenter;
import com.pfl.module_user.mvp.regist.Module2View;
import com.pfl.module_user.mvp.regist.RegistPersenter;
import com.pfl.module_user.mvp.regist.RegistView;
import com.pfl.module_user.po.ModuleUserPoUser;
import com.trello.rxlifecycle2.LifecycleProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rocky on 2018/1/2.
 */

@Module
public class RegistModule {

    private LifecycleProvider lifecycle;
    private RegistView view;

    public RegistModule(LifecycleProvider lifecycle, RegistView module2View) {
        this.lifecycle = lifecycle;
        this.view = module2View;
    }

    @Provides
    RegistView provideRegistView() {
        return view;
    }

    @Provides
    LifecycleProvider provideLifecycleProvider() {
        return lifecycle;
    }

    @Provides
    RegistPersenter provideModule2Persenter(LifecycleProvider lifecycle, RetrofitService service, RegistView view) {

        return new RegistPersenter(lifecycle, service, view);
    }

    @Provides
    ModuleUserPoUser provideUser() {
        return new ModuleUserPoUser();
    }

}
