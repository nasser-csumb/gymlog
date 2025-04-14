package com.csumb.gymlog.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.csumb.gymlog.Database.Entities.GymLog;

import java.util.List;

@Dao
public interface GymLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GymLog gymLog);

    @Query("SELECT * FROM " + GymLogDatabase.gymLogTable)
    List<GymLog> getAllRecords();



}
