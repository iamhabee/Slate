package com.habee.slate.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.habee.slate.model.SlateModels;

@Database(entities = {SlateModels.class}, version = 1)
public abstract class SlateDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "slate_db";

    private static SlateDatabase instance;

    static SlateDatabase getInstance(final Context context){
        if (instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    SlateDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract SlateDao getSlateDao();
}
