package com.android.javaututapp.presentation.app;

import android.app.Application;

import com.android.data.repository.ImageRepository;

import dagger.hilt.android.HiltAndroidApp;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltAndroidApp
public class ImageApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ImageRepository.initialize(this);
    }
}
