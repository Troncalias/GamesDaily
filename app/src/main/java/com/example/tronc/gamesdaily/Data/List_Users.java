package com.example.tronc.gamesdaily.Data;

import android.app.ListActivity;

import com.example.tronc.gamesdaily.Models.User;

import java.util.ArrayList;

public class List_Users extends ListActivity {
    private ArrayList<User> lista_games = new ArrayList<User>();

    public ArrayList<User> getLista_games() {
        return lista_games;
    }

    public void setLista_games(ArrayList<User> lista_games) {
        this.lista_games = lista_games;
    }

    //String username, String password, String nome, String dataRegisto, String email, int tipo_utilizador_id, byte[] imagem
    public List_Users() {
        User user = new User(1,"tronca", "pass", "nome", "20/10/2019 10:00", "email@mail.com", 0, null);
        lista_games.add(user);
        user = new User(2,"DJ", "pass", "nome", "20/10/2019 10:00", "email@mail.com", 0, null);
        lista_games.add(user);
    }

    public User search(int id){
        User trues = null;
        for (int i=0; i<this.lista_games.size(); i++){
            if(this.lista_games.get(i).getId() == id){
                trues = this.lista_games.get(i);
            }
        }
        return trues;
    }
}
