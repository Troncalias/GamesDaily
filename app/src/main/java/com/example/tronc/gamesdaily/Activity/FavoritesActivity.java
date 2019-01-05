package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tronc.gamesdaily.Adapter.GamesAdapter;
import com.example.tronc.gamesdaily.Data.MyDB;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Chat;
import com.example.tronc.gamesdaily.Models.Favoritos;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.Notifications.MyNotification;
import com.example.tronc.gamesdaily.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FavoritesActivity extends AppCompatActivity {

    private static Activity mRefActivity;
    private static String user;

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private GamesAdapter gAdapter;
    private Bundle extras;
    private MyDB sampleDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        mRefActivity = this;

        extras = getIntent().getExtras();
        user = extras.getString("KEY");

        sampleDatabase = Room.databaseBuilder(getApplicationContext(), MyDB.class, this.getString(R.string.database_value)).build();
        setToolbar();
        setFragments();

        LoadGames listGames = new LoadGames(user);
        listGames.execute();


    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Favoritos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HeaderFragment f = new HeaderFragment();
        fragmentTransaction.add(R.id.frame_layout_header, f);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    public class LoadGames extends AsyncTask<Void, Void, ArrayList<Games>> {
        public String user;
        public LoadGames(String user) {
            this.user = user;
        }
        @Override
        protected ArrayList<Games> doInBackground(Void... voids) {
            ArrayList<Games> list = (ArrayList<Games>) sampleDatabase.geral().loadAllGamesAcepted(true);
            ArrayList<Favoritos> listf = (ArrayList<Favoritos>) sampleDatabase.geral().loadFavesByUser(user);
            ArrayList<Games> listGames = new ArrayList<Games>();

            for(int i=0; i<list.size(); i++){
                for(int y=0; y<listf.size(); y++){
                    if(list.get(i).getId() == listf.get(y).getGames_id()){
                        listGames.add(list.get(i));
                    }
                }
            }
            return listGames;
        }
        @Override
        protected void onPostExecute(ArrayList<Games> games){//Executa como se fosse na principal
            gAdapter = new GamesAdapter(mRefActivity.getApplicationContext(), games, mRefActivity, user);
            mRecyclerView = findViewById(R.id.rvGames);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRefActivity.getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(gAdapter);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        if(extras.getInt("Type") == 2) {
            getMenuInflater().inflate(R.menu.menu_admin, menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_sort:
                setClickMenuSort();
                return true;
            case R.id.action_add:
                setClickMenuAddChat();
                return true;
            case R.id.action_admin:
                Intent i = new Intent(FavoritesActivity.this, AdminActivity.class);
                i.putExtra("KEY",user);
                startActivity(i);
                return true;
            case R.id.action_definitions:
                Intent y = new Intent(FavoritesActivity.this, DefenitionActivity.class);
                y.putExtra("KEY",user);
                startActivity(y);
                return true;
            default:
                return false;
        }
    }

    private void setClickMenuSort() {

        AlertDialog.Builder builder = new AlertDialog.Builder(FavoritesActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_order, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        Button dateBtn = (Button) view.findViewById(R.id.btn_orderStanderd);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderBy order = new OrderBy("Standerd", dialog);
                order.execute();
            }
        });
        Button nameBtn = (Button) view.findViewById(R.id.btn_orderByName);
        nameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderBy order = new OrderBy("Name", dialog);
                order.execute();
            }
        });
        Button idBtn = (Button) view.findViewById(R.id.btn_orderByRating);
        idBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderBy order = new OrderBy("Rating", dialog);
                order.execute();
            }
        });

        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    public class OrderBy extends AsyncTask<Void, Void, ArrayList<Games>> {
        public String name;
        public AlertDialog dialog;

        public OrderBy(String name, AlertDialog dialog) {
            this.name = name;
            this.dialog = dialog;
        }

        @Override
        protected ArrayList<Games> doInBackground(Void... voids) {
            ArrayList<Games> list;
            if(name.equals("Name")){
                list = (ArrayList<Games>) sampleDatabase.geral().loadAllGamesOrderName(true);
            }else{
                list = (ArrayList<Games>) sampleDatabase.geral().loadAllGamesAcepted(true);
            }
            ArrayList<Favoritos> listf = (ArrayList<Favoritos>) sampleDatabase.geral().loadFavesByUser(user);
            ArrayList<Games> listGames = new ArrayList<Games>();

            for(int i=0; i<list.size(); i++){
                for(int y=0; y<listf.size(); y++){
                    if(list.get(i).getId() == listf.get(y).getGames_id()){
                        listGames.add(list.get(i));
                    }
                }
            }

            return listGames;
        }
        @Override
        protected void onPostExecute(ArrayList<Games> games){
            dialog.dismiss();
            gAdapter.setList(games);
        }
    }

    private void setClickMenuAddChat() {

        AlertDialog.Builder builder = new AlertDialog.Builder(FavoritesActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_chat, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        final EditText tituloChat = (EditText) view.findViewById(R.id.tituloChat);
        final EditText descricao_Chat = (EditText) view.findViewById(R.id.descricaoChat);

        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button addBtn = (Button) view.findViewById(R.id.btn_add_chat);
        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddChat addchat = new AddChat(tituloChat.getText().toString(), descricao_Chat.getText().toString(), dialog);
                addchat.execute();
            }
        });
    }

    public class AddChat extends AsyncTask<Void, Void, Boolean> {
        public String titulo;
        public String descricao;
        public AlertDialog dialog;

        public AddChat(String t, String d, AlertDialog di) {
            titulo = t;
            descricao = d;
            dialog = di;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date hoje = new Date();
            String data = dateFormat.format(hoje);

            //int id_game, String Data,String Titulo, String Descricao
            Chat chat = new Chat(-1, data, titulo, descricao);
            sampleDatabase.geral().addChat(chat);

            return true;
        }

        protected void onPostExecute(Boolean bool){
            Context context = getApplicationContext();
            CharSequence text = "Chat Adicionado";
            int duration = Toast.LENGTH_SHORT;

            MyNotification.addChatNotification(FavoritesActivity.this, titulo, descricao, getString(R.string.notification_add_chat_title), getApplicationContext());
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            dialog.dismiss();
        }
    }

}
