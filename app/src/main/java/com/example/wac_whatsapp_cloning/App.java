package com.example.wac_whatsapp_cloning;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("QGkWKaG5pLI5wuDiPbmFj5GAsbpjHLHSDh0iltaT")
                // if defined
                .clientKey("L5YQKwdTrbvhnmYA30kyltMvysmQQMEOYzqTPvWP")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }
}
