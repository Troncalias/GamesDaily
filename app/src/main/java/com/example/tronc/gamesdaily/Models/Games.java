package com.example.tronc.gamesdaily.Models;

public class Games {
    private int id;
    private String nome;
    private String Publicador;
    private String descricao;
    private String dataInsercao;
    private byte[] imagem;
    private String utilizador_adicionou;
    private int number_jogadores;
    private String rating;
    private Chat chat;

    public Games(int id, String nome, String rating, String publicador, String descricao, String dataInsercao, byte[] imagem, String utilizador_adicionou, int number_jogadores) {
        this.id = id;
        this.nome = nome;
        this.rating = rating;
        Publicador = publicador;
        this.descricao = descricao;
        this.dataInsercao = dataInsercao;
        this.imagem = imagem;
        this.utilizador_adicionou = utilizador_adicionou;
        this.number_jogadores = number_jogadores;
    }

    public Games(){}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getPublicador() { return Publicador; }

    public void setPublicador(String publicador) { Publicador = publicador; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getDataInsercao() { return dataInsercao; }

    public void setDataInsercao(String dataInsercao) { this.dataInsercao = dataInsercao; }

    public byte[] getImagem() { return imagem; }

    public void setImagem(byte[] imagem) { this.imagem = imagem; }

    public String getUtilizador_adicionou() { return utilizador_adicionou; }

    public void setUtilizador_adicionou(String utilizador_adicionou) { this.utilizador_adicionou = utilizador_adicionou; }

    public int getNumber_jogadores() { return number_jogadores; }

    public void setNumber_jogadores(int number_jogadores) { this.number_jogadores = number_jogadores; }

    public String getRating() { return rating; }

    public void setRating(String rating) { this.rating = rating; }


}
