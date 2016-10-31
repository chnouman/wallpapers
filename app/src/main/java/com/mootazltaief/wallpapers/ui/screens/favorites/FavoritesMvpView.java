package com.mootazltaief.wallpapers.ui.screens.favorites;

import com.mootazltaief.wallpapers.data.models.Wallpaper;
import com.mootazltaief.wallpapers.ui.base.MvpView;

import java.util.List;

interface FavoritesMvpView extends MvpView {

    void showWallpapers(List<Wallpaper> wallpapers);

    void showEmpty();
}
