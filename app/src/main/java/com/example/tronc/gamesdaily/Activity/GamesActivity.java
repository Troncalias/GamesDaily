package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
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
import com.example.tronc.gamesdaily.Adapter.MensageAdapter;
import com.example.tronc.gamesdaily.Data.MyDB;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Avaliacao;
import com.example.tronc.gamesdaily.Models.Chat;
import com.example.tronc.gamesdaily.Models.Favoritos;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.Models.Mensage;
import com.example.tronc.gamesdaily.R;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GamesActivity extends AppCompatActivity {

    private static Activity mRefActivity;
    private static MyDB sampleDatabase;

    private static MensageAdapter gAdapterMensages;
    private static GamesAdapter gAdapterGames;
    private static String user;

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private static RecyclerView rvUtilizadores;
    private Bundle extras;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        mRefActivity = this;

        extras = getIntent().getExtras();
        user = extras.getString("KEY");

        setToolbar();
        setFragments();
        sampleDatabase = Room.databaseBuilder(getApplicationContext(), MyDB.class, this.getString(R.string.database_value)).build();
    }

    @Override
    public void onResume(){
        super.onResume();
        LoadContent load = new LoadContent();
        load.execute();
    }

    private void setToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Games");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HeaderFragment f = new HeaderFragment();
        fragmentTransaction.add(R.id.frame_layout_header, f);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    public class LoadContent extends AsyncTask<Void, Void, ArrayList<Games>> {

        @Override
        protected ArrayList<Games> doInBackground(Void... voids) {
            ArrayList<Games> listGames = (ArrayList<Games>) sampleDatabase.geral().loadAllGamesAcepted(true);

            return listGames;
        }
        @Override
        protected void onPostExecute(ArrayList<Games> games){//Executa como se fosse na principal
            gAdapterGames = new GamesAdapter(mRefActivity.getApplicationContext(), games, mRefActivity);
            mRecyclerView = findViewById(R.id.rvGames);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRefActivity.getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(gAdapterGames);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }
    }

    public static void openGame(Games game, final Activity mActivity) {
        final AlertDialog.Builder builder =  new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.dialog_game, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();
        final ImageView imageView = view.findViewById(R.id.imageView);
        if(game.getImagem() != null){
            ByteArrayInputStream imageStream = new ByteArrayInputStream(game.getImagem());
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(theImage);
        }

        final TextView nomeTv = (TextView) view.findViewById(R.id.nomeTv);
        nomeTv.setText(game.getName());
        final TextView dataInsercaoTv = (TextView) view.findViewById(R.id.dataInsercaoTv);
        dataInsercaoTv.setText(game.getDate());
        final TextView ratingTv = (TextView) view.findViewById(R.id.ratingTv);
        ratingTv.setText(Float.toString(game.getRating()));

        final TextView numberOfPlayersTv = (TextView) view.findViewById(R.id.numberOfGamers);
        numberOfPlayersTv.setText(String.valueOf(game.getNumberGamers()));
        final TextView descricao = (TextView) view.findViewById(R.id.descricaoTv);
        descricao.setText(game.getDescription());
        final TextView publicadoraTv = (TextView) view.findViewById(R.id.publicherTv);
        publicadoraTv.setText(game.getPublicher());
        Button avaliarBtn = (Button) view.findViewById(R.id.button_avaliar);
        final int i = game.getId();
        avaliarBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddAvalicao(i, user, mActivity, ratingTv);
            }
        });

        final int val = game.getId();
        Button favoritoBtn = (Button) view.findViewById(R.id.button_favorito);
        favoritoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AddFavoritos add = new AddFavoritos(val, user, sampleDatabase, mActivity);
                add.execute();
            }
        });

        Button cancel = (Button) view.findViewById(R.id.button_cancelBt);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button AccessStoresBtn = (Button) view.findViewById(R.id.button_lojas);
    }

    public static void openChat(Games games, final Activity mActivity) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.dialog_accept, null);

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
        final int i = games.getId();
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddMensagem(i, user, mActivity);
            }
        });

        LoadMensages listMensagens = new LoadMensages(mActivity, sampleDatabase, games.getId());
        listMensagens.execute();
    }

    public static void AddMensagem(final int id, final String user, final Activity mActivity){
        final AlertDialog.Builder builder =  new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.dialog_mensage, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        final EditText content = (EditText) view.findViewById(R.id.mensagem_text);

        Button aceitar = (Button) view.findViewById(R.id.btn_add_chat);
        aceitar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddMensage addm = new AddMensage(mActivity, sampleDatabase, content.getText().toString(), id, user, gAdapterMensages, dialog);
                addm.execute();
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
        public int id_game;
        public String user;
        public MensageAdapter gAdapter;
        public AlertDialog dialog;

        public AddMensage(Activity mActivity, MyDB sampleDatabase, String mensage, int id, String user, MensageAdapter gAdapterMensages, AlertDialog dialog) {
            this.mActivity = mActivity;
            this.sampleDatabase = sampleDatabase;
            this.mensage = mensage;
            this.id_game = id;
            this.user = user;
            this.gAdapter = gAdapterMensages;
            this.dialog = dialog;
        }

        @Override
        protected ArrayList<Mensage> doInBackground(Void... voids) {
            java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date hoje = new Date();
            String data = dateFormat.format(hoje);

            ArrayList<Chat> listChat = (ArrayList<Chat>) sampleDatabase.geral().loadAllChatsNormal(id_game);
            Mensage mensagem = new Mensage(listChat.get(0).getId(), user, mensage, data);
            sampleDatabase.geral().addMensage(mensagem);

            ArrayList<Chat> listChat2 = (ArrayList<Chat>) sampleDatabase.geral().loadAllChatsNormal(id_game);
            ArrayList<Mensage> listGames = (ArrayList<Mensage>) sampleDatabase.geral().loadAllMensagens(listChat2.get(0).getId());
            return listGames;
        }
        @Override
        protected void onPostExecute(ArrayList<Mensage> listMensagens){
            this.dialog.dismiss();
            gAdapterMensages.setList(listMensagens);
        }
    }

    public static class LoadMensages extends AsyncTask<Void, Void, ArrayList<Mensage>> {
        public Activity mActivity;
        public MyDB sampleDatabase;
        public int id;

        public LoadMensages(Activity mActivity, MyDB sampleDatabase, int id) {
            this.mActivity = mActivity;
            this.sampleDatabase = sampleDatabase;
            this.id = id;
        }

        protected void onPreExecute(){

        }

        @Override
        protected ArrayList<Mensage> doInBackground(Void... voids) {
            ArrayList<Chat> listChat = (ArrayList<Chat>) sampleDatabase.geral().loadAllChatsNormal(id);
            ArrayList<Mensage> listGames = (ArrayList<Mensage>) sampleDatabase.geral().loadAllMensagens(listChat.get(0).getId());
            return listGames;
        }
        @Override
        protected void onPostExecute(ArrayList<Mensage> list){//Executa como se fosse na principal
            gAdapterMensages = new MensageAdapter(mRefActivity, list);
            rvUtilizadores.setAdapter(gAdapterMensages);
            rvUtilizadores.setLayoutManager(new LinearLayoutManager(mRefActivity));
        }
    }

    public static void AddAvalicao(final int i, final String user, final Activity mActivity, final TextView ratingTv){
        final AlertDialog.Builder builder =  new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.dialog_mensage, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        final TextView title = (TextView) view.findViewById(R.id.titulo);
        title.setText("Adiciona Avaliacao");
        final EditText content = (EditText) view.findViewById(R.id.mensagem_text);
        content.setHint("Entre 0 e 10");

        Button aceitar = (Button) view.findViewById(R.id.btn_add_chat);
        aceitar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    Float value = Float.parseFloat(content.getText().toString());
                    if(value>=0 && value<=10){
                        AddRating addrating = new AddRating(mActivity,sampleDatabase, dialog, i, user, value, ratingTv);
                        addrating.execute();
                    }else{
                        Toast toast = Toast.makeText(mActivity, "O valor deve de ser de 0 para 1", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }catch (NumberFormatException nfe){
                    Toast toast = Toast.makeText(mActivity, "Deve de introduzir um valor numerico", Toast.LENGTH_SHORT);
                    toast.show();
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

    public static class AddRating extends AsyncTask<Void, Void, ArrayList<Games>> {
        public Activity mActivity;
        public MyDB sampleDatabase;
        public AlertDialog dialog;
        public int id_game;
        public String user;
        public float value;
        public TextView text;
        public float avaliacao;

        public AddRating(Activity mActivity, MyDB sampleDatabase, AlertDialog dialog, int id, String user, float value, TextView ratingTv) {
            this.mActivity = mActivity;
            this.sampleDatabase = sampleDatabase;
            this.dialog = dialog;
            this.id_game = id;
            this.user = user;
            this.value = value;
            this.text = ratingTv;
        }

        @Override
        protected ArrayList<Games> doInBackground(Void... voids) {
            Avaliacao avaliar = new Avaliacao(value, user, id_game);
            sampleDatabase.geral().addAvaliacao(avaliar);

            ArrayList<Avaliacao> listAvalia = (ArrayList<Avaliacao>) sampleDatabase.geral().getGameRate(id_game);
            Games game = sampleDatabase.geral().getGamesById(id_game);

            float total=0;
            avaliacao=0;

            for(int i=0; i<listAvalia.size(); i++){
                total += listAvalia.get(i).getValue();
            }
            avaliacao = total/listAvalia.size();
            game.setRating(avaliacao);
            sampleDatabase.geral().addGame(game);
            ArrayList<Games> listGames = (ArrayList<Games>) sampleDatabase.geral().loadAllGames();
            return listGames;
        }

        @Override
        protected void onPostExecute(ArrayList<Games> listMensagens){
            this.dialog.dismiss();
            text.setText(String.valueOf(avaliacao));
        }
    }

    public static class AddFavoritos extends AsyncTask<Void, Void, Boolean> {
        public int id;
        public String user;
        public MyDB sampleDatabase;
        public Activity mActivity;

        public AddFavoritos(int id, String user, MyDB sampleDatabase, Activity mActivity) {
            this.id = id;
            this.user = user;
            this.sampleDatabase = sampleDatabase;
            this.mActivity = mActivity;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            ArrayList<Favoritos> listf = (ArrayList<Favoritos>) sampleDatabase.geral().getIfExistFavorito(user,id);
            int i = listf.size();
            boolean sucess;
            if(listf.size() == 0){
                Favoritos favorito = new Favoritos(user, id);
                sampleDatabase.geral().addFavoritos(favorito);
                i++;
                sucess = true;
            }else{
                sampleDatabase.geral().deleteFavoritos(listf.get(0));
                i--;
                sucess = false;
            }
            Games listgames =  sampleDatabase.geral().getGamesById(id);
            listgames.setNumberGamers(i);
            sampleDatabase.geral().addGame(listgames);
            return sucess;
        }

        @Override
        protected void onPostExecute(Boolean sucess){
            if(sucess){
                Toast toast = Toast.makeText(mActivity, "Adicionado aos Favoritos", Toast.LENGTH_SHORT);
                toast.show();
            }else{
                Toast toast = Toast.makeText(mActivity, "Removido dos Favoritos", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        extras = getIntent().getExtras();
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
                return true;
            case R.id.action_sort:
                setClickMenuSort();
                return true;
            case R.id.action_add:
                setClickMenuAddChat();
                return true;
            case R.id.action_admin:
                Intent i = new Intent(GamesActivity.this, AdminActivity.class);
                i.putExtra("KEY",user);
                startActivity(i);
                return true;
            case R.id.action_definitions:
                Intent y = new Intent(GamesActivity.this, DefenitionActivity.class);
                y.putExtra("KEY",user);
                startActivity(y);
                return true;
            default:
                return false;
        }
    }

    private void setClickMenuSort() {

        AlertDialog.Builder builder = new AlertDialog.Builder(GamesActivity.this);
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

        Button confirmarBtn = (Button) view.findViewById(R.id.btn_confirmar);
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
            ArrayList<Games> listGames;
            if(name.equals("Name")){
                listGames = (ArrayList<Games>) sampleDatabase.geral().loadAllGamesOrderName(true);
            }else if(name.equals("Rating")){
                listGames = (ArrayList<Games>) sampleDatabase.geral().loadAllGamesOrderScore(true);
            }else{
                listGames = (ArrayList<Games>) sampleDatabase.geral().loadAllGamesAcepted(true);
            }
            return listGames;
        }
        @Override
        protected void onPostExecute(ArrayList<Games> games){
            dialog.dismiss();
            gAdapterGames.setList(games);
        }
    }

    private void setClickMenuAddChat() {

        AlertDialog.Builder builder = new AlertDialog.Builder(GamesActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_chat, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        final EditText tituloChat = (EditText) view.findViewById(R.id.tituloChat);
        final EditText descricao_Chat = (EditText) view.findViewById(R.id.descricaoChat);
        Button addBtn = (Button) view.findViewById(R.id.btn_add_chat);
        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
