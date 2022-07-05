package com.android.javaututapp.presentation.vm;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.data.models.ImageDataModel;
import com.android.data.models.StatusLoading;
import com.android.data.repository.ImageRepository;
import com.android.domain.domain.usecase.LeftToLoadUseCase;
import com.android.domain.domain.usecase.PixelsToDpUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ImageViewModel extends ViewModel {

    ImageRepository imageRepository;
    public LiveData<List<ImageDataModel>> imageListLiveData;
    public LiveData<StatusLoading> status;


    @Inject
    ImageViewModel(
            ImageRepository imageRepository
    ) {
        this.imageRepository = imageRepository;
        imageListLiveData = imageRepository.getAllImages();
        status = imageRepository.getStatus();
    }

    private int screenSize = 0;
    private int countHorizontal = 0;
    private int countVertical = 0;

    public int getCountHorizontal() {
        return countHorizontal;
    }
    public int getCountVertical() {
        return countVertical;
    }

    public void addImage(int adapterSize) {
        int leftToLoad = LeftToLoadUseCase.execute(adapterSize, screenSize, countHorizontal);
        imageRepository.addImage(leftToLoad);
    }

    public void getSize(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int heightPixels = displayMetrics.heightPixels;
        int widthPixels = displayMetrics.widthPixels;
        float density = displayMetrics.density;

        countVertical = PixelsToDpUseCase.execute(heightPixels, density) / 100;
        countHorizontal = PixelsToDpUseCase.execute(widthPixels, density) / 100;
        screenSize = countVertical * countVertical;
    }
}
