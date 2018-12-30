package com.example.tronc.gamesdaily.Interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.tronc.gamesdaily.Models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addGame(User user);

    @Query("SELECT * FROM Users WHERE id=:userId")
    public User getUsersById(int userId);

    @Query("SELECT * FROM Users")
    public List<User> loadAllUsers();
}
