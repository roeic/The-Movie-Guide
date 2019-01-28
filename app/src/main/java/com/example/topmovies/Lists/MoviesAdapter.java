package com.example.topmovies.Lists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.topmovies.Model.MovieModel;
import com.example.topmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>{

    private List<MovieModel> movies;
    private Picasso picasso;
    private OnMovieClickedListner  movieClickedListner;

    public MoviesAdapter(List<MovieModel> movies, OnMovieClickedListner movieClickedListner) {
        this.movies = movies;
        picasso = Picasso.get();
        this.movieClickedListner = movieClickedListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movies_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bind(movies.get(i));

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mImage;
        TextView mTitle, mOverview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.popular_item_img);
            mTitle = itemView.findViewById(R.id.popular_item_name);
            mOverview = itemView.findViewById(R.id.popular_item_overview);
            itemView.setOnClickListener(this);
        }

        public  void  bind (MovieModel movieModel){
            picasso.load(movieModel.getImageUri()).placeholder(R.drawable.place_holder).into(mImage);
            mTitle.setText(movieModel.getName());
            mOverview.setText(movieModel.getOverview());
        }


        @Override
        public void onClick(View v) {
            if (movieClickedListner == null) return;
            movieClickedListner.onMovieClicked(getAdapterPosition());
        }
    }

}
