package com.example.tronc.gamesdaily.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity( tableName = "User")
public class User {
    @NonNull
    @PrimaryKey
    private String username;


    private String password;
    private String nome;
    private String dataRegisto;
    private String email;
    private int storeId;
    private int tipo_utilizador_id;
    private byte[] imagem;

    public User() {
    }

    @Ignore
    public User(String username, String password, String nome, String dataRegisto, String email) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.dataRegisto = dataRegisto;
        this.email = email;
        this.tipo_utilizador_id= 1;
    }
    @Ignore
    public User(String username, String password, String nome, String dataRegisto, String email, int tipo) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.dataRegisto = dataRegisto;
        this.email = email;
        this.tipo_utilizador_id= tipo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(String dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getTipo_utilizador_id() {
        return tipo_utilizador_id;
    }

    public void setTipo_utilizador_id(int tipo_utilizador_id) { this.tipo_utilizador_id = tipo_utilizador_id; }

    public byte[] getImagem() { return imagem; }

    public void setImagem(byte[] imagem) { this.imagem = imagem; }
}
