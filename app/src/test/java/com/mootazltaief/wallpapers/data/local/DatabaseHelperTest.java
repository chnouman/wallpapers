package com.mootazltaief.wallpapers.data.local;

import com.mootazltaief.wallpapers.BuildConfig;
import com.mootazltaief.wallpapers.TestDataFactory;
import com.mootazltaief.wallpapers.data.models.DaoMaster;
import com.mootazltaief.wallpapers.data.models.DaoSession;
import com.mootazltaief.wallpapers.data.models.Wallpaper;
import com.mootazltaief.wallpapers.util.DefaultConfig;

import org.greenrobot.greendao.database.Database;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class DatabaseHelperTest {
    private DatabaseHelper mDatabaseHelper;

    @Before
    public void setUp() throws Exception {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(RuntimeEnvironment.application, "wallpapers-db");
        Database database = devOpenHelper.getWritableDb();
        DaoSession daoSession = new DaoMaster(database).newSession();
        mDatabaseHelper = new DatabaseHelper(daoSession);
    }

    @Test
    public void addWallpaper() throws Exception {
        Wallpaper wallpaper = TestDataFactory.makeWallpaper(1);
        boolean result = mDatabaseHelper.addWallpaper(wallpaper);
        assertTrue(result);
    }

    @Test
    public void getFavorites() throws Exception {
        for (int i = 1; i <= 5; i++) {
            Wallpaper wallpaper = TestDataFactory.makeWallpaper(i);
            mDatabaseHelper.addWallpaper(wallpaper);
        }
        List<Wallpaper> wallpapers = mDatabaseHelper.getFavorites();
        assertEquals(5, wallpapers.size());
    }

    @Test
    public void deleteWallpaper() throws Exception {
        Wallpaper wallpaper1 = TestDataFactory.makeWallpaper(6);
        Wallpaper wallpaper2 = TestDataFactory.makeWallpaper(7);
        mDatabaseHelper.addWallpaper(wallpaper1);
        mDatabaseHelper.addWallpaper(wallpaper2);
        List<Wallpaper> wallpapers = mDatabaseHelper.getFavorites();
        assertEquals(2, wallpapers.size());
        mDatabaseHelper.deleteWallpaper(wallpaper2);
        wallpapers = mDatabaseHelper.getFavorites();
        assertEquals(1, wallpapers.size());
    }

    @Test
    public void checkIfFavorite() throws Exception {
        Wallpaper wallpaper = TestDataFactory.makeWallpaper(1);
        mDatabaseHelper.addWallpaper(wallpaper);
        boolean result = mDatabaseHelper.checkIfFavorite(wallpaper);
        assertTrue(result);
    }

    @Test
    public void checkIfNotFavorite() throws Exception {
        Wallpaper wallpaper = TestDataFactory.makeWallpaper(15);
        boolean result = mDatabaseHelper.checkIfFavorite(wallpaper);
        assertFalse(result);
    }
}