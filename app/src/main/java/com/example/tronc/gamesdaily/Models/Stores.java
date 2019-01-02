package com.example.tronc.gamesdaily.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Stores")
public class Stores {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    private String nome;
    private String morada;
    private String descricao;
    private String dataInsercao;
    private byte[] imagem;
    private int userId;
    private boolean acepted=false;

    public Stores() {
    }

    @Ignore
    public Stores(String nome, String morada, String descricao, String dataInsercao, byte[] imagem, int userId, boolean acepted) {
        this.nome = nome;
        this.morada = morada;
        this.descricao = descricao;
        this.dataInsercao = dataInsercao;
        this.imagem = imagem;
        this.userId = userId;
        this.acepted = acepted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(String dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public boolean isAcepted() { return acepted; }

    public void setAcepted(boolean acepted) { this.acepted = acepted; }
}
