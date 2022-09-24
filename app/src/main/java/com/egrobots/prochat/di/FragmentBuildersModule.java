package com.egrobots.prochat.di;

import com.egrobots.prochat.di.modules.AuthenticationViewModelModule;
import com.egrobots.prochat.di.modules.UserProfileViewModelModule;
import com.egrobots.prochat.presentation.dialogs.authentication.ForgotPasswordBottomSheetDialog;
import com.egrobots.prochat.presentation.dialogs.authentication.LoginBottomSheetDialog;
import com.egrobots.prochat.presentation.dialogs.authentication.SignUpBottomSheetDialog;
import com.egrobots.prochat.presentation.dialogs.authentication.VerifyAccountBottomSheetDialog;
import com.egrobots.prochat.presentation.user.UserProfileContentFragment;
import com.egrobots.prochat.presentation.user.groups.GroupFragment;
import com.egrobots.prochat.viewmodels.UserProfileViewModel;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    abstract UserProfileContentFragment contributeSurveyQuestion1Fragment();

    abstract GroupFragment contributeGroupFragment();

    @ContributesAndroidInjector(modules = AuthenticationViewModelModule.class)
    abstract SignUpBottomSheetDialog contributeSignUpBottomSheetDialogFragment();

    @ContributesAndroidInjector(modules = AuthenticationViewModelModule.class)
    abstract VerifyAccountBottomSheetDialog contributeVerifyAccountBottomSheetDialog();

    @ContributesAndroidInjector(modules = AuthenticationViewModelModule.class)
    abstract LoginBottomSheetDialog contributeLoginBottomSheetDialog();

    @ContributesAndroidInjector(modules = AuthenticationViewModelModule.class)
    abstract ForgotPasswordBottomSheetDialog contributeForgotPasswordBottomSheetDialog();

    @ContributesAndroidInjector(modules = UserProfileViewModelModule.class)
    abstract UserProfileContentFragment contributeUserProfileContentFragment();

}
