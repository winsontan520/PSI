package com.winsontan520.psi;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Winson Tan on 16/4/18.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

}
