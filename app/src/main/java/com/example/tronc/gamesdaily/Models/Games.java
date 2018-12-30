package com.example.tronc.gamesdaily.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

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
    private int AddBy;
    private int NumberGamers;
    private float Rating;

    public Games(){}

    @Ignore
    public Games(String name, String publicher, String description, String date, byte[] imagem, int addBy, int numberGamers, float rating) {
        Name = name;
        Publicher = publicher;
        Description = description;
        Date = date;
        Imagem = imagem;
        AddBy = addBy;
        NumberGamers = numberGamers;
        Rating = rating;
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

    public int getAddBy() { return AddBy; }

    public void setAddBy(int addBy) { AddBy = addBy; }

    public int getNumberGamers() { return NumberGamers; }

    public void setNumberGamers(int numberGamers) { NumberGamers = numberGamers; }

    public float getRating() { return Rating; }

    public void setRating(float rating) { Rating = rating; }

    public String getName() { return Name; }

    public void setName(String name) { Name = name; }
}
