package com.mootazltaief.wallpapers;

import com.mootazltaief.wallpapers.data.models.Wallpaper;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
public class TestDataFactory {


    public static Wallpaper makeWallpaper(long id) {
        Wallpaper wallpaper = new Wallpaper();
        wallpaper.setId(id);
        wallpaper.setThumb("http://mootaz-ltaief.com/wallpapers/thumb_img" + id + ".jpg");
        wallpaper.setUrl("http://mootaz-ltaief.com/wallpapers/img" + id + ".jpg");
        return wallpaper;
    }

    public static List<Wallpaper> makeListWallpaper(int number) {
        List<Wallpaper> wallpapers = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            wallpapers.add(makeWallpaper(i));
        }
        return wallpapers;
    }


}