package com.example.topmovies.Model;

import com.example.topmovies.Rest.MoviesService;

import java.util.ArrayList;

public class MovieModelConvertor {

    public static ArrayList<MovieModel> convertResult (MovieResultList movieResultList){

        ArrayList<MovieModel> result = new ArrayList<>();
        for(ResultsMovie resultsItem : movieResultList.getResults()){
            if(resultsItem.getPosterPath()==null || resultsItem.getBackdropPath()==null || resultsItem.getOverview().isEmpty())continue;
            result.add(new MovieModel(resultsItem.getId(),resultsItem.getTitle(),MoviesService.POSTER_BASE_URL+resultsItem.getPosterPath(),
                    MoviesService.BACKDROP_BASE_URL+resultsItem.getBackdropPath(),resultsItem.getOverview(),resultsItem.getReleaseDate(),resultsItem.getVoteAverage()));
        }


        return result;
    }
}
