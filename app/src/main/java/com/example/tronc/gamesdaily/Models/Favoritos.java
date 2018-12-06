package com.example.tronc.gamesdaily.Models;

public class Favoritos {

    private int id;
    private String username;
    private int games_id;
    private String dataInsercao;

    public Favoritos(int id, String username, int games_id, String dataInsercao) {
        this.id = id;
        this.username = username;
        this.games_id = games_id;
        this.dataInsercao = dataInsercao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(String dataInsercao) {
        this.dataInsercao = dataInsercao;
    }
}
