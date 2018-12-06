package com.example.tronc.gamesdaily.Models;

public class News {

    private int id;
    private String newsName;
    private String dataInsercao;
    private String descricao;
    private String rating;

    public News(int id, String newsName, String dataInsercao, String descricao) {
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

