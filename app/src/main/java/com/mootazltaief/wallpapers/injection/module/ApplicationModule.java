package com.mootazltaief.wallpapers.injection.module;

import android.app.Application;
import android.content.Context;

import com.mootazltaief.wallpapers.data.models.DaoMaster;
import com.mootazltaief.wallpapers.data.models.DaoSession;
import com.mootazltaief.wallpapers.data.remote.ApiService;
import com.mootazltaief.wallpapers.injection.ApplicationContext;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    ApiService provideApiService() {
        return ApiService.Creator.newApiService();
    }

    @Provides
    DaoMaster.DevOpenHelper providesOpenHelper(@ApplicationContext Context context) {
        return new DaoMaster.DevOpenHelper(context, "wallpapers-db");
    }

    @Provides
    Database providesDatabase(DaoMaster.DevOpenHelper devOpenHelper) {
        return devOpenHelper.getWritableDb();
    }

    @Provides
    @Singleton
    DaoSession providesDaoSession(Database database) {
        return new DaoMaster(database).newSession();
    }
}
