package com.android.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.data.models.ImageDataModel;

@Database(entities = {ImageDataModel.class}, version = 1)
public abstract class ImageDatabase extends RoomDatabase {

    public abstract ImageDao imageDao();
}
