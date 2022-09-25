package com.egrobots.prochat.di;

import com.egrobots.prochat.di.modules.AuthenticationViewModelModule;
import com.egrobots.prochat.di.modules.UserProfileViewModelModule;
import com.egrobots.prochat.presentation.screens.authentication.dialogs.ForgotPasswordBottomSheetDialog;
import com.egrobots.prochat.presentation.screens.authentication.dialogs.LoginBottomSheetDialog;
import com.egrobots.prochat.presentation.screens.authentication.dialogs.SignUpBottomSheetDialog;
import com.egrobots.prochat.presentation.screens.authentication.dialogs.VerifyAccountBottomSheetDialog;
import com.egrobots.prochat.presentation.screens.userprofile.UserProfileContentFragment;
import com.egrobots.prochat.presentation.screens.userprofile.groups.GroupChatsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    abstract UserProfileContentFragment contributeSurveyQuestion1Fragment();

    abstract GroupChatsFragment contributeGroupFragment();

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
