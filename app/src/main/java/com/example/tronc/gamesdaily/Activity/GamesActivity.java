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
import com.example.tronc.gamesdaily.Data.List_Games;
import com.example.tronc.gamesdaily.Data.List_Mensagens;
import com.example.tronc.gamesdaily.Data.MyDB;
import com.example.tronc.gamesdaily.Data.ValuesBD;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.Models.Mensage;
import com.example.tronc.gamesdaily.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GamesActivity extends AppCompatActivity {

    private ArrayList<Games> listGames;
    private static Activity mRefActivity;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private static RecyclerView rvUtilizadores;
    private GamesAdapter gAdapter;
    private Bundle extras;
    private MyDB sampleDatabase;

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
        ratingTv.setText(Integer.toString(game.getAddBy()));

        final TextView numberOfPlayersTv = (TextView) view.findViewById(R.id.numberOfGamers);
        numberOfPlayersTv.setText(String.valueOf(game.getNumberGamers()));
        final TextView descricao = (TextView) view.findViewById(R.id.descricaoTv);
        descricao.setText(Integer.toString(game.getAddBy()));
        final TextView publicadoraTv = (TextView) view.findViewById(R.id.publicherTv);
        publicadoraTv.setText(Integer.toString(game.getAddBy()));
        Button avaliarBtn = (Button) view.findViewById(R.id.button_avaliar);
        Button favoritoBtn = (Button) view.findViewById(R.id.button_favorito);
        Button removeBtn = (Button) view.findViewById(R.id.button_cancelBt);
        Button AccessStoresBtn = (Button) view.findViewById(R.id.button_lojas);
    }

    public static void openChat(Games games, Activity mActivity) {
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

        setListMensagens();
    }

    private static void setListMensagens() {
        ArrayList<Mensage> list = (ArrayList<Mensage>) new List_Mensagens().getLista_chates();
        MensageAdapter gAdapter = new MensageAdapter(mRefActivity, list);
        rvUtilizadores.setAdapter(gAdapter);
        rvUtilizadores.setLayoutManager(new LinearLayoutManager(mRefActivity));
    }


    private ArrayList<Games> getListGames(){
        return this.listGames;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        mRefActivity = this;

        setToolbar();
        setFragments();
        sampleDatabase = Room.databaseBuilder(getApplicationContext(), MyDB.class, new ValuesBD().getNamedabe()).build();
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
        protected  void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Games> doInBackground(Void... voids) {
            int i = sampleDatabase.geral().getSizeGames();
            i++;    String y1 = String.valueOf(i);
            i++;    String y2 = String.valueOf(i);
            Games game1 = new Games("nome" + y1, "publicador1", "descricao1", "20/10/2019 10:00", null, 1, 100,10);
            Games game2 = new Games("nome" + y2, "publicador2", "descricao2", "20/10/2019 10:00" , null, 2,200, 10);
            sampleDatabase.geral().addGame((game1));
            sampleDatabase.geral().addGame((game2));
            sampleDatabase.geral().deletGame(game2);
            listGames = (ArrayList<Games>) sampleDatabase.geral().loadAllGames();
            return listGames;
        }
        @Override
        protected void onPostExecute(ArrayList<Games> games){//Executa como se fosse na principal
            gAdapter = new GamesAdapter(mRefActivity.getApplicationContext(), games, mRefActivity);
            mRecyclerView = findViewById(R.id.rvGames);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRefActivity.getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(gAdapter);
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
