package com.example.tripmei;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Trip.class}, version = 1)
//@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract TripDao tripDao();
    private  static  AppDatabase INSTANCE;

    public  static  AppDatabase getDbInstance(Context context){
        if (INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context,AppDatabase.class, "database-name")
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }
}
