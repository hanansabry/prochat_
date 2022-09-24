package com.egrobots.prochat.di.modules;

import com.egrobots.prochat.di.ViewModelKey;
import com.egrobots.prochat.viewmodels.AuthenticationViewModel;
import com.egrobots.prochat.viewmodels.UserProfileViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class UserProfileViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel.class)
    public abstract ViewModel bindViewModel(UserProfileViewModel viewModel);
}
