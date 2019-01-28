package com.example.topmovies.Model;

import java.util.ArrayList;

public class MovieContent {

    public static  final ArrayList<MovieModel> MOVIES  = new ArrayList<>();


    public static  void  clear(){
        MOVIES.clear();
    }


    public static  void  addMovie(MovieModel movie){
        MOVIES.add(movie);
    }
}
