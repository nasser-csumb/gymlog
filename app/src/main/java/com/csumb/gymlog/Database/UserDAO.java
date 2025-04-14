package com.csumb.gymlog.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.csumb.gymlog.Database.Entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + GymLogDatabase.USER_TABLE + " ORDER BY username")
    List<User> getAllUsers();

    @Query("SELECT * FROM " + GymLogDatabase.USER_TABLE + " WHERE username = :name AND password = :pass")
    User loginUser(String name, String pass);

    @Query("SELECT * FROM " + GymLogDatabase.USER_TABLE + " WHERE id = :id")
    User getUser(int id);

    @Query("DELETE FROM " + GymLogDatabase.USER_TABLE)
    void deleteAll();
}
