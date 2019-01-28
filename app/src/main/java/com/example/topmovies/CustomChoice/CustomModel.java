package com.example.topmovies.CustomChoice;

public class CustomModel {

 private int  vote;
 private double rating;
 private int year;


    public CustomModel() {
    }

    public CustomModel(int vote, double rating, int year) {
        this.vote = vote;
        this.rating = rating;
        this.year = year;

    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }



    @Override
    public String toString() {
        return getVote()+" "+getRating()+" "+ getYear();
    }
}
