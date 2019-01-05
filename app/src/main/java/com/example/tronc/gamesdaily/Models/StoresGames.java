package com.example.tronc.gamesdaily.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;


/**
 * Class que permite fazer o relacionamento entre cada Store e os Games
 */
@Entity(primaryKeys = {"game_id", "store_id"},
        foreignKeys =  {@ForeignKey(entity = Stores.class, parentColumns = "id", childColumns = "store_id"),
                @ForeignKey(entity = Games.class, parentColumns = "id", childColumns = "game_id")},
        tableName = "StoresGames")
public class StoresGames {
    @NonNull
    @ColumnInfo(name="game_id")
    private int game_id;

    @NonNull
    @ColumnInfo(name="store_id")
    private int store_id;

    public StoresGames() {
    }

    @Ignore
    public StoresGames(@NonNull int game_id, @NonNull int store_id) {
        this.game_id = game_id;
        this.store_id = store_id;
    }

    @NonNull
    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(@NonNull int game_id) {
        this.game_id = game_id;
    }

    @NonNull
    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(@NonNull int store_id) {
        this.store_id = store_id;
    }
}
