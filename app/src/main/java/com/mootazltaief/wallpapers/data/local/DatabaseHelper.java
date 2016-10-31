package com.mootazltaief.wallpapers.data.local;

import com.mootazltaief.wallpapers.data.models.DaoSession;
import com.mootazltaief.wallpapers.data.models.Wallpaper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DatabaseHelper {


    private final DaoSession mDaoSession;

    @Inject
    public DatabaseHelper(DaoSession daoSession) {
        this.mDaoSession = daoSession;
    }

    public boolean addWallpaper(Wallpaper wallpaper) {
        long result = mDaoSession.getWallpaperDao().insert(wallpaper);
        return result > 0;
    }

    public void deleteWallpaper(Wallpaper wallpaper) {
        mDaoSession.getWallpaperDao().delete(wallpaper);
    }

    public boolean checkIfFavorite(Wallpaper wallpaper) {
        Wallpaper result = mDaoSession.getWallpaperDao().load(wallpaper.getId());
        return result != null;
    }

    public List<Wallpaper> getFavorites() {
        return mDaoSession.getWallpaperDao().loadAll();
    }

}
