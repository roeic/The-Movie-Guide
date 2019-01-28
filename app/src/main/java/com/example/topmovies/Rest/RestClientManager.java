package com.example.topmovies.Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClientManager {

    private  static  MoviesService moviesService;

    public static MoviesService getMoviesServiceInstance(){
        if(moviesService == null){
            Retrofit retrofit=  new Retrofit.Builder().baseUrl(MoviesService.BASE_API_URL).addConverterFactory(GsonConverterFactory.create())
                    .build();

            moviesService = retrofit.create(MoviesService.class);

        }

        return moviesService;
    }
}
