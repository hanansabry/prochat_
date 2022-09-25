package com.egrobots.prochat.di;


import com.egrobots.prochat.di.modules.AuthenticationViewModelModule;
import com.egrobots.prochat.di.modules.UserProfileViewModelModule;
import com.egrobots.prochat.presentation.screens.authentication.ForgotPasswordActivity;
import com.egrobots.prochat.presentation.screens.authentication.LoginActivity;
import com.egrobots.prochat.presentation.screens.authentication.SignUpActivity;
import com.egrobots.prochat.presentation.screens.authentication.VerifyAccount;
import com.egrobots.prochat.presentation.screens.userprofile.UserProfileActivity;
import com.egrobots.prochat.presentation.screens.userprofile.groups.GroupFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = AuthenticationViewModelModule.class)
    abstract LoginActivity contributeSignInActivity();

    @ContributesAndroidInjector(modules = AuthenticationViewModelModule.class)
    abstract SignUpActivity contributeSignUpActivity();

    @ContributesAndroidInjector(modules = AuthenticationViewModelModule.class)
    abstract VerifyAccount contributeVerifyAccount();

    @ContributesAndroidInjector(modules = AuthenticationViewModelModule.class)
    abstract ForgotPasswordActivity contributeForgotPasswordActivity();

    @ContributesAndroidInjector(modules = UserProfileViewModelModule.class)
    abstract UserProfileActivity contributeUserProfileActivity();

    @ContributesAndroidInjector(modules = UserProfileViewModelModule.class)
    abstract GroupFragment contributeGroupFragment();
}
