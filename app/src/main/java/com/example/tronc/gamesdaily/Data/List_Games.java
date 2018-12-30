package com.example.tronc.gamesdaily.Data;

import android.app.ListActivity;

import com.example.tronc.gamesdaily.Models.Games;

import java.util.ArrayList;
import java.util.List;

public class List_Games extends ListActivity {
    private List<Games> lista_games = new ArrayList<Games>();

    public List_Games() {
        //String name, String publicher, String description, String date, byte[] imagem, int addBy, int numberGamers, float rating
        Games game = new Games("nome1" ,"publicador1", "descricao1",  "20/10/2019 10:00", null, 1, 100, 9);
        this.lista_games.add(game);
        game = new Games("nome2", "publicador2", "descricao2", "20/10/2019 10:00" , null, 2, 200, 10);
        this.lista_games.add(game);
    }

    public List<Games> getLista_games() {
        return lista_games;
    }

    public void setLista_games(ArrayList<Games> lista_games) {
        this.lista_games = lista_games;
    }

    public Games search(int id){
        Games trues = null;
        for (int i=0; i<this.lista_games.size(); i++){
            if(this.lista_games.get(i).getId() == id){
                trues = this.lista_games.get(i);
            }
        }
        return trues;
    }
}
