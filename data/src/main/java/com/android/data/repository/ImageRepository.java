package com.android.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.android.data.database.ImageDao;
import com.android.data.database.ImageDatabase;
import com.android.data.models.ImageDataModel;
import com.android.data.models.StatusLoading;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageRepository {
    private static final String DATABASE_NAME = "image-database";
    private ExecutorService executor;

    private Context context;
    private ImageDatabase database;
    private ImageDao imageDao;
    private AddImageToDataBase addImageToDataBase;

    ImageRepository(Context context) {
        this.context = context;

        database = Room.databaseBuilder(
                context.getApplicationContext(),
                ImageDatabase.class,
                DATABASE_NAME
        ).build();

        imageDao = database.imageDao();

        executor = Executors.newSingleThreadExecutor();

        addImageToDataBase = new AddImageToDataBase(imageDao, context);
    }

    private MutableLiveData<StatusLoading> status = new MutableLiveData<StatusLoading>(StatusLoading.Success);
    public LiveData<StatusLoading> getStatus() {
        return status;
    }

    public LiveData<List<ImageDataModel>> getAllImages() {
        return imageDao.getAllImages();
    }

    public void addImage(int leftToLoad) {

        class RunnableImpl implements Runnable {

            @Override
            public void run() {
                status.postValue(StatusLoading.Loading);

                try {
                    for (int i = 1; i <= leftToLoad; i++) {
                        addImageToDataBase.execute();
                        Log.d("AAA", "addImageToDataBase execute + " + i);
                    }

                    status.postValue(StatusLoading.Success);
                } catch (Throwable t) {
                    status.postValue(StatusLoading.Error);
                }
            }
        }

        if (status.getValue() != StatusLoading.Loading) {
            Runnable runnable = new RunnableImpl();

            executor.execute(runnable);
        }
    }

    private static ImageRepository INSTANCE = null;

    public static void initialize(@NonNull Context context) {
        if (INSTANCE == null) INSTANCE = new ImageRepository(context);
    }

    public static ImageRepository get() {
        if (INSTANCE == null) throw new IllegalStateException("ImageRepository must be initialized");
        else return INSTANCE;
    }
}
