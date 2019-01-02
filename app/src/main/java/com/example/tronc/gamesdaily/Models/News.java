package com.example.tronc.gamesdaily.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "News")
public class News {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "newsName")
    private String newsName;

    @ColumnInfo(name = "date")
    private String dataInsercao;

    @ColumnInfo(name = "description")
    private String descricao;


    public News(String newsName, String dataInsercao, String descricao) {
        this.newsName = newsName;
        this.dataInsercao = dataInsercao;
        this.descricao = descricao;
    }

    @Ignore
    public News(@NonNull int id, String newsName, String dataInsercao, String descricao) {
        this.id = id;
        this.newsName = newsName;
        this.dataInsercao = dataInsercao;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(String dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}

