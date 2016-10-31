package com.mootazltaief.wallpapers.ui.screens.main;

import com.mootazltaief.wallpapers.data.models.Wallpaper;
import com.mootazltaief.wallpapers.ui.base.MvpView;

import java.util.List;

public interface MainMvpView extends MvpView {
    void addWallpapers(List<Wallpaper> wallpapers);
    void showEmpty();
    void showError(int error);
    void showLoading(boolean show);
}
