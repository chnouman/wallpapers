package com.mootazltaief.wallpapers.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {
    public static void saveFile(Bitmap bitmap, String imageDir, String imageName, String tag) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/" + imageDir);
        myDir.mkdirs();

        String fileName = imageName + ".jpg";

        File file = new File(myDir, fileName);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
