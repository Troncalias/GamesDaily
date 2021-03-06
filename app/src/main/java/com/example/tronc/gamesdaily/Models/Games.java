package com.example.tronc.gamesdaily.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Class que serve como estrutura para guradar todas as informações dos Games
 */
@Entity(tableName = "Games")
public class Games {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String Name;
    private String Publicher;
    private String Description;
    private String Date;
    private byte[] Imagem;
    private int NumberGamers;
    private float Rating;
    private boolean Acepted;

    public Games(){}

    @Ignore
    public Games(String name, String publicher, String description, String date, byte[] imagem, int numberGamers, float rating, boolean acepted) {
        Name = name;
        Publicher = publicher;
        Description = description;
        Date = date;
        Imagem = imagem;
        NumberGamers = numberGamers;
        Rating = rating;
        Acepted = acepted;
    }

    @NonNull
    public int getId() { return id; }

    public void setId(@NonNull int id) { this.id = id; }

    public String getPublicher() { return Publicher; }

    public void setPublicher(String publicher) { Publicher = publicher; }

    public String getDescription() { return Description; }

    public void setDescription(String description) { Description = description; }

    public String getDate() { return Date; }

    public void setDate(String date) { Date = date; }

    public byte[] getImagem() { return Imagem; }

    public void setImagem(byte[] imagem) { Imagem = imagem; }

    public int getNumberGamers() { return NumberGamers; }

    public void setNumberGamers(int numberGamers) { NumberGamers = numberGamers; }

    public float getRating() { return Rating; }

    public void setRating(float rating) { Rating = rating; }

    public String getName() { return Name; }

    public void setName(String name) { Name = name; }

    public boolean isAcepted() { return Acepted; }

    public void setAcepted(boolean acepted) { Acepted = acepted; }

}
