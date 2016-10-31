package com.mootazltaief.wallpapers.ui.screens.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.mootazltaief.wallpapers.R;
import com.mootazltaief.wallpapers.data.models.Wallpaper;
import com.mootazltaief.wallpapers.ui.base.BaseActivity;
import com.mootazltaief.wallpapers.ui.screens.main.WallpapersAdapter;
import com.mootazltaief.wallpapers.ui.screens.single.SingleWallpaperActivity;
import com.mootazltaief.wallpapers.utils.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends BaseActivity implements FavoritesMvpView, RecyclerViewClickListener {

    @Inject
    FavoritesPresenter mPresenter;
    @BindView(R.id.wallpapers_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view_container)
    LinearLayout mEmptyViewContainer;

    private WallpapersAdapter mAdapter;
    private List<Wallpaper> mWallpapers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        activityComponent().inject(this);
        mPresenter.attachView(this);
        mWallpapers = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new WallpapersAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setWallpaperList(mWallpapers);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getWallpapers();
    }

    @Override
    public void showWallpapers(List<Wallpaper> wallpapers) {
        mWallpapers.clear();
        mWallpapers.addAll(wallpapers);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmpty() {
        mEmptyViewContainer.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void recyclerViewListClicked(View v, int position, int id) {
        Wallpaper wallpaper = mWallpapers.get(position);
        Intent intent = new Intent(this, SingleWallpaperActivity.class);
        intent.putExtra("wallpaper", wallpaper);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
