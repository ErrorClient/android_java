package com.android.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitInstance {
    private static final String BASE_URL = "https://api.thecatapi.com";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build();

    public static ImageSearchApi imageSearchApi = retrofit.create(ImageSearchApi.class);
}
