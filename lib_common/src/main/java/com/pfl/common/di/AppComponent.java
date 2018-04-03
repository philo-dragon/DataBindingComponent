package com.pfl.common.di;

import android.app.Application;

import com.pfl.common.di.scope.FragmentScope;
import com.pfl.common.http.RetrofitService;
import com.pfl.common.imageloader.ImageLoader;
import com.pfl.common.utils.AppConfig;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by mertsimsek on 13/01/17.
 */

@Singleton
@Component(modules = {NetworkModule.class, AppModule.class})
public interface AppComponent {

    Application getApplication();

    RetrofitService getRetrofitService();

    ImageLoader getImageLoader();

    AppConfig getAppConfig();

}