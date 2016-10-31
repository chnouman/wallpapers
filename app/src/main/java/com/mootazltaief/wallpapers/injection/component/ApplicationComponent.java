package com.mootazltaief.wallpapers.injection.component;

import android.app.Application;
import android.content.Context;

import com.mootazltaief.wallpapers.data.local.DatabaseHelper;
import com.mootazltaief.wallpapers.data.local.PreferencesHelper;
import com.mootazltaief.wallpapers.data.remote.ApiService;
import com.mootazltaief.wallpapers.injection.ApplicationContext;
import com.mootazltaief.wallpapers.injection.module.ApplicationModule;
import com.mootazltaief.wallpapers.utils.RxEventBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    ApiService api();

    PreferencesHelper preferencesHelper();

    DatabaseHelper databaseHelper();

    RxEventBus eventBus();

}
