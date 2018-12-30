package com.example.tronc.gamesdaily.Interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.tronc.gamesdaily.Models.Favoritos;

import java.util.List;

@Dao
public interface FavoritosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addFavoritosDao(Favoritos favorito);

    @Query("SELECT * FROM Favoritos WHERE id=:favoritosId")
    public Favoritos getFavoritosById(int favoritosId);

    @Query("SELECT * FROM Favoritos")
    public List<Favoritos> loadAllFavoritos();
}
