package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
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
import android.widget.Toast;

import com.example.tronc.gamesdaily.Adapter.ChatAdapter;
import com.example.tronc.gamesdaily.Adapter.MensageAdapter;
import com.example.tronc.gamesdaily.Data.MyDB;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Chat;
import com.example.tronc.gamesdaily.Models.Mensage;
import com.example.tronc.gamesdaily.Notifications.MyNotification;
import com.example.tronc.gamesdaily.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    private static Activity mRefActivity;

    private static MensageAdapter gAdapterMensages;
    private static ChatAdapter gAdapterChat;

    private String user;

    private static RecyclerView rvUtilizadores;
    private static Bundle extras;
    private static MyDB sampleDatabase;

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private static Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mRefActivity = this;
        mContext = this;
        sampleDatabase = Room.databaseBuilder(getApplicationContext(), MyDB.class, this.getString(R.string.database_value)).build();

        extras = getIntent().getExtras();
        user = extras.getString("KEY");

        setToolbar();
        setFragments();

        LoadChats listChats = new LoadChats();
        listChats.execute();

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

    public class LoadChats extends AsyncTask<Void, Void, ArrayList<Chat>> {

        @Override
        protected ArrayList<Chat> doInBackground(Void... voids) {
            ArrayList<Chat> listChat = (ArrayList<Chat>) sampleDatabase.geral().loadAllChatsNormal(-1);
            return listChat;
        }

        protected void onPostExecute(ArrayList<Chat> list){
            gAdapterChat = new ChatAdapter(mRefActivity.getApplicationContext(), list, mRefActivity, user);
            mRecyclerView = findViewById(R.id.rvChat);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRefActivity, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(gAdapterChat);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }
    }

    public static void openChat(final Chat myItem, final Activity mActivity, final String user) {
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
        closeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button searchButton = (Button) view.findViewById(R.id.button_search);
        searchButton.setText("Add Mensage");
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddMensagem(myItem.getId(), mActivity, user);
            }
        });

        LoadMensages listMensagens = new LoadMensages(mActivity, sampleDatabase, myItem);
        listMensagens.execute();
    }

    public static void AddMensagem(final int id, final Activity mActivity, final String user){
        final AlertDialog.Builder builder =  new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.dialog_mensage, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        final EditText content = (EditText) view.findViewById(R.id.mensagem_text);

        Button aceitar = (Button) view.findViewById(R.id.btn_add_chat);
        aceitar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(content.getText().toString().equals("")){
                    Toast toast = Toast.makeText(mContext, "Introduza uma mensagem", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    AddMensage addm = new AddMensage(mActivity, sampleDatabase, content.getText().toString(), id, user, gAdapterMensages, dialog);
                    addm.execute();
                }
            }
        });

        Button anular = (Button) view.findViewById(R.id.button_cancel);
        anular.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static class AddMensage extends AsyncTask<Void, Void, ArrayList<Mensage>> {
        public Activity mActivity;
        public MyDB sampleDatabase;
        public String mensage;
        public int id;
        public String user;
        public MensageAdapter gAdapter;
        public AlertDialog dialog;

        public AddMensage(Activity mActivity, MyDB sampleDatabase, String mensage, int id, String user, MensageAdapter gAdapterMensages, AlertDialog dialog) {
            this.mActivity = mActivity;
            this.sampleDatabase = sampleDatabase;
            this.mensage = mensage;
            this.id = id;
            this.user = user;
            this.gAdapter = gAdapterMensages;
            this.dialog = dialog;
        }

        @Override
        protected ArrayList<Mensage> doInBackground(Void... voids) {
            java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date hoje = new Date();
            String data = dateFormat.format(hoje);


            Mensage mensagem = new Mensage(id, user, mensage, data);
            sampleDatabase.geral().addMensage(mensagem);
            ArrayList<Mensage> listGames = (ArrayList<Mensage>) sampleDatabase.geral().loadAllMensagens(id);
            return listGames;
        }
        @Override
        protected void onPostExecute(ArrayList<Mensage> listMensagens){
            this.dialog.dismiss();
            gAdapter.setList(listMensagens);
        }
    }

    public static class LoadMensages extends AsyncTask<Void, Void, ArrayList<Mensage>> {
        public  Activity mActivity;
        public MyDB sampleDatabase;
        public Chat chat;

        public LoadMensages(Activity mActivity, MyDB sampleDatabase, Chat myItem) {
            this.mActivity = mActivity;
            this.sampleDatabase = sampleDatabase;
            this.chat = myItem;
        }

        @Override
        protected ArrayList<Mensage> doInBackground(Void... voids) {
            ArrayList<Mensage> listGames = (ArrayList<Mensage>) sampleDatabase.geral().loadAllMensagens(chat.getId());
            return listGames;
        }
        @Override
        protected void onPostExecute(ArrayList<Mensage> list){
            gAdapterMensages = new MensageAdapter(mRefActivity, list);
            rvUtilizadores.setAdapter(gAdapterMensages);
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
        if(extras.getInt("Type") == 2) {
            getMenuInflater().inflate(R.menu.menu_admin, menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
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
                Intent i = new Intent(ChatActivity.this, AdminActivity.class);
                i.putExtra("KEY",user);
                startActivity(i);
                return true;
            case R.id.action_definitions:
                Intent y = new Intent(ChatActivity.this, DefenitionActivity.class);
                y.putExtra("KEY",user);
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

        Button idBtn = (Button) view.findViewById(R.id.btn_orderByRating);
        Button dateBtn = (Button) view.findViewById(R.id.btn_orderStanderd);
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

            MyNotification.addChatNotification(ChatActivity.this, titulo, descricao, getString(R.string.notification_add_chat_title), getApplicationContext());
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            dialog.dismiss();
        }
    }

}

