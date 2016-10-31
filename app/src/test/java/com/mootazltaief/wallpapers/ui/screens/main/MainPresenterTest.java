package com.mootazltaief.wallpapers.ui.screens.main;

import com.mootazltaief.wallpapers.TestDataFactory;
import com.mootazltaief.wallpapers.data.models.Wallpaper;
import com.mootazltaief.wallpapers.data.remote.ApiService;
import com.mootazltaief.wallpapers.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.observers.TestSubscriber;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    ApiService mMockApiService;

    @Mock
    Scheduler mMockIoSchduler;

    @Mock
    Scheduler mMockMainSchduler;

    @Mock
    MainMvpView mMockView;

    private MainPresenter mMainPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mMainPresenter = new MainPresenter(mMockApiService);
        mMainPresenter.attachView(mMockView);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void getWallpaper_200OkResponse_InvokesCorrectApiCalls() {
        //Given
        when(mMockApiService.getWallpapers()).
                thenReturn(Observable.just(TestDataFactory.makeListWallpaper(2)));

        //When
        TestSubscriber<List<Wallpaper>> subscriber = new TestSubscriber<>();
        mMainPresenter.getRequestObservable().subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<List<Wallpaper>> onNextEvents = subscriber.getOnNextEvents();
        List<Wallpaper> users = onNextEvents.get(0);
        Assert.assertEquals((long) 1, users.get(0).getId());
        Assert.assertEquals((long) 2, users.get(1).getId());
    }

    @Test
    public void getWallpaper_IOExceptionThenSuccess_SearchUsersRetried() {
        //Given
        when(mMockApiService.getWallpapers())
                .thenReturn(getIOExceptionError(), Observable.just(TestDataFactory.makeListWallpaper(2)));

        //When
        TestSubscriber<List<Wallpaper>> subscriber = new TestSubscriber<>();
        mMainPresenter.getRequestObservable().subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        verify(mMockApiService, times(2)).getWallpapers();
    }

    @Test
    public void getWallpaper_ReturnsResults() {
        //Given
        List<Wallpaper> wallpapers = TestDataFactory.makeListWallpaper(2);
        when(mMockApiService.getWallpapers())
                .thenReturn(Observable.just(wallpapers));

        //When
        mMainPresenter.getWallpapers();

        verify(mMockView).showLoading(true);
        verify(mMockView).addWallpapers(wallpapers);
        verify(mMockView).showLoading(false);
        verify(mMockView, never()).showError(anyInt());
    }

    @Test
    public void getWallpaper_ReturnsError() {
        String errorMsg = "No internet";
        when(mMockApiService.getWallpapers()).thenReturn(Observable.error(new Throwable(errorMsg)));

        mMainPresenter.getWallpapers();

        verify(mMockView).showLoading(true);
        verify(mMockView).showLoading(false);
        verify(mMockView).showEmpty();
        verify(mMockView).showError(anyInt());
    }


    private Observable getIOExceptionError() {
        return Observable.error(new IOException());
    }
}