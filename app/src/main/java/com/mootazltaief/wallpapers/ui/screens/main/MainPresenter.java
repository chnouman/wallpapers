package com.mootazltaief.wallpapers.ui.screens.main;

import com.mootazltaief.wallpapers.R;
import com.mootazltaief.wallpapers.data.models.Wallpaper;
import com.mootazltaief.wallpapers.data.remote.ApiService;
import com.mootazltaief.wallpapers.ui.base.BasePresenter;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainMvpView> {
    private final ApiService mApiService;

    @Inject
    public MainPresenter(ApiService apiService) {
        mApiService = apiService;
    }

    void getWallpapers() {
        getMvpView().showLoading(true);
        addSubscription(getRequestObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber()));
    }

    Observable<List<Wallpaper>> getRequestObservable() {
        return Observable.defer(() -> mApiService.getWallpapers())
                .retryWhen(observable -> observable.flatMap(o -> {
                    if (o instanceof IOException) {
                        return Observable.just(null);
                    }
                    return Observable.error(o);
                }));
    }

    private Subscriber<List<Wallpaper>> getSubscriber() {
        return new Subscriber<List<Wallpaper>>() {
            @Override
            public void onCompleted() {
                //no-op
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showEmpty();
                getMvpView().showLoading(false);
                getMvpView().showError(R.string.error_message);
            }

            @Override
            public void onNext(List<Wallpaper> wallpapers) {
                if (wallpapers.isEmpty()) {
                    getMvpView().showEmpty();
                } else {
                    getMvpView().addWallpapers(wallpapers);
                }
                getMvpView().showLoading(false);
            }
        };
    }
}
