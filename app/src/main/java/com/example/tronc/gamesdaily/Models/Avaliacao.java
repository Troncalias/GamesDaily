package com.example.tronc.gamesdaily.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"username","game_id"},tableName = "Avaliacao")
public class Avaliacao {
    private float value;
    @NonNull
    private String username;
    @NonNull
    private int game_id;

    public Avaliacao() {
    }

    @Ignore
    public Avaliacao(float value, @NonNull String username, @NonNull int game_id) {
        this.value = value;
        this.username = username;
        this.game_id = game_id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(@NonNull int game_id) {
        this.game_id = game_id;
    }
}
