package com.smartdroidesign.retrofitoauth2;

import android.app.Application;

import com.smartdroidesign.retrofitoauth2.api.OAuthUtil;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // ensure the shared pref is initialized with the Global Context
        OAuthUtil.initSharedPref(this);
    }
}
