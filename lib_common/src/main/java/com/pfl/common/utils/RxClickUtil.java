package com.pfl.common.utils;

import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * Created by rocky on 2018/4/16.
 */

public class RxClickUtil {

    public static void RxClick(final View view, final View.OnClickListener listener) {

        RxView.clicks(view).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                listener.onClick(view);
            }
        });
    }

}
