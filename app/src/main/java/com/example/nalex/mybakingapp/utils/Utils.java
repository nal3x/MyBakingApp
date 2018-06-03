package com.example.nalex.mybakingapp.utils;

import com.example.nalex.mybakingapp.BuildConfig;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {

    public static final String FILETYPE = "jpg";
    public static final String IMAGE_SIZE = "large";
    public static final String IMAGE_TYPE = "photo";
    public static final String REQUESTED_RESULTS = "1";
    public static final String SEARCH_TYPE = "image";
    public static final String REQUESTED_FIELDS = "items";


    public static Map<String, String> getQueryOptions(String recipeName) {
        /* Helper method which returns a map of query options, used to make the custom google
         * search. These options are passed to getImageUrl's retrofit ImageService.
         */

        Map<String, String> queryOptions = new HashMap<>();

        //required search parameters
        queryOptions.put("q", recipeName);
        queryOptions.put("key", BuildConfig.apikey);
        queryOptions.put("cx", "009201282033906542644:hgbpu-i8xwe");
        //optional parameters
        queryOptions.put("fileType", FILETYPE);
        queryOptions.put("imgSize", IMAGE_SIZE);
        queryOptions.put("imgType", IMAGE_TYPE);
        queryOptions.put("num", REQUESTED_RESULTS);
        queryOptions.put("searchType", SEARCH_TYPE);
        queryOptions.put("fields", REQUESTED_FIELDS);

        return queryOptions;

    }

    public static BakeService getBakeService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BakeService service = retrofit.create(BakeService.class);
        return service;
    }

    public static ImageService getImageService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ImageService.class);
    }
}
