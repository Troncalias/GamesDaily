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

    public List_Users() {
        //String username, String password, String nome, String dataRegisto, String email
        User user = new User("tronca", "pass", "nome", "20/10/2019 10:00", "email@mail.com");
        lista_games.add(user);
        user = new User("DJ", "pass", "nome", "20/10/2019 10:00", "email@mail.com");
        lista_games.add(user);
    }

    public User search(String name){
        User trues = null;
        for (int i=0; i<this.lista_games.size(); i++){
            if(this.lista_games.get(i).getUsername() == name){
                trues = this.lista_games.get(i);
            }
        }
        return trues;
    }
}
