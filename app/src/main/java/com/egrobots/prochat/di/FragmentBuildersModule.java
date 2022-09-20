package com.egrobots.prochat.di;

import com.egrobots.prochat.presentation.user.UserProfileContentFragment;
import com.egrobots.prochat.presentation.user.groups.GroupFragment;

import dagger.Module;

@Module
public abstract class FragmentBuildersModule {

    abstract UserProfileContentFragment contributeSurveyQuestion1Fragment();

    abstract GroupFragment contributeGroupFragment();
}
