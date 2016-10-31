package com.mootazltaief.wallpapers.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mootazltaief.wallpapers.data.models.Wallpaper;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

public interface ApiService {

    String ENDPOINT = "http://mootaz-ltaief.com/";

    @GET("images.json")
    Observable<List<Wallpaper>> getWallpapers();

    /********
     * Helper class that sets up a new services
     *******/
    class Creator {

        public static ApiService newApiService() {
            Gson gson = new GsonBuilder().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(ApiService.class);
        }
    }
}
