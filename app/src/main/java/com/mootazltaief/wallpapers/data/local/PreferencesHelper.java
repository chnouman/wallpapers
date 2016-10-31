package com.mootazltaief.wallpapers.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.mootazltaief.wallpapers.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Preference helper class.
 */
@Singleton
public class PreferencesHelper {
    public static final String AUTH_TOKEN = "token";
    private static final String PREF_FILE_NAME = "prefs";
    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Clear preferences data
     */
    public void clear() {
        mPref.edit().clear().apply();
    }


    /**
     * Set a long value in the preferences
     * @param key
     * @param value
     */
    public void setLong(String key, long value) {
        mPref.edit()
                .putLong(key, value)
                .apply();
    }

    /**
     * Set a string value in the preferences
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        mPref.edit()
                .putString(key, value)
                .apply();
    }

    /**
     * Set a bool value in the preferences
     * @param key
     * @param value
     */
    public void setBoolean(String key, boolean value) {
        mPref.edit()
                .putBoolean(key, value)
                .apply();
    }

    /**
     * Get a long value in the preferences
     * @param key
     * @return
     */
    public long getLong(String key) {
        return mPref.getLong(key, 0);
    }

    /**
     * Get a string value in the preferences
     * @param key
     * @return
     */
    public String getString(String key) {
        return mPref.getString(key, null);
    }

    /**
     * get a bool value in the preferences
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        return mPref.getBoolean(key, false);
    }

}
