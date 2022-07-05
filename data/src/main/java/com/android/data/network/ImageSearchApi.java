package com.android.data.network;

import com.android.data.models.ImageApiModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ImageSearchApi {
    @GET("/v1/images/search")
    Call<List<ImageApiModel>> getImage();
}
