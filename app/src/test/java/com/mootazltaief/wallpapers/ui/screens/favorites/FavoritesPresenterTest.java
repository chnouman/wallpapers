package com.mootazltaief.wallpapers.ui.screens.favorites;

import com.mootazltaief.wallpapers.TestDataFactory;
import com.mootazltaief.wallpapers.data.local.DatabaseHelper;
import com.mootazltaief.wallpapers.data.models.Wallpaper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FavoritesPresenterTest {

    @Mock
    DatabaseHelper mMockDatabaseHelper;

    @Mock
    FavoritesMvpView mMockView;

    private FavoritesPresenter mFavoritesPresenter;

    @Before
    public void setUp() throws Exception {
        mFavoritesPresenter = new FavoritesPresenter(mMockDatabaseHelper);
        mFavoritesPresenter.attachView(mMockView);
    }

    @After
    public void tearDown() {
        mFavoritesPresenter.detachView();
    }

    @Test
    public void getWallpaper_returnResult() {
        List<Wallpaper> wallpapers = TestDataFactory.makeListWallpaper(2);
        when(mMockDatabaseHelper.getFavorites())
                .thenReturn(wallpapers);

        mFavoritesPresenter.getWallpapers();
        verify(mMockView).showWallpapers(wallpapers);

    }

    @Test
    public void getWallpaper_returnEmpty() {
        List<Wallpaper> wallpapers = new ArrayList<>();
        when(mMockDatabaseHelper.getFavorites())
                .thenReturn(wallpapers);

        mFavoritesPresenter.getWallpapers();
        verify(mMockView).showEmpty();
    }

}