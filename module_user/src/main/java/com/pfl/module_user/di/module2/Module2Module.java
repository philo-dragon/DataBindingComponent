package com.pfl.module_user.di.module2;

import com.pfl.common.http.RetrofitService;
import com.pfl.module_user.mvp.module2.Module2Persenter;
import com.pfl.module_user.mvp.module2.Module2View;
import com.trello.rxlifecycle2.LifecycleProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rocky on 2018/1/2.
 */

@Module
public class Module2Module {

    private LifecycleProvider lifecycle;
    private Module2View view;

    public Module2Module(LifecycleProvider lifecycle, Module2View module2View) {
        this.lifecycle = lifecycle;
        this.view = module2View;
    }

    @Provides
    Module2View provideModule2View() {
        return view;
    }

    @Provides
    LifecycleProvider provideLifecycleProvider() {
        return lifecycle;
    }

    @Provides
    Module2Persenter provideModule2Persenter(LifecycleProvider lifecycle, RetrofitService service, Module2View view) {

        return new Module2Persenter(lifecycle, service, view);
    }

}
