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

import com.example.tronc.gamesdaily.Adapter.GamesAdapter;
import com.example.tronc.gamesdaily.Adapter.MensageAdapter;
import com.example.tronc.gamesdaily.Data.MyDB;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.Models.Mensage;
import com.example.tronc.gamesdaily.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class GamesActivity extends AppCompatActivity {

    private static Activity mRefActivity;
    private static MyDB sampleDatabase;
    private static MensageAdapter mAdapter;

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private static RecyclerView rvUtilizadores;
    private GamesAdapter gAdapter;
    private Bundle extras;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        mRefActivity = this;

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
            /**
             ArrayList<Games> listGames = (ArrayList<Games>) sampleDatabase.geral().loadAllGames();
             sampleDatabase.geral().deletGame(listGames.get(listGames.size()-1));**/
            ArrayList<Games> listGames = (ArrayList<Games>) sampleDatabase.geral().loadAllGamesAcepted(true);

            return listGames;
        }
        @Override
        protected void onPostExecute(ArrayList<Games> games){//Executa como se fosse na principal
            gAdapter = new GamesAdapter(mRefActivity.getApplicationContext(), games, mRefActivity);
            mRecyclerView = findViewById(R.id.rvGames);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRefActivity.getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(gAdapter);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }
    }

    public static void openGame(Games game, Activity mActivity) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(mActivity);
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
        Button favoritoBtn = (Button) view.findViewById(R.id.button_favorito);
        Button removeBtn = (Button) view.findViewById(R.id.button_cancelBt);
        Button AccessStoresBtn = (Button) view.findViewById(R.id.button_lojas);
    }

    public static void openChat(Games games, Activity mActivity) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.dialog_accept, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        rvUtilizadores = (RecyclerView) view.findViewById(R.id.rvList);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRefActivity, DividerItemDecoration.VERTICAL);
        rvUtilizadores.addItemDecoration(mDividerItemDecoration);
        TextView textView = (TextView) view.findViewById(R.id.tituloTv);
        Button closeBtn = (Button) view.findViewById(R.id.button_close);
        Button searchButton = (Button) view.findViewById(R.id.button_search);

        LoadMensages listMensagens = new LoadMensages(mActivity, sampleDatabase, mAdapter);
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
        public MensageAdapter mAdapter;

        public LoadMensages(Activity mActivity, MyDB sampleDatabase, MensageAdapter mAdapter) {
            this.mActivity = mActivity;
            this.sampleDatabase = sampleDatabase;
            this.mAdapter = mAdapter;
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
            mAdapter = new MensageAdapter(mRefActivity, list);
            rvUtilizadores.setAdapter(mAdapter);
            rvUtilizadores.setLayoutManager(new LinearLayoutManager(mRefActivity));
        }
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
                Intent i = new Intent(GamesActivity.this, AdminActivity.class);
                i.putExtra("KEY",extras.getString("KEY"));
                startActivity(i);
                return true;
            case R.id.action_definitions:
                Intent y = new Intent(GamesActivity.this, DefenitionActivity.class);
                y.putExtra("KEY","a");
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

        Button idBtn = (Button) view.findViewById(R.id.btn_orderByID);
        Button dateBtn = (Button) view.findViewById(R.id.btn_orderByDate);
        Button nameBtn = (Button) view.findViewById(R.id.btn_orderByName);
        Button confirmarBtn = (Button) view.findViewById(R.id.btn_confirmar);
        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);

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

    private void setClickMenuSearch() {

        AlertDialog.Builder builder = new AlertDialog.Builder(GamesActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_search, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        final EditText tituloChat = (EditText) view.findViewById(R.id.procurar);
        Button confirmar = (Button) view.findViewById(R.id.btn_confirm);
        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
