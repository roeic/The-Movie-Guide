package com.example.topmovies.Details;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.RotateDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topmovies.Model.MovieModel;
import com.example.topmovies.Model.ResultsVideo;
import com.example.topmovies.Model.VideolistResult;
import com.example.topmovies.NoteDataBase.NoteDataBase;
import com.example.topmovies.NoteDataBase.NoteModel;
import com.example.topmovies.R;
import com.example.topmovies.Rest.MoviesService;
import com.example.topmovies.Rest.RestClientManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsFragments extends Fragment implements View.OnClickListener {


    public static final String ARG_MOVIE = "MovieModel-data";
    public static final int TRAILER = 10;
    public static final int SHARE = 11;

    private ImageView fragImage,fragBack;
    private TextView fragTitle,fragReleasedDate,fragOverview,fragScore;
    private MovieModel movieModel;
    private Picasso picasso;
    private Button btnTrailer;
    private NoteModel note;
    private EditText editText;
    private ImageButton btnShare;

    public MovieDetailsFragments(){}

    public static MovieDetailsFragments newInstance (MovieModel movieModel){
        MovieDetailsFragments fragments = new MovieDetailsFragments();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE,movieModel);
        fragments.setArguments(args);
        return fragments;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        picasso = Picasso.get();
        if (getArguments() != null){
            movieModel = getArguments().getParcelable(ARG_MOVIE);

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragImage = view.findViewById(R.id.details_frag_img);
        fragScore = view.findViewById(R.id.details_frag_score);
        fragBack = view.findViewById(R.id.details_frag_back);
        fragTitle = view.findViewById(R.id.details_frag_title);
        fragReleasedDate = view.findViewById(R.id.details_frag_releasedDate);
        fragOverview = view.findViewById(R.id.details_frag_overview_text);
        btnTrailer = view.findViewById(R.id.details_frag_btn_trailer);
        btnShare = view.findViewById(R.id.details_frag_share_btn);
        btnShare.setOnClickListener(this);
        btnTrailer.setOnClickListener(this);
        editText = view.findViewById(R.id.details_frag_note);




        setMovie();
    }

    private void setMovie(){
        if(movieModel == null) return;
        picasso.load(movieModel.getImageUri()).placeholder(R.drawable.place_holder).into(fragImage);
        picasso.load(movieModel.getBackImageUri()).placeholder(R.drawable.place_holder).into(fragBack);
        fragTitle.setText(movieModel.getName());
        fragReleasedDate.setText(movieModel.getReleaseDate());
        fragOverview.setText(movieModel.getOverview());
        fragScore.setText(String.valueOf(movieModel.getScore()));
        note = NoteDataBase.getInstance(getContext()).noteDao().findNote(movieModel.getMovieId());
        if(note != null) {
            editText.setText(note.getNote());
        }

    }

    @Override
    public void onClick(final View v) {
                if(movieModel == null) return;
                if (v.getId() == R.id.details_frag_btn_trailer){
                    setButtonLoadingStatus();
                }
                MoviesService moviesService  = RestClientManager.getMoviesServiceInstance();
                moviesService.getTrailer(movieModel.getMovieId()).enqueue(new Callback<VideolistResult>() {
                    @Override
                    public void onResponse(Call<VideolistResult> call, Response<VideolistResult> response) {
                        VideolistResult body = response.body();
                        if(body != null){
                            List<ResultsVideo> result = body.getResults();
                            if(result != null && !result.isEmpty()){
                                String trailerUrl = MoviesService.YOUTUBE_BASE_URL + result.get(0).getKey();
                                switch (v.getId()){
                                    case R.id.details_frag_btn_trailer:
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                                        startActivity(browserIntent);
                                        resetButtonStatus();
                                        break;
                                    case R.id.details_frag_share_btn:
                                        Intent share = new Intent(Intent.ACTION_SEND);
                                        share.setType("text/plain");
                                        share.putExtra(Intent.EXTRA_TEXT, trailerUrl);
                                        startActivity(Intent.createChooser(share,"Share trailer with..."));
                                        break;
                                }

                            }else {
                                Toast.makeText(getContext(), "No trailer found", Toast.LENGTH_SHORT).show();
                                resetButtonStatus();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<VideolistResult> call, Throwable t) {
                        Toast.makeText(getContext(), "something_went_wrong", Toast.LENGTH_SHORT).show();
                      resetButtonStatus();
                    }
                });
    }

    private void setButtonLoadingStatus(){
        Context context = getContext();
        if(context == null){
            return;
        }
        RotateDrawable rotateDrawable = (RotateDrawable) ContextCompat.getDrawable(context,R.drawable.progress_animation);
        ObjectAnimator anim = ObjectAnimator.ofInt(rotateDrawable,"level",0,10000);
        anim.setDuration(1000);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.start();
        btnTrailer.setText("Loading");
        btnTrailer.setCompoundDrawablesRelativeWithIntrinsicBounds(rotateDrawable,null,null,null);

    }

    private void resetButtonStatus(){
            btnTrailer.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
            btnTrailer.setText("Movie Trailer");
    }


    @Override
    public void onPause() {
        super.onPause();
        if(note != null) {
            if (!note.getNote().equals(editText.getText().toString())) {
                NoteDataBase.getInstance(getContext()).noteDao().insert(note = new NoteModel(movieModel.getMovieId(), editText.getText().toString()));
            }
        }else {
            if(!editText.getText().toString().isEmpty()) {
                NoteDataBase.getInstance(getContext()).noteDao().insert(note = new NoteModel(movieModel.getMovieId(), editText.getText().toString()));
            }
        }
    }



}
