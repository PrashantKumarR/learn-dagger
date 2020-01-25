package com.example.myapplication.di;

import com.example.myapplication.di.auth.AuthModule;
import com.example.myapplication.di.auth.AuthViewModelModule;
import com.example.myapplication.ui.auth.AuthActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelModule.class,
                    AuthModule.class
            }
    )
    abstract AuthActivity contributeAuthActivity();

}
