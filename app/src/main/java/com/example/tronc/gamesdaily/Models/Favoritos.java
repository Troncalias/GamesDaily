package com.example.tronc.gamesdaily.Models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Class que funciona como um registo de Games que um utlizador quer aceder com maior facilidade
 */
@Entity(primaryKeys = {"username","games_id"},
        indices = {@Index(value = {"username","games_id"})},
        foreignKeys =  {@ForeignKey(entity = User.class, parentColumns = "username", childColumns = "username"),
                @ForeignKey(entity = Games.class, parentColumns = "id", childColumns = "games_id")},
        tableName = "Favoritos")
public class Favoritos {

    @NonNull
    private String username;
    @NonNull
    private int games_id;

    public Favoritos() {
    }

    @Ignore
    public Favoritos(String username, int games_id) {
        this.username = username;
        this.games_id = games_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGames_id() {
        return games_id;
    }

    public void setGames_id(int games_id) {
        this.games_id = games_id;
    }
}
