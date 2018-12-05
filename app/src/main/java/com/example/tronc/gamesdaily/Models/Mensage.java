package com.example.tronc.gamesdaily.Models;

public class Mensage {
    private int id;
    private String utilizador_username;
    private String conteudo;
    private String dataInsercao;

    public Mensage(int id, String utilizador_username, String conteudo, String dataInsercao) {
        this.id = id;
        this.utilizador_username = utilizador_username;
        this.conteudo = conteudo;
        this.dataInsercao = dataInsercao;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUtilizador_username() {
        return utilizador_username;
    }

    public void setUtilizador_username(String utilizador_username) {
        this.utilizador_username = utilizador_username;
    }

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
}
