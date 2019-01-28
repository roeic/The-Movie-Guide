package com.example.topmovies.Rest;

import com.example.topmovies.Model.MovieResultList;
import com.example.topmovies.Model.VideolistResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesService {
    String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342";
    String BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780";
    String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    String BASE_URL = "https://api.themoviedb.org";
    /* base search image url */
    String BASE_API_URL = BASE_URL + "/3/";

    String DISCOVER = "discover/movie";
    String PLAYINGNOW = "movie/now_playing";
    String apiKey = "9cfd6f3dbbeb2ba1381fb40e1e1cd74e";
    String keyQuery = "?api_key="+apiKey;

    String DICOVER_QUERY_PATH = DISCOVER + keyQuery;

    String PLAYINGNOW_QUERY_PATH = PLAYINGNOW + keyQuery;


    String MOVIE_ID_KEY = "movie_id";
    String VIDEOS = "movie/{" + MOVIE_ID_KEY + "}/videos";
    String VIDEOS_QUERY_PATH = VIDEOS + keyQuery;


    @GET(VIDEOS_QUERY_PATH)
    Call<VideolistResult>getTrailer(@Path(MOVIE_ID_KEY) int movieID);

    @GET(PLAYINGNOW_QUERY_PATH)
    Call<MovieResultList> getPlayingNowMovies();

    @GET(DICOVER_QUERY_PATH)
    Call<MovieResultList> getDiscoverMovie(@Query("primary_release_date.gte")String minYear,@Query("primary_release_date.lte")String maxYear,
    @Query("vote_count.gte") Integer minVote,@Query("vote_average.gte") Double minScore, @Query("primary_release_year") Integer releasedYear);
}
