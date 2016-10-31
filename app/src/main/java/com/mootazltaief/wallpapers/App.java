package com.mootazltaief.wallpapers;

import android.app.Application;
import android.content.Context;

import com.mootazltaief.wallpapers.injection.component.ApplicationComponent;
import com.mootazltaief.wallpapers.injection.component.DaggerApplicationComponent;
import com.mootazltaief.wallpapers.injection.module.ApplicationModule;

/**
 * Application class
 */
public class App extends Application {

    private ApplicationComponent mApplicationComponent;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
