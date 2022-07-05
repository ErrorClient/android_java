package com.android.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.android.data.models.ImageDataModel;

import java.util.List;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM imageDataModel")
    LiveData<List<ImageDataModel>> getAllImages();

    @Query("SELECT * FROM imagedatamodel WHERE id = (:id)")
    LiveData<ImageDataModel> getImage(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addImage(ImageDataModel imageModel);
}
