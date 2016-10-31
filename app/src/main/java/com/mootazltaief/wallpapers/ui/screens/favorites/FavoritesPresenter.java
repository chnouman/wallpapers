package com.mootazltaief.wallpapers.ui.screens.favorites;

import com.mootazltaief.wallpapers.data.local.DatabaseHelper;
import com.mootazltaief.wallpapers.data.models.Wallpaper;
import com.mootazltaief.wallpapers.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

public class FavoritesPresenter extends BasePresenter<FavoritesMvpView> {
    private final DatabaseHelper mDatabaseHelper;

    @Inject
    public FavoritesPresenter(DatabaseHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }


    public void getWallpapers() {
        List<Wallpaper> wallpapers = mDatabaseHelper.getFavorites();
        if (wallpapers == null || wallpapers.isEmpty()) {
            getMvpView().showEmpty();
        } else {
            getMvpView().showWallpapers(wallpapers);
        }

    }
}
