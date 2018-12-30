package com.example.tronc.gamesdaily.Interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.tronc.gamesdaily.Models.Games;

import java.util.List;

@Dao
public interface GamesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addGame(Games games);

    @Query("SELECT * FROM Games WHERE id=:gameId")
    public Games getGamesById(int gameId);

    @Query("SELECT * FROM Games")
    public List<Games> loadAllGames();
}
