package com.example.topmovies.Lists;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.example.topmovies.CustomChoice.ChoiceFragmentListener;
import com.example.topmovies.CustomChoice.CustomChoiceFragment;
import com.example.topmovies.CustomChoice.CustomModel;
import com.example.topmovies.Details.DetailsActivity;
import com.example.topmovies.Model.MovieContent;
import com.example.topmovies.Model.MovieModelConvertor;
import com.example.topmovies.Model.MovieResultList;
import com.example.topmovies.R;
import com.example.topmovies.Rest.MoviesService;
import com.example.topmovies.Rest.RestClientManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity implements OnMovieClickedListner, ChoiceFragmentListener {


    private RecyclerView recyclerView;
    private TextView textView;
    private CircularProgressButton playingNowBtn, thisMonthBtn, nextMonthBtn, best2018Btn, best2017Btn, customBtn;
    private ImageView mainImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_movies);
        playingNowBtn = findViewById(R.id.category_playing_now);
        thisMonthBtn = findViewById(R.id.category_this_month);
        nextMonthBtn = findViewById(R.id.category_next_month);
        best2017Btn = findViewById(R.id.category_best_2017);
        best2018Btn = findViewById(R.id.category_best_2018);
        customBtn = findViewById(R.id.category_custom);
        recyclerView = findViewById(R.id.main_list_recycler);
        mainImg = findViewById(R.id.main_img);
        textView = findViewById(R.id.category_text);
        Toolbar toolbar = findViewById(R.id.action_bar_main);
        setSupportActionBar(toolbar);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

    }


    private void loadPlayingNowMovies(final CircularProgressButton button) {
        mainImg.setVisibility(View.INVISIBLE);
        button.startAnimation();
        MovieContent.clear();
        MoviesService moviesService = RestClientManager.getMoviesServiceInstance();
        moviesService.getPlayingNowMovies().enqueue(new Callback<MovieResultList>() {
            @Override
            public void onResponse(Call<MovieResultList> call, Response<MovieResultList> response) {
                if (response.code() == 200) {
                    textView.setText("Playing now");
                    MovieContent.MOVIES.addAll(MovieModelConvertor.convertResult(response.body()));
                    recyclerView.setAdapter(new MoviesAdapter(MovieContent.MOVIES, MoviesActivity.this));
                    endAnimation(button);
                }
            }
            @Override
            public void onFailure(Call<MovieResultList> call, Throwable t) {
            }
        });
    }

    private void loadDiscoverMovies(String minYear, String maxYear, int minVote, double minScore, Integer releasedyear
            , final CircularProgressButton button, final String text) {
        mainImg.setVisibility(View.INVISIBLE);
        button.startAnimation();
        MovieContent.clear();
        recyclerView.setVisibility(View.INVISIBLE);
        MoviesService moviesService = RestClientManager.getMoviesServiceInstance();
        moviesService.getDiscoverMovie(minYear, maxYear, minVote, minScore, releasedyear).enqueue(new Callback<MovieResultList>() {
            @Override
            public void onResponse(Call<MovieResultList> call, Response<MovieResultList> response) {
                if (response.code() == 200) {
                    textView.setText(text);
                    MovieContent.MOVIES.addAll(MovieModelConvertor.convertResult(response.body()));
                    recyclerView.setAdapter(new MoviesAdapter(MovieContent.MOVIES, MoviesActivity.this));
                    recyclerView.setVisibility(View.VISIBLE);
                    endAnimation(button);
                }
            }

            @Override
            public void onFailure(Call<MovieResultList> call, Throwable t) {
                Log.d("roei", t.getMessage());
            }
        });

    }

    @Override
    public void onMovieClicked(int itemPosition) {
        if (itemPosition < 0 || itemPosition >= MovieContent.MOVIES.size()) return;
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_ITEM_POSITION, itemPosition);
        startActivity(intent);
    }


    private String[] getCurrentMonth(int monthSelected) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String month = df.format(cal.getTime());
        cal.add(Calendar.MONTH, 1);
        String nextMonth = df.format(cal.getTime());
        String[] next = new String[2];
        switch (monthSelected) {
            case 0:
                next[0] = month;
                next[1] = nextMonth;
                break;
            case 1:
                next[0] = nextMonth;
                cal.add(Calendar.MONTH, 1);
                String afterNext = df.format(cal.getTime());
                next[1] = afterNext;
                break;
        }
        return next;
    }


    @Override
    public void onConfirmClicked(CustomModel customModel) {
        loadDiscoverMovies(null, null, customModel.getVote(), customModel.getRating(), customModel.getYear(),  customBtn,
                "Vote: "+customModel.getVote()+"+  Rating:" +customModel.getRating() + "+  Year:" +customModel.getYear());
    }

    public void onCategorySelected(View view) {
        switch (view.getId()) {
            case R.id.category_playing_now:
                loadPlayingNowMovies(playingNowBtn);
                break;
            case R.id.category_this_month:
                String[] month = getCurrentMonth(0);
                loadDiscoverMovies(month[0], month[1], 0, 0,  null, thisMonthBtn,"Out this month");
                break;
            case R.id.category_next_month:
                String[] nextMonth = getCurrentMonth(1);
                loadDiscoverMovies(nextMonth[0], nextMonth[1], 0, 0,  null, nextMonthBtn,"Out next Month");

                break;
            case R.id.category_best_2018:
                loadDiscoverMovies("2018", "2019", 500, 7.5,  null, best2018Btn,"Best of 2018");

                break;
            case R.id.category_best_2017:
                loadDiscoverMovies("2017", "2018", 500, 7.5,  null, best2017Btn,"Best of 2017");

                break;
            case R.id.category_custom:
                CustomChoiceFragment fragment = new CustomChoiceFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.main_activity_layout, fragment).addToBackStack(null)
                        .commit();
                break;
        }
    }

    void endAnimation(final CircularProgressButton button) {
        Handler handler = new Handler();
        final Bitmap done = BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp);
        button.doneLoadingAnimation(getResources().getColor(R.color.category_button_done), done);
        Runnable r = new Runnable() {
            @Override
            public void run() {

                button.revertAnimation();


            }
        };
        handler.postDelayed(r, 1800);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playingNowBtn.dispose();
        thisMonthBtn.dispose();
        nextMonthBtn.dispose();
        best2018Btn.dispose();
        best2017Btn.dispose();
        customBtn.dispose();
    }


}
