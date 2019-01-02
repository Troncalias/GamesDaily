package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
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
import android.widget.TextView;

import com.example.tronc.gamesdaily.Adapter.ChatAdapter;
import com.example.tronc.gamesdaily.Adapter.MensageAdapter;
import com.example.tronc.gamesdaily.Data.MyDB;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Chat;
import com.example.tronc.gamesdaily.Models.Mensage;
import com.example.tronc.gamesdaily.R;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatActivity extends AppCompatActivity {
    private static Activity mRefActivity;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private static RecyclerView rvUtilizadores;
    private ChatAdapter gAdapter;
    private Bundle extras;
    private static MyDB sampleDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mRefActivity = this;
        sampleDatabase = Room.databaseBuilder(getApplicationContext(), MyDB.class, this.getString(R.string.database_value)).build();

        setToolbar();
        setFragments();
        LoadChats listChats = new LoadChats();
        listChats.execute();

    }

    @Override
    public void onResume(){
        super.onResume();
        //GamesActivity.LoadContent load = new GamesActivity.LoadContent();
        //load.execute();
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Chats");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HeaderFragment f = new HeaderFragment();
        fragmentTransaction.add(R.id.frame_layout_header, f);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    /**
    private void setList() {
        ArrayList<Chat> contacts = (ArrayList<Chat>) new List_Chats().getLista_chates();
        gAdapter = new ChatAdapter(this.getApplicationContext(), contacts, this);
        mRecyclerView = findViewById(R.id.rvChat);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(gAdapter);
    }**/

    public class LoadChats extends AsyncTask<Void, Void, ArrayList<Chat>> {

        @Override
        protected ArrayList<Chat> doInBackground(Void... voids) {
            ArrayList<Chat> listChat = (ArrayList<Chat>) sampleDatabase.geral().loadAllChatsNormal(-1);
            return listChat;
        }

        protected void onPostExecute(ArrayList<Chat> list){
            gAdapter = new ChatAdapter(mRefActivity.getApplicationContext(), list, mRefActivity);
            mRecyclerView = findViewById(R.id.rvChat);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRefActivity, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(gAdapter);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }
    }

    public static void openChat(Chat myItem, Activity mActivity) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.dialog_accept, null);

        builder.setView(view);
        builder.setView(view);
        final AlertDialog dialog = builder.show();
        rvUtilizadores = (RecyclerView) view.findViewById(R.id.rvList);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRefActivity, DividerItemDecoration.VERTICAL);
        rvUtilizadores.addItemDecoration(mDividerItemDecoration);
        TextView textView = (TextView) view.findViewById(R.id.tituloTv);
        Button closeBtn = (Button) view.findViewById(R.id.button_close);
        Button searchButton = (Button) view.findViewById(R.id.button_search);

        LoadMensages listMensagens = new LoadMensages(mActivity, sampleDatabase);
        listMensagens.execute();
    }

    /**
    private static void setListMensagens() {
        ArrayList<Mensage> list = (ArrayList<Mensage>) new List_Mensagens().getLista_chates();
        MensageAdapter gAdapter = new MensageAdapter(mRefActivity, list);
        rvUtilizadores.setAdapter(gAdapter);
        rvUtilizadores.setLayoutManager(new LinearLayoutManager(mRefActivity));
    }**/

    public static class LoadMensages extends AsyncTask<Void, Void, ArrayList<Mensage>> {
        public  Activity mActivity;
        public MyDB sampleDatabase;

        public LoadMensages(Activity mActivity, MyDB sampleDatabase) {
            this.mActivity = mActivity;
            this.sampleDatabase = sampleDatabase;
        }

        @Override
        protected ArrayList<Mensage> doInBackground(Void... voids) {
            int i = sampleDatabase.geral().getSizeGames();
            i++;    String y1 = String.valueOf(i);
            i++;    String y2 = String.valueOf(i);
            Mensage mensagem = new Mensage(001, "log", "Bem vindo a nossa aplicacao", "20/10/2019 10:00");
            this.sampleDatabase.geral().addMensage(mensagem);
            this.sampleDatabase.geral().addMensage(mensagem);
            ArrayList<Mensage> listGames = (ArrayList<Mensage>) sampleDatabase.geral().loadAllMensagens(001);
            return listGames;
        }
        @Override
        protected void onPostExecute(ArrayList<Mensage> list){//Executa como se fosse na principal
            MensageAdapter gAdapter = new MensageAdapter(mRefActivity, list);
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
        Bundle extras = getIntent().getExtras();
        if(extras.getString("KEY").equals("admin")) {
            getMenuInflater().inflate(R.menu.menu_admin, menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_search:
                setClickMenuSearch();
                return true;
            case R.id.action_sort:
                setClickMenuSort();
                return true;
            case R.id.action_add:
                setClickMenuAddChat();
                return true;
            case R.id.action_admin:
                Intent i = new Intent(ChatActivity.this, AdminActivity.class);
                i.putExtra("KEY",extras.getString("KEY"));
                startActivity(i);
                return true;
            case R.id.action_definitions:
                Intent y = new Intent(ChatActivity.this, DefenitionActivity.class);
                y.putExtra("KEY","a");
                startActivity(y);
                return true;
            default:
                return false;
        }
    }

    private void setClickMenuSort() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_order, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        Button idBtn = (Button) view.findViewById(R.id.btn_orderByID);
        Button dateBtn = (Button) view.findViewById(R.id.btn_orderByDate);
        Button nameBtn = (Button) view.findViewById(R.id.btn_orderByName);
        Button confirmarBtn = (Button) view.findViewById(R.id.btn_confirmar);
        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);

    }

    private void setClickMenuAddChat() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_chat, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        final EditText tituloChat = (EditText) view.findViewById(R.id.tituloChat);
        final EditText descricao_Chat = (EditText) view.findViewById(R.id.descricaoChat);
        Button addBtn = (Button) view.findViewById(R.id.btn_add_chat);
        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);

    }

    private void setClickMenuSearch() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_search, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        final EditText tituloChat = (EditText) view.findViewById(R.id.procurar);
        Button confirmar = (Button) view.findViewById(R.id.btn_confirm);
        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);

    }

}

