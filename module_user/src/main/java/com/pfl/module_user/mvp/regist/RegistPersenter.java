package com.pfl.module_user.mvp.regist;

import com.pfl.common.entity.base.AccessToken;
import com.pfl.common.http.RetrofitFactory;
import com.pfl.common.http.RetrofitService;
import com.pfl.common.http.RxSchedulers;
import com.pfl.common.utils.BaseObserver;
import com.pfl.module_user.mvp.regist.Module2View;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

/**
 * Created by rocky on 2018/1/2.
 */

public class RegistPersenter {

    private LifecycleProvider lifecycle;
    private RetrofitService service;
    private RegistView view;

    @Inject
    public RegistPersenter(LifecycleProvider lifecycle, RetrofitService service, RegistView view) {
        this.lifecycle = lifecycle;
        this.service = service;
        this.view = view;
    }

    public void requestData() {
        RetrofitFactory.getInstance()
                .getProxy(RetrofitService.class, service, service)
                .getToken("client_credentials", "282307895618", "b9c6c8f954dbbf7274910585a95efce1")
                .compose(RxSchedulers.<AccessToken>compose())
                .compose(lifecycle.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<AccessToken>() {
                    @Override
                    public void onNext(AccessToken accessToken) {
                        view.onSuccess(accessToken.getAccess_token());
                    }
                });
    }
}
