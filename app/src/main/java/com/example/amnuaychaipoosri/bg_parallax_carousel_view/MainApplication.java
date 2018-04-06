package com.example.amnuaychaipoosri.bg_parallax_carousel_view;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.example.amnuaychaipoosri.bg_parallax_carousel_view.manager.Contextor;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Contextor.getInstance().init(getApplicationContext());

    }

}
