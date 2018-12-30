package com.example.tronc.gamesdaily.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Mensagens")
public class Mensage {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "id_chat")
    private int id_chat;

    @ColumnInfo(name = "user_id")
    private int user_id;

    @ColumnInfo(name = "content")
    private String conteudo;

    @ColumnInfo(name = "data")
    private String dataInsercao;

    public Mensage() {
    }

    @Ignore
    public Mensage(int id_chat, int user_id, String conteudo, String dataInsercao) {
        this.id_chat = id_chat;
        this.user_id = user_id;
        this.conteudo = conteudo;
        this.dataInsercao = dataInsercao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) { this.user_id = user_id; }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(String dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public int getId_chat() { return id_chat; }

    public void setId_chat(int id_chat) { this.id_chat = id_chat; }
}
