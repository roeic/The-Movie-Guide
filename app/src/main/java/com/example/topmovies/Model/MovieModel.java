package com.example.topmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {


    private int movieId;
    private String name;
    private String imageUri;
    private String backImageUri;
    private String overview;
    private String releaseDate;
    private double score;



    public MovieModel(){}

    public MovieModel(int movieId, String name, String imageUri, String backImageUri, String overview, String releaseDate, double score) {
        this.movieId = movieId;
        this.name = name;
        this.imageUri = imageUri;
        this.backImageUri = backImageUri;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.score =score;
    }


    protected MovieModel(Parcel in) {
        movieId = in.readInt();
        name = in.readString();
        imageUri = in.readString();
        backImageUri = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        score = in.readDouble();
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getBackImageUri() {
        return backImageUri;
    }

    public void setBackImageUri(String backImageUri) {
        this.backImageUri = backImageUri;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(name);
        dest.writeString(imageUri);
        dest.writeString(backImageUri);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeDouble(score);
    }
}
