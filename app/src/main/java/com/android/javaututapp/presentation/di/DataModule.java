package com.android.javaututapp.presentation.di;

import com.android.data.repository.ImageRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    @Provides
    @Singleton
    public ImageRepository provideImageRepository() {
        return ImageRepository.get();
    }
}
