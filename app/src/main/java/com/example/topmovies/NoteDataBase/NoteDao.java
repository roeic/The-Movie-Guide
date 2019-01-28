package com.example.topmovies.NoteDataBase;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM notemodel WHERE movieId LIKE :key")
    NoteModel findNote(int key);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteModel noteModel);

    @Query("SELECT * FROM notemodel")
    List<NoteModel> getall();

}
