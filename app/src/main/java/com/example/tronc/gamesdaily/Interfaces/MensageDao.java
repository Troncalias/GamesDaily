package com.example.tronc.gamesdaily.Interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.tronc.gamesdaily.Models.Mensage;

import java.util.List;

@Dao
public interface MensageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addMensage(Mensage mensage);

    @Query("SELECT * FROM Mensagens WHERE id=:mensagensId")
    public Mensage getMensageById(int mensagensId);

    @Query("SELECT * FROM Mensagens")
    public List<Mensage> loadAllMensages();
}
