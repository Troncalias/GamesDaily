package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.tronc.gamesdaily.Adapter.GamesAdapter;
import com.example.tronc.gamesdaily.Adapter.StoresAdapter;
import com.example.tronc.gamesdaily.Data.MyDB;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Chat;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.Models.Stores;
import com.example.tronc.gamesdaily.Models.StoresGames;
import com.example.tronc.gamesdaily.Notifications.MyNotification;
import com.example.tronc.gamesdaily.R;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StoresActivity extends AppCompatActivity {

    private static Activity mRefActivity;
    private static RecyclerView rvUtilizadores;
    private static RecyclerView mRecyclerView;
    private static GamesAdapter gAdapter;
    private static MyDB sampleDatabase;
    private Toolbar mToolbar;
    private StoresAdapter sAdapter;
    private Bundle extras;
    private static String user;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        mRefActivity = this;
        sampleDatabase = Room.databaseBuilder(getApplicationContext(), MyDB.class, this.getString(R.string.database_value)).build();
        mContext = this;

        extras = getIntent().getExtras();
        user = extras.getString("KEY");
        setToolbar();
        setFragments();
        LoadStores listStores = new LoadStores();
        listStores.execute();
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Lojas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HeaderFragment f = new HeaderFragment();
        fragmentTransaction.add(R.id.frame_layout_header, f);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    public class LoadStores extends AsyncTask<Void, Void, ArrayList<Stores>> {

        @Override
        protected ArrayList<Stores> doInBackground(Void... voids) {
            ArrayList<Stores> list = (ArrayList<Stores>) sampleDatabase.geral().loadAllStoresAcepted(true);
            return list;
        }

        protected void onPostExecute(ArrayList<Stores> list) {
            sAdapter = new StoresAdapter(mRefActivity.getApplicationContext(), list, mRefActivity);
            mRecyclerView = findViewById(R.id.rvGames);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRefActivity, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(sAdapter);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        if (extras.getInt("Type") == 2) {
            getMenuInflater().inflate(R.menu.menu_admin, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                setClickMenuSort();
                return true;
            case R.id.action_add:
                setClickMenuAddChat();
                return true;
            case R.id.action_admin:
                Intent i = new Intent(StoresActivity.this, AdminActivity.class);
                i.putExtra("KEY", user);
                startActivity(i);
                return true;
            case R.id.action_definitions:
                Intent y = new Intent(StoresActivity.this, DefenitionActivity.class);
                y.putExtra("KEY", user);
                startActivity(y);
                return true;
            default:
                return false;
        }
    }

    private void setClickMenuSort() {

        AlertDialog.Builder builder = new AlertDialog.Builder(StoresActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_order_simpler, null);

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

        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public class OrderBy extends AsyncTask<Void, Void, ArrayList<Stores>> {
        public String name;
        public AlertDialog dialog;

        public OrderBy(String name, AlertDialog dialog) {
            this.name = name;
            this.dialog = dialog;
        }

        @Override
        protected ArrayList<Stores> doInBackground(Void... voids) {
            ArrayList<Stores> listGames;
            if(name.equals("Name")){
                listGames = (ArrayList<Stores>) sampleDatabase.geral().loadAllStoresAceptedOrder(true);
            }else {
                listGames = (ArrayList<Stores>) sampleDatabase.geral().loadAllStoresAcepted(true);
            }
            return listGames;
        }
        @Override
        protected void onPostExecute(ArrayList<Stores> games){
            dialog.dismiss();
            sAdapter.setList(games);
        }
    }

    private void setClickMenuAddChat() {

        AlertDialog.Builder builder = new AlertDialog.Builder(StoresActivity.this);
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

            MyNotification.addChatNotification(StoresActivity.this, titulo, descricao, getString(R.string.notification_add_chat_title), getApplicationContext());
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            dialog.dismiss();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void openGame(final Stores stores, Activity mActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.dialog_store, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();
        final ImageView imageView = view.findViewById(R.id.imageView);
        if (stores.getImagem() != null) {
            ByteArrayInputStream imageStream = new ByteArrayInputStream(stores.getImagem());
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(theImage);
        }

        final TextView nomeTv = (TextView) view.findViewById(R.id.nomeTv);
        nomeTv.setText(stores.getNome());
        final TextView dataInsercaoTv = (TextView) view.findViewById(R.id.moradaTv);
        dataInsercaoTv.setText(stores.getMorada());
        final TextView ratingTv = (TextView) view.findViewById(R.id.descricaoTv);
        ratingTv.setText(stores.getDescricao());

        final Button loadmap = (Button) view.findViewById(R.id.pretitle_moradaTv);
        loadmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = stores.getNome();
                String descricao = stores.getDescricao();
                String morada = stores.getMorada();
                StoresActivity.openMap(morada, nome, descricao);
            }
        });

        final Button close = (Button) view.findViewById(R.id.button_cancelBt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    public static void openGames(Activity mActivity, Stores store) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.dialog_accept_simpler, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();
        builder.setView(view);

        rvUtilizadores = (RecyclerView) view.findViewById(R.id.rvList);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRefActivity, DividerItemDecoration.VERTICAL);
        rvUtilizadores.addItemDecoration(mDividerItemDecoration);
        TextView textView = (TextView) view.findViewById(R.id.tituloTv);
        Button closeBtn = (Button) view.findViewById(R.id.button_close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        LoadGames listGames = new LoadGames(mActivity, sampleDatabase, store);
        listGames.execute();

    }

    public static void openMap(String m, String n, String d) {

        Intent x = new Intent(mContext, MapsActivity.class);
        x.putExtra("morada", m);
        x.putExtra("nome", n);
        x.putExtra("descricao", d);
        mContext.startActivity(x);
    }

    public static class LoadGames extends AsyncTask<Void, Void, ArrayList<Games>> {
        public Activity mActivity;
        public MyDB sampleDatabase;
        public Stores stores;

        public LoadGames(Activity mActivity, MyDB database, Stores store) {
            this.sampleDatabase = database;
            this.mActivity = mActivity;
            this.stores = store;
        }

        @Override
        protected ArrayList<Games> doInBackground(Void... voids) {
            ArrayList<Games> listGames = (ArrayList<Games>) sampleDatabase.geral().loadAllGamesAcepted(true);
            ArrayList<StoresGames> listAssociados = (ArrayList<StoresGames>) sampleDatabase.geral().loadSotresGamesByStore(stores.getId());

            ArrayList<Games> list = new ArrayList<Games>();

            for(int i=0;i<listGames.size(); i++){
                for(int y=0; y<listAssociados.size(); y++){
                    if(listGames.get(i).getId() == listAssociados.get(y).getGame_id()){
                        list.add(listGames.get(i));
                        y=listAssociados.size();
                    }
                }
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Games> games) {
            GamesAdapter adapter = new GamesAdapter(mActivity, games, mRefActivity, user);
            rvUtilizadores.setAdapter(adapter);
            rvUtilizadores.setLayoutManager(new LinearLayoutManager(mActivity));
        }
    }
}
