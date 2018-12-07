package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.Models.Mensage;
import com.example.tronc.gamesdaily.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GamesActivity extends AppCompatActivity {

    private static Activity mRefActivity;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private static RecyclerView rvUtilizadores;
    private GamesAdapter gAdapter;
    private Bundle extras;

    public static String getRatingGame(int id) {
        return new List_Games().search(id).getRating();
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
        nomeTv.setText(game.getNome());
        final TextView dataInsercaoTv = (TextView) view.findViewById(R.id.dataInsercaoTv);
        dataInsercaoTv.setText(game.getDataInsercao());
        final TextView ratingTv = (TextView) view.findViewById(R.id.ratingTv);
        ratingTv.setText(game.getUtilizador_adicionou());

        final TextView numberOfPlayersTv = (TextView) view.findViewById(R.id.numberOfGamers);
        numberOfPlayersTv.setText(String.valueOf(game.getNumber_jogadores()));
        final TextView descricao = (TextView) view.findViewById(R.id.descricaoTv);
        descricao.setText(game.getUtilizador_adicionou());
        final TextView publicadoraTv = (TextView) view.findViewById(R.id.publicherTv);
        publicadoraTv.setText(game.getUtilizador_adicionou());
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


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        mRefActivity = this;

        setToolbar();
        setFragments();
        setList();

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

    private void setList(){
        ArrayList<Games> contacts = (ArrayList<Games>) new List_Games().getLista_games();
        gAdapter = new GamesAdapter(this.getApplicationContext(), contacts, mRefActivity);
        mRecyclerView = findViewById(R.id.rvGames);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(gAdapter);}

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
