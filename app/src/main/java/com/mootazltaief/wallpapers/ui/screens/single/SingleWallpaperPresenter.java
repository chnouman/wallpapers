package com.mootazltaief.wallpapers.ui.screens.single;

import android.support.annotation.NonNull;

import com.mootazltaief.wallpapers.data.local.DatabaseHelper;
import com.mootazltaief.wallpapers.data.models.Wallpaper;
import com.mootazltaief.wallpapers.ui.base.BasePresenter;

import javax.inject.Inject;

public class SingleWallpaperPresenter extends BasePresenter<SingleMvpView> {

    private final DatabaseHelper mDatabaseHelper;

    @Inject
    public SingleWallpaperPresenter(DatabaseHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    public void addToFavorites(@NonNull Wallpaper wallpaper) {
        if (mDatabaseHelper.checkIfFavorite(wallpaper)) {
            mDatabaseHelper.deleteWallpaper(wallpaper);
            getMvpView().wallpaperNotInFavorite();
        } else {
            mDatabaseHelper.addWallpaper(wallpaper);
            getMvpView().wallpaperInFavorite();
        }
    }

    public void checkInFavorites(@NonNull Wallpaper wallpaper) {
        if (mDatabaseHelper.checkIfFavorite(wallpaper)) {
            getMvpView().wallpaperInFavorite();
        } else {
            getMvpView().wallpaperNotInFavorite();
        }
    }
}
