package com.mootazltaief.wallpapers.ui.screens.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mootazltaief.wallpapers.R;
import com.mootazltaief.wallpapers.data.models.Wallpaper;
import com.mootazltaief.wallpapers.utils.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WallpapersAdapter extends RecyclerView.Adapter<WallpapersAdapter.MyViewHolder> {

    private List<Wallpaper> mWallpaperList;
    private final Context mContext;
    private final RecyclerViewClickListener mListener;

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mImageView;

        public MyViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.wallpaper);
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.recyclerViewListClicked(view, this.getLayoutPosition(), view.getId());
        }
    }


    public WallpapersAdapter(Context context, RecyclerViewClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mWallpaperList = new ArrayList<>();
    }

    public void setWallpaperList(List<Wallpaper> wallpapers) {
        this.mWallpaperList = wallpapers;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wallpaper, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Wallpaper wallpaper = mWallpaperList.get(position);
        Picasso.with(mContext).load(wallpaper.getThumb()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mWallpaperList.size();
    }
}