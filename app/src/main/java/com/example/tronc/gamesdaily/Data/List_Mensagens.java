package com.example.tronc.gamesdaily.Data;

import android.app.ListActivity;

import com.example.tronc.gamesdaily.Models.Mensage;

import java.util.ArrayList;
import java.util.List;

public class List_Mensagens extends ListActivity {
    private List<Mensage> lista_games = new ArrayList<Mensage>();

    public List_Mensagens() {
        Mensage mensagem = new Mensage(001, "Troncalias", "Bem vindo a nossa aplicacao", "20/10/2019 10:00");
        lista_games.add(mensagem);
        lista_games.add(mensagem);
    }

    public List<Mensage> getLista_chates() {
        return lista_games;
    }

    public void setLista_games(ArrayList<Mensage> lista_games) {
        this.lista_games = lista_games;
    }

    public Mensage search(int id){
        Mensage trues = null;
        for (int i=0; i<this.lista_games.size(); i++){
            if(this.lista_games.get(i).getId() == id){
                trues = this.lista_games.get(i);
            }
        }
        return trues;
    }
}
