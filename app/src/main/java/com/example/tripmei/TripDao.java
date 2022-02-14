package com.example.tripmei;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TripDao {

    @Query("SELECT * FROM trip")
    List<Trip> getAll();

    @Query("SELECT * FROM trip where tripState == 'UPCOMING' ")
    List<Trip> getAllUpcoming();

    @Query("SELECT * FROM trip where tripState != 'UPCOMING' ")
    List<Trip> getAllHistory();

    @Query("SELECT * FROM trip where id = :tid ")
    List<Trip> getRow(int tid);

    @Insert
    void insertAll(Trip... trips);

    @Insert
    void insert(Trip trip);

    @Update
    void update(Trip trip);

    @Query("UPDATE trip SET tripState = :State WHERE id = :tid")
    void updateState(String State , int tid);

    @Query("UPDATE trip SET notes = :notesList WHERE id = :tid")
    void addNotes(String notesList , int tid);

    @Delete
    void delete(Trip trip);
}
