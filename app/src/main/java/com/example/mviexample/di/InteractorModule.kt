package com.example.mviexample.di

import com.example.mviexample.domain.LoadUsersInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class InteractorModule {

    @Binds
    abstract fun bindLoadUsersInteractor(
        impl: LoadUsersInteractorImpl
    ): LoadUsersInteractor
}