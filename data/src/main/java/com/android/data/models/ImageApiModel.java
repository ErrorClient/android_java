package com.android.data.models;

import com.squareup.moshi.Json;

public class ImageApiModel {
    @Json(name = "id") public String id;
    @Json(name = "url") public String url;
}
