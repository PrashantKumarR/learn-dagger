package com.example.myapplication.di.auth;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.di.ViewModelKey;
import com.example.myapplication.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel authViewModel);
}
