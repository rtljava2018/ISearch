package com.rkm.itunessearch;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

public class ItuneApp extends Application {
    public static Application sApplication;
    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        sApplication = this;

        //storageService = new StorageService(this);
        //NetworkIndentity.init(this);


    }
}
