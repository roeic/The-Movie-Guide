package com.example.topmovies.NoteDataBase;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class NoteModel {


    @PrimaryKey
    public int movieId;

    @ColumnInfo(name = "note")
    public String note;



    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public NoteModel(int movieId, String note) {
        this.movieId = movieId;
        this.note = note;
    }




    @Override
    public String toString() {
        return "the id is: " + getMovieId()+ " and the comments is: "+ getNote()+"\n";
    }
}
