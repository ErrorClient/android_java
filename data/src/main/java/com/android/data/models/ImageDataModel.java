package com.android.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ImageDataModel {
    @PrimaryKey
    @NonNull
    public String id = "";
    public String url = "";
}
