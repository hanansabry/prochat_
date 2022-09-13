package com.egrobots.prochat.di;


import com.egrobots.prochat.di.modules.AuthenticationViewModelModule;
import com.egrobots.prochat.presentation.authentication.LoginActivity;
import com.egrobots.prochat.presentation.authentication.SignUpActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = AuthenticationViewModelModule.class)
    abstract LoginActivity contributeSignInActivity();

    @ContributesAndroidInjector(modules = AuthenticationViewModelModule.class)
    abstract SignUpActivity contributeSignUpActivity();
}
