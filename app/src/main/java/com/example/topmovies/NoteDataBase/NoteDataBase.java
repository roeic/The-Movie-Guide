package com.example.topmovies.NoteDataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {NoteModel.class},version = 1 , exportSchema = false)
public abstract class NoteDataBase extends RoomDatabase {

    public static final String NOTES = "notes";

    public abstract NoteDao noteDao();

    static NoteDataBase INSTANCE;

    public static NoteDataBase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDataBase.class,NOTES).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .build();

        }


        return INSTANCE;
    }

    public static void destroyInstance(){INSTANCE = null;}

}
