package com.example.tronc.gamesdaily.Models;

import java.util.ArrayList;

public class Chat {
    private ArrayList<Mensage> comentarios;
    private String Titulo;
    private String Descricao;
    private String dataCriação;
    private int id_game;
    private int id;

    public Chat(int id, int id_game, String Data,String Titulo, String Descricao,ArrayList<Mensage> comentarios) {
        this.Titulo = Titulo;
        this.Descricao = Descricao;
        this.comentarios = comentarios;
        this.id_game = id_game;
        this.id = id;
        this.dataCriação = Data;
    }

    public Chat(int id, String Data,String Titulo, String Descricao,ArrayList<Mensage> comentarios) {
        this.Titulo = Titulo;
        this.Descricao = Descricao;
        this.comentarios = comentarios;
        this.id = id;
        this.dataCriação = Data;
    }

    public ArrayList<Mensage> getComentarios() { return comentarios; }

    public void setComentarios(ArrayList<Mensage> comentarios) { this.comentarios = comentarios; }

    public void addComentario(Mensage mensagem){
        this.comentarios.add(mensagem);
    }

    public int getId_game() {
        return id_game;
    }

    public void setId_game(int id_game) {
        this.id_game = id_game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() { return Titulo; }

    public void setTitulo(String titulo) { Titulo = titulo; }

    public String getDescricao() { return Descricao; }

    public void setDescricao(String descricao) { Descricao = descricao; }

    public String getDataCriação() { return dataCriação; }

    public void setDataCriação(String dataCriação) { this.dataCriação = dataCriação; }
}
