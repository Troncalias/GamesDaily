package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tronc.gamesdaily.Adapter.ChatRemoveAdapter;
import com.example.tronc.gamesdaily.Adapter.GamesAcceptAdapter;
import com.example.tronc.gamesdaily.Adapter.NewsAcceptAdapter;
import com.example.tronc.gamesdaily.Adapter.StoresAcceptAdapter;
import com.example.tronc.gamesdaily.Adapter.UserAdapter;
import com.example.tronc.gamesdaily.Data.List_News;
import com.example.tronc.gamesdaily.Data.MyDB;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Chat;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.Models.News;
import com.example.tronc.gamesdaily.Models.Stores;
import com.example.tronc.gamesdaily.Models.User;
import com.example.tronc.gamesdaily.R;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    private MyDB sampleDatabase;

    private static Activity mRefActivity;
    private static RecyclerView rvUtilizadores;
    private ImageView imageView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mRefActivity = this;
        sampleDatabase = Room.databaseBuilder(getApplicationContext(), MyDB.class, this.getString(R.string.database_value)).build();

        setToolbar();
        setFragments();
        setButtons();
    }

    private void setToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Administração");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HeaderFragment f = new HeaderFragment();
        fragmentTransaction.add(R.id.frame_layout_header, f);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    private void setButtons() {
        setButtonGame();
        setButtonStore();
        setButtonNews();
        setButtonChats();
        setButtonUsers();
    }


    private void setButtonGame(){
        Button btn_add_game = (Button) findViewById(R.id.btn_add_game);
        btn_add_game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_add_game, null);

                builder.setView(view);
                final AlertDialog dialog = builder.show();

                final EditText nomeGame = (EditText) view.findViewById(R.id.tituloGame);
                final EditText descricaoGame = (EditText) view.findViewById(R.id.descricaoGame);
                final EditText publicherGame = (EditText) view.findViewById(R.id.publicadorGame);
                Button button_add_image = (Button) view.findViewById(R.id.button_add_image_game);
                Button addBtn = (Button) view.findViewById(R.id.btn_confirm);
                Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);


            }
        });

        Button btn_accept_game = (Button) findViewById(R.id.btn_accept_game);
        btn_accept_game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_accept, null);
                builder.setView(view);
                final AlertDialog dialog = builder.show();

                rvUtilizadores = (RecyclerView) view.findViewById(R.id.rvList);
                DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(AdminActivity.this, DividerItemDecoration.VERTICAL);
                rvUtilizadores.addItemDecoration(mDividerItemDecoration);
                TextView textView = (TextView) view.findViewById(R.id.tituloTv);
                textView.setText("Accept Game");
                Button closeBtn = (Button) view.findViewById(R.id.button_close);
                Button searchButton = (Button) view.findViewById(R.id.button_search);

                LoadGames listGames = new LoadGames();
                listGames.execute();
            }
        });


    }

    public class LoadGames extends AsyncTask<Void, Void, ArrayList<Games>> {

        @Override
        protected ArrayList<Games> doInBackground(Void... voids) {
            ArrayList<Games> list = (ArrayList<Games>) sampleDatabase.geral().loadAllGamesAcepted(false);
            return list;
        }

        protected void onPostExecute(ArrayList<Games> list){
            GamesAcceptAdapter gAdapter = new GamesAcceptAdapter(mRefActivity, list);
            rvUtilizadores.setAdapter(gAdapter);
            rvUtilizadores.setLayoutManager(new LinearLayoutManager(mRefActivity));
        }
    }

    private void setButtonStore(){
        Button btn_add_store = (Button) findViewById(R.id.btn_add_store);
        btn_add_store.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_add_store, null);

                builder.setView(view);
                final AlertDialog dialog = builder.show();
                final EditText nomeLoja = (EditText) view.findViewById(R.id.nomeLoja);
                final EditText descricaoLoja = (EditText) view.findViewById(R.id.descricaoLoja);

                imageView = (ImageView) view.findViewById(R.id.imageView);
                Button button_add_image = (Button) view.findViewById(R.id.button_add_image);
                Button addBtn = (Button) view.findViewById(R.id.btn_confirm);
                Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);
            }
        });

        Button btn_accept_store = (Button) findViewById(R.id.btn_accept_store);
        btn_accept_store.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_accept, null);
                builder.setView(view);
                final AlertDialog dialog = builder.show();

                rvUtilizadores = (RecyclerView) view.findViewById(R.id.rvList);
                DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(AdminActivity.this, DividerItemDecoration.VERTICAL);
                rvUtilizadores.addItemDecoration(mDividerItemDecoration);

                TextView textView = (TextView) view.findViewById(R.id.tituloTv);
                textView.setText("Accept Store");
                Button closeBtn = (Button) view.findViewById(R.id.button_close);
                Button searchButton = (Button) view.findViewById(R.id.button_search);

                LoadStores listStores = new LoadStores();
                listStores.execute();
            }
        });
    }

    public class LoadStores extends AsyncTask<Void, Void, ArrayList<Stores>> {

        @Override
        protected ArrayList<Stores> doInBackground(Void... voids) {
            int i = sampleDatabase.geral().getSizeStores();
            i++;    String y1 = String.valueOf(i);
            i++;    String y2 = String.valueOf(i);
            Stores stores = new Stores("Loja " + y1, "Rua Avenida 1", "Loja de Jogos Steam", "20/10/2019 10:00", null, 02, true);
            sampleDatabase.geral().addStore(stores);
            stores = new Stores("Loja " + y2, "Rua Avenida 2", "Loja de Jogos Local", "30/1/2018 10:00", null, 01, true);
            sampleDatabase.geral().addStore(stores);
            sampleDatabase.geral().deleteStore(stores.getId());
            ArrayList<Stores> list = (ArrayList<Stores>) sampleDatabase.geral().loadAllStoresAcepted(false);
            return list;
        }

        protected void onPostExecute(ArrayList<Stores> list){
            StoresAcceptAdapter gAdapter = new StoresAcceptAdapter(mRefActivity, list);
            rvUtilizadores.setAdapter(gAdapter);
            rvUtilizadores.setLayoutManager(new LinearLayoutManager(mRefActivity));
        }
    }

    private void setButtonNews() {
        Button btn_add_noticia = (Button) findViewById(R.id.btn_add_news);
        btn_add_noticia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_add_news, null);

                builder.setView(view);
                final AlertDialog dialog = builder.show();
                final EditText tituloEt = (EditText) view.findViewById(R.id.tituloEt);
                final EditText conteudoEt = (EditText) view.findViewById(R.id.conteudoEt);
                TextView textView = (TextView) view.findViewById(R.id.tituloTv);
                textView.setText("Accept Game");
                Button addBtn = (Button) view.findViewById(R.id.btn_add_noticia);
                Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);

            }
        });

        Button btn_accept_noticia = (Button) findViewById(R.id.btn_accept_news);
        btn_accept_noticia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_accept, null);
                builder.setView(view);
                final AlertDialog dialog = builder.show();

                TextView textView = (TextView) view.findViewById(R.id.tituloTv);
                textView.setText("Accept News");
                rvUtilizadores = (RecyclerView) view.findViewById(R.id.rvList);
                DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(AdminActivity.this, DividerItemDecoration.VERTICAL);
                rvUtilizadores.addItemDecoration(mDividerItemDecoration);

                Button closeBtn = (Button) view.findViewById(R.id.button_close);
                Button searchButton = (Button) view.findViewById(R.id.button_search);

                setListNews();

            }
        });
    }

    private static void setListNews() {
        ArrayList<News> list = (ArrayList<News>) new List_News().getList_news();
        NewsAcceptAdapter gAdapter = new NewsAcceptAdapter(mRefActivity, list);
        rvUtilizadores.setAdapter(gAdapter);
        rvUtilizadores.setLayoutManager(new LinearLayoutManager(mRefActivity));
    }

    private void setButtonChats() {
        Button btn_add_chat = (Button) findViewById(R.id.btn_remove_chat);
        btn_add_chat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_accept, null);
                builder.setView(view);
                final AlertDialog dialog = builder.show();


                rvUtilizadores = (RecyclerView) view.findViewById(R.id.rvList);
                DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(AdminActivity.this, DividerItemDecoration.VERTICAL);
                rvUtilizadores.addItemDecoration(mDividerItemDecoration);

                TextView textView = (TextView) view.findViewById(R.id.tituloTv);
                textView.setText("Remove Chat");
                Button closeBtn = (Button) view.findViewById(R.id.button_close);
                Button searchButton = (Button) view.findViewById(R.id.button_search);

                LoadChats listChats = new LoadChats();
                listChats.execute();
            }
        });
    }

    public class LoadChats extends AsyncTask<Void, Void, ArrayList<Chat>> {

        @Override
        protected ArrayList<Chat> doInBackground(Void... voids) {
            int i = sampleDatabase.geral().getSizeChats();
            i++;    String y1 = String.valueOf(i);
            i++;    String y2 = String.valueOf(i);

            //int id_game, String Data,String Titulo, String Descricao
            Chat chat1 = new Chat(01,"20/10/2019 10:00", "Bem Vindo", "Descrição das regras da app");
            Chat chat2 = new Chat(02, "21/10/2019 10:00", "Truques e gliches", "Descrição para a ");
            sampleDatabase.geral().addChat(chat1);
            sampleDatabase.geral().addChat(chat2);
            ArrayList<Chat> listChat = (ArrayList<Chat>) sampleDatabase.geral().loadAllChats();
            return listChat;
        }

        protected void onPostExecute(ArrayList<Chat> list){
            ChatRemoveAdapter gAdapter = new ChatRemoveAdapter(mRefActivity, list);
            rvUtilizadores.setAdapter(gAdapter);
            rvUtilizadores.setLayoutManager(new LinearLayoutManager(mRefActivity));
        }
    }

    private void setButtonUsers() {
        Button btn_utilizadores = (Button) findViewById(R.id.btn_remove_users);
        btn_utilizadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_accept, null);
                builder.setView(view);
                final AlertDialog dialog = builder.show();

                rvUtilizadores = (RecyclerView) view.findViewById(R.id.rvList);
                DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(AdminActivity.this, DividerItemDecoration.VERTICAL);
                rvUtilizadores.addItemDecoration(mDividerItemDecoration);

                TextView textView = (TextView) view.findViewById(R.id.tituloTv);
                textView.setText("Remove Users");
                Button closeBtn = (Button) view.findViewById(R.id.button_close);
                Button searchButton = (Button) view.findViewById(R.id.button_search);

                LoadUsers list = new LoadUsers();
                list.execute();
            }
        });
    }

    public class LoadUsers extends AsyncTask<Void, Void, ArrayList<User>> {

        @Override
        protected ArrayList<User> doInBackground(Void... voids) {
            ArrayList<User> listUsers = (ArrayList<User>) sampleDatabase.geral().loadAllUsers();
            return listUsers;
        }

        protected void onPostExecute(ArrayList<User> list){
            UserAdapter gAdapter = new UserAdapter(mRefActivity, list);
            rvUtilizadores.setAdapter(gAdapter);
            rvUtilizadores.setLayoutManager(new LinearLayoutManager(mRefActivity));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg = " ";
        switch (item.getItemId()) {
            case R.id.action_add:
                msg = "Add Chat";
                setClickMenuAddChat();
                break;
            case R.id.action_admin:
                msg = "Admin";
                break;
            case R.id.action_logout:
                msg = "Logout";
                break;
            case R.id.action_definitions:
                msg = "Defenition";
                break;
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    private void setClickMenuAddChat() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_chat, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        final EditText tituloChat = (EditText) view.findViewById(R.id.tituloChat);
        final EditText descricao_Chat = (EditText) view.findViewById(R.id.descricaoChat);
        Button addBtn = (Button) view.findViewById(R.id.btn_add_chat);
        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);

    }

}

