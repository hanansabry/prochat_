package com.egrobots.prochat.presentation;

import android.content.Context;

import com.egrobots.prochat.di.DaggerAppComponent;

import androidx.multidex.MultiDex;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseActivity extends DaggerApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
