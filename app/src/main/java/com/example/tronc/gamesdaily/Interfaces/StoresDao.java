package com.example.tronc.gamesdaily.Interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.tronc.gamesdaily.Models.Stores;

import java.util.List;

@Dao
public interface StoresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addStore(Stores store);

    @Query("SELECT * FROM Stores WHERE id=:storeId")
    public Stores getStoresById(int storeId);

    @Query("SELECT * FROM Stores")
    public List<Stores> loadAllStores();
}
