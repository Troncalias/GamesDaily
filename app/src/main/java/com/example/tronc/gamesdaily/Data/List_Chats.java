package com.example.tronc.gamesdaily.Data;

import android.app.ListActivity;

import com.example.tronc.gamesdaily.Models.Chat;
import com.example.tronc.gamesdaily.Models.Mensage;

import java.util.ArrayList;
import java.util.List;

public class List_Chats extends ListActivity {
    private List<Chat> lista_games = new ArrayList<Chat>();

    public List_Chats() {
        //int id, String utilizador_username, String conteudo, String dataInsercao
        Mensage mensagem = new Mensage(001, "Troncalias", "Bem vindo a nossa aplicacao", "10/11/2018");
        //int id,String Titulo, String Descricao,ArrayList<Mensage> comentarios
        ArrayList<Mensage> array = new ArrayList<Mensage>();
        array.add(mensagem);
        array.add(mensagem);
        Chat chat1 = new Chat(001, "20/10/2019 10:00", "Bem Vindo", "Descrição das regras da app", array);
        Chat chat2 = new Chat(002, "21/10/2019 10:00", "Truques e gliches", "Descrição para a ", array);
        lista_games.add(chat1);
        lista_games.add(chat2);

    }

    public List<Chat> getLista_chates() {
        return lista_games;
    }

    public void setLista_games(ArrayList<Chat> lista_games) {
        this.lista_games = lista_games;
    }

    public Chat search(int id){
        Chat trues = null;
        for (int i=0; i<this.lista_games.size(); i++){
            if(this.lista_games.get(i).getId() == id){
                trues = this.lista_games.get(i);
            }
        }
        return trues;
    }
}
