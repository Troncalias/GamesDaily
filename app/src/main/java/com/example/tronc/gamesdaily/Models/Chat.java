package com.example.tronc.gamesdaily.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;

@Entity(tableName = "Chats")
public class Chat {
    private String Titulo;

    @ColumnInfo(name = "Descricao")
    private String Descricao;

    @ColumnInfo(name = "data_criacao")
    private String dataCriação;

    @ColumnInfo(name = "id_game")
    private int id_game;

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    public Chat(){

    }

    @Ignore
    public Chat(int id, int id_game, String Data,String Titulo, String Descricao) {
        this.Titulo = Titulo;
        this.Descricao = Descricao;
        this.id_game = id_game;
        this.id = id;
        this.dataCriação = Data;
    }

    @Ignore
    public Chat(int id, String Data,String Titulo, String Descricao) {
        this.Titulo = Titulo;
        this.Descricao = Descricao;
        this.id = id;
        this.dataCriação = Data;
    }

    public int getId_game() {
        return id_game;
    }

    public void setId_game(int id_game) {
        this.id_game = id_game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() { return Titulo; }

    public void setTitulo(String titulo) { Titulo = titulo; }

    public String getDescricao() { return Descricao; }

    public void setDescricao(String descricao) { Descricao = descricao; }

    public String getDataCriação() { return dataCriação; }

    public void setDataCriação(String dataCriação) { this.dataCriação = dataCriação; }
}
