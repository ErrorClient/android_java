package com.android.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.android.data.database.ImageDao;
import com.android.data.models.ImageApiModel;
import com.android.data.models.ImageDataModel;
import com.android.data.network.RetrofitInstance;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Response;

public class AddImageToDataBase {
    private Context context;
    private ImageDao imageDao;
    AddImageToDataBase(ImageDao imageDao, Context context) {
        this.context = context;
        this.imageDao = imageDao;
    }

    public void execute() {
        ImageDataModel image = new ImageDataModel();
        LiveData<ImageDataModel> existImageModel = null;

        /***
         * Если в базе данных уже есть такая картинка - грузим другую
         */

        image = loadImage();
//        while (image.id.equals("") || existImageModel != null) {
//            image = loadImage();
//            try {
//                existImageModel = imageDao.getImage(image.id);
//            } catch (Throwable t) {
//                existImageModel = null;}
//        }

        imageDao.addImage(image);
    }

    private ImageDataModel loadImage() {
        InternetAvailable isInternetAvailable = new InternetAvailable(context);

        if (!isInternetAvailable.execute()) {
            throw new IllegalStateException("Internet is not available");
        }

        ImageDataModel image = new ImageDataModel();
        Response<List<ImageApiModel>> responseImage = null;
        try {
            responseImage = RetrofitInstance.imageSearchApi.getImage().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Objects.requireNonNull(responseImage).isSuccessful()) {
            image.id = Objects.requireNonNull(responseImage.body()).get(0).id;
            image.url = responseImage.body().get(0).url;
        }

        return image;
    }
}
