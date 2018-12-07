package com.example.tronc.gamesdaily.Models;

public class User {
    private int id;
    private String username;
    private String password;
    private String nome;
    private String dataRegisto;
    private String email;
    private Stores store;
    private int tipo_utilizador_id;
    private byte[] imagem;

    public User(int id,String username, String password, String nome, String dataRegisto, String email, int tipo_utilizador_id, byte[] imagem) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.dataRegisto = dataRegisto;
        this.tipo_utilizador_id = tipo_utilizador_id;
        this.email = email;
        this.imagem = imagem;
        this.store = null;
    }

    public User() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(String dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public int getTipo_utilizador_id() {
        return tipo_utilizador_id;
    }

    public void setTipo_utilizador_id(int tipo_utilizador_id) { this.tipo_utilizador_id = tipo_utilizador_id; }

    public byte[] getImagem() { return imagem; }

    public void setImagem(byte[] imagem) { this.imagem = imagem; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }


}
