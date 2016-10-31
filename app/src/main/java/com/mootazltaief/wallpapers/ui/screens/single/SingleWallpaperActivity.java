package com.mootazltaief.wallpapers.ui.screens.single;

import android.Manifest;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.kayvannj.permission_utils.Func;
import com.github.kayvannj.permission_utils.PermissionUtil;
import com.mootazltaief.wallpapers.R;
import com.mootazltaief.wallpapers.data.models.Wallpaper;
import com.mootazltaief.wallpapers.ui.base.BaseActivity;
import com.mootazltaief.wallpapers.utils.FileUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class SingleWallpaperActivity extends BaseActivity implements SingleMvpView {

    @Inject
    SingleWallpaperPresenter mPresenter;
    @BindView(R.id.iv_photo)
    PhotoView mWallpaperHolder;
    @BindView(R.id.fab_wallpaper)
    FloatingActionButton mFabWallpaper;
    @BindView(R.id.fab_download)
    FloatingActionButton mFabDownload;
    @BindView(R.id.fab_bookmark)
    FloatingActionButton fabBookmark;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.menu_fab)
    FloatingActionMenu mMenuFab;

    private boolean mBitmapAvailable = false;
    private Wallpaper mWallpaper;
    private PhotoViewAttacher mPhotoViewAttacher;
    private CompositeSubscription mSubscriptions;
    private PermissionUtil.PermissionRequestObject mPermissionRequest;
    private final int REQUEST_CODE_STORAGE = 101;

    private final static String TAG = "SingleWallpaperActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_single_wallpaper);
        ButterKnife.bind(this);

        activityComponent().inject(this);
        mPresenter.attachView(this);

        mSubscriptions = new CompositeSubscription();

        if (savedInstanceState != null && savedInstanceState.getParcelable("wallpaper") != null) {
            mWallpaper = savedInstanceState.getParcelable("wallpaper");
        } else {
            mWallpaper = getIntent().getParcelableExtra("wallpaper");
        }

        if (mWallpaper != null) {
            mPresenter.checkInFavorites(mWallpaper);
        }

        showWallpaper();

        mFabWallpaper.setOnClickListener(view -> {
            mProgress.setVisibility(View.VISIBLE);
            if (mBitmapAvailable) {
                mSubscriptions.add(Observable.create(new Observable.OnSubscribe<Boolean>() {
                    @Override
                    public void call(Subscriber<? super Boolean> subscriber) {
                        WallpaperManager myWallpaperManager
                                = WallpaperManager.getInstance(getApplicationContext());
                        try {
                            myWallpaperManager.setBitmap(mWallpaperHolder.getVisibleRectangleBitmap());
                            subscriber.onNext(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                            subscriber.onError(e);
                        }
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getDefaultSubscriber()));
            }
        });

        mFabDownload.setOnClickListener(view -> {
            if (mBitmapAvailable) {
                mPermissionRequest = PermissionUtil.with(SingleWallpaperActivity.this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).onAllGranted(
                        new Func() {
                            @Override
                            protected void call() {
                                saveWallpaper();
                            }
                        }).onAnyDenied(
                        new Func() {
                            @Override
                            protected void call() {
                                //no-op
                            }
                        }).ask(REQUEST_CODE_STORAGE);
            }
        });

        fabBookmark.setOnClickListener(view -> mPresenter.addToFavorites(mWallpaper));
    }

    private Subscriber<Boolean> getDefaultSubscriber() {
        return new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                //no-op
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mProgress.setVisibility(View.INVISIBLE);
                mMenuFab.close(true);
            }

            @Override
            public void onNext(Boolean aBoolean) {
                mProgress.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Wallpaper set", Toast.LENGTH_SHORT).show();
                mMenuFab.close(true);
            }
        };
    }

    private void showWallpaper() {
        if (mWallpaper != null) {
            Picasso.with(this)
                    .load(mWallpaper.getUrl())
                    .into(mWallpaperHolder, new Callback() {
                        @Override
                        public void onSuccess() {
                            mBitmapAvailable = true;
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                mPhotoViewAttacher = new PhotoViewAttacher(mWallpaperHolder);
                                mPhotoViewAttacher.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                mProgress.setVisibility(View.INVISIBLE);
                            }, 10);
                        }

                        @Override
                        public void onError() {
                            Log.e(TAG, "Error loading image");
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("wallpaper", mWallpaper);
        super.onSaveInstanceState(outState);
    }

    private void saveWallpaper() {
        Picasso.with(getApplicationContext()).
                load(mWallpaper.getUrl())
                .into(picassoImageTarget("WP", mWallpaper.getId() + ""));
        Toast.makeText(this, "Wallpaper saved :)", Toast.LENGTH_SHORT).show();
    }

    private Target picassoImageTarget(final String imageDir, final String imageName) {
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                FileUtils.saveFile(bitmap, imageDir, imageName, TAG);
                mMenuFab.close(true);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                //no-op
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                //no-op
            }
        };
    }

    @Override
    public void wallpaperNotInFavorite() {
        fabBookmark.setImageResource(R.drawable.ic_bookmark_empty);
    }

    @Override
    public void wallpaperInFavorite() {
        fabBookmark.setImageResource(R.drawable.ic_bookmark);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
