package com.pfl.common.di;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pfl.common.converter.StringConverter;
import com.pfl.common.imageloader.BaseImageLoaderStrategy;
import com.pfl.common.imageloader.ImageLoader;
import com.pfl.common.imageloader.glide.GlideImageLoaderStrategy;
import com.pfl.common.utils.AppConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by rocky on 2017/12/28.
 */

@Module
public class AppModule {

    private Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(String.class, new StringConverter());//把Null转换成""字符串
        return builder.create();
    }

    @Provides
    @Singleton
    BaseImageLoaderStrategy provideBaseImageLoaderStrategy() {
        return new GlideImageLoaderStrategy();
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader(BaseImageLoaderStrategy imageLoaderStrategy) {
        return new ImageLoader(imageLoaderStrategy);
    }

    @Provides
    @Singleton
    AppConfig provideAppConfig() {
        return new AppConfig.Builder()
                .build();
    }

}
