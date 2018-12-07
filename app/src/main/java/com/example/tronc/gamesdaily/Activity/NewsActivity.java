package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.tronc.gamesdaily.Adapter.NewsAdapter;
import com.example.tronc.gamesdaily.Data.List_News;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.News;
import com.example.tronc.gamesdaily.R;

import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private static Activity mRefActivity;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private NewsAdapter nAdapter;
    private Bundle extras;
    private List<News> ListNews;

    public static String getRatingNews(int id) {
        return new List_News().search(id).getRating();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mRefActivity = this;
        extras = getIntent().getExtras();
        ListNews = (List<News>) new List_News().getList_news();

        setToolbar();
        setFragments();
        setButtons();
        setList();


        nAdapter = new NewsAdapter(this, ListNews);
        mRecyclerView = findViewById(R.id.rvGames);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(nAdapter);

    }

    private void setFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HeaderFragment f = new HeaderFragment();
        fragmentTransaction.add(R.id.frame_layout_header, f);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    private void setButtons() {
        Button bGames = findViewById(R.id.btn_jogos);
        bGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewsActivity.this, GamesActivity.class);
                i.putExtra("KEY",extras.getString("KEY"));
                startActivity(i);
            }
        });

        Button bChates = findViewById(R.id.btn_chates);
        bChates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewsActivity.this, ChatActivity.class);
                i.putExtra("KEY",extras.getString("KEY"));
                startActivity(i);
            }
        });

        Button bLojas = findViewById(R.id.btn_lojas);
        bLojas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewsActivity.this, StoresActivity.class);
                i.putExtra("KEY",extras.getString("KEY"));
                startActivity(i);
            }
        });

        Button bFavoritos = findViewById(R.id.btn_favoritos);
        bFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewsActivity.this, FavoritesActivity.class);
                i.putExtra("KEY",extras.getString("KEY"));
                startActivity(i);
            }
        });
    }

    private void setList() {
        nAdapter = new NewsAdapter(this, ListNews);
        mRecyclerView = findViewById(R.id.rvGames);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(nAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(extras.getString("KEY").equals("admin")) {
            getMenuInflater().inflate(R.menu.admin_menu, menu);
        }else if(extras.getString("KEY").equals("admin2")){
            getMenuInflater().inflate(R.menu.admin_menu_store_runer, menu);
        }else if (extras.getString("KEY").equals("a")){
            getMenuInflater().inflate(R.menu.main_menu_store_runer, menu);
        }else{
            getMenuInflater().inflate(R.menu.main_menu, menu);
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
                startActivity(new Intent(NewsActivity.this, AdminActivity.class));
                return true;
            case R.id.action_add_store:
                setClickMenuAddStore();
                return true;
            case R.id.action_store:

                return true;
            default:
                return false;
        }
    }

    private void setClickMenuSort() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_chat, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        final EditText tituloChat = (EditText) view.findViewById(R.id.tituloChat);
        final EditText descricao_Chat = (EditText) view.findViewById(R.id.descricaoChat);
        Button addBtn = (Button) view.findViewById(R.id.btn_add_chat);
        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);

    }

    private void setClickMenuAddStore() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_store, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        final EditText nomeLoja = (EditText) view.findViewById(R.id.nomeLoja);
        final EditText descricaoLoja = (EditText) view.findViewById(R.id.descricaoLoja);
        final EditText localLoja = (EditText) view.findViewById(R.id.placeTv);
        Button button_escolher = (Button) view.findViewById(R.id.button_choose_place);
        Button button_add_image = (Button) view.findViewById(R.id.button_add_image_game);
        Button addBtn = (Button) view.findViewById(R.id.btn_confirm);
        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);

    }

    private void setClickMenuSearch() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_search, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        final EditText tituloChat = (EditText) view.findViewById(R.id.procurar);
        Button confirmar = (Button) view.findViewById(R.id.btn_confirm);
        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);

    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Game News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}
