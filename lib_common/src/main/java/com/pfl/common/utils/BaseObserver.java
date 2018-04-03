package com.pfl.common.utils;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by rocky on 2018/1/2.
 */

public class BaseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T httpResponse) {

    }

    @Override
    public void onError(Throwable e) {
        Utils.init(App.getInstance());
        ToastUtils.showShort(e.getMessage());
    }

    @Override
    public void onComplete() {

    }
}
