package com.example.nalex.mybakingapp.utils;

import com.example.nalex.mybakingapp.model.Search;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ImageService {

    @GET("/customsearch/v1")
    Call<Search> getImageUrl(@QueryMap Map<String, String> options);

}
