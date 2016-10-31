package com.mootazltaief.wallpapers.injection.component;

import com.mootazltaief.wallpapers.injection.PerActivity;
import com.mootazltaief.wallpapers.injection.module.ActivityModule;
import com.mootazltaief.wallpapers.ui.screens.favorites.FavoritesActivity;
import com.mootazltaief.wallpapers.ui.screens.main.MainActivity;
import com.mootazltaief.wallpapers.ui.screens.single.SingleWallpaperActivity;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);

    void inject(SingleWallpaperActivity activity);

    void inject(FavoritesActivity activity);
}
