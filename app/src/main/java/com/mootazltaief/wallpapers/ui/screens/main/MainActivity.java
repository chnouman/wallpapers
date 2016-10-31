package com.mootazltaief.wallpapers.ui.screens.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mootazltaief.wallpapers.R;
import com.mootazltaief.wallpapers.data.models.Wallpaper;
import com.mootazltaief.wallpapers.ui.base.BaseActivity;
import com.mootazltaief.wallpapers.ui.screens.favorites.FavoritesActivity;
import com.mootazltaief.wallpapers.ui.screens.single.SingleWallpaperActivity;
import com.mootazltaief.wallpapers.utils.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView, RecyclerViewClickListener {



    @Inject
    MainPresenter mPresenter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.wallpapers_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.content_main)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.empty_view_container)
    LinearLayout mEmptyViewContainer;
    private WallpapersAdapter mAdapter;
    private List<Wallpaper> mWallpapers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        activityComponent().inject(this);
        mPresenter.attachView(this);
        setSupportActionBar(mToolbar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new WallpapersAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
        mWallpapers = new ArrayList<>();
        mAdapter.setWallpaperList(mWallpapers);
        mPresenter.getWallpapers();
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.getWallpapers());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorites) {
            startActivity(new Intent(this, FavoritesActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addWallpapers(List<Wallpaper> wallpapers) {
        mWallpapers.addAll(wallpapers);
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
        mEmptyViewContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showEmpty() {
        mEmptyViewContainer.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(int error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(boolean show) {
        if (show)
            mProgress.setVisibility(View.VISIBLE);
        else
            mProgress.setVisibility(View.INVISIBLE);

    }

    @Override
    public void recyclerViewListClicked(View v, int position, int id) {
        Wallpaper wallpaper = mWallpapers.get(position);
        Intent intent = new Intent(this, SingleWallpaperActivity.class);
        intent.putExtra("wallpaper", wallpaper);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
