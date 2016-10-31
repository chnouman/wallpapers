package com.mootazltaief.wallpapers.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Wallpaper implements Parcelable {

    @Id
    private long id;
    private String thumb;
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.thumb);
        dest.writeString(this.url);
    }

    private Wallpaper(Parcel in) {
        this.id = in.readLong();
        this.thumb = in.readString();
        this.url = in.readString();
    }

    @Generated(hash = 1483774132)
    public Wallpaper(long id, String thumb, String url) {
        this.id = id;
        this.thumb = thumb;
        this.url = url;
    }

    @Generated(hash = 1395429522)
    public Wallpaper() {
    }

    public static final Parcelable.Creator<Wallpaper> CREATOR = new Parcelable.Creator<Wallpaper>() {
        @Override
        public Wallpaper createFromParcel(Parcel source) {
            return new Wallpaper(source);
        }

        @Override
        public Wallpaper[] newArray(int size) {
            return new Wallpaper[size];
        }
    };
}
