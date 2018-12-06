package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


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

    public static String getRatingNews(int id) {
        return new List_News().search(id).getRating();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mRefActivity = this;

        setToolbar();
        setFragments();
        setButtons();
        setList();


        List<News> contacts = (List<News>) new List_News().getList_news();
        nAdapter = new NewsAdapter(this, contacts);
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
                startActivity(new Intent(NewsActivity.this, GamesActivity.class));
            }
        });

        Button bChates = findViewById(R.id.btn_chates);
        bChates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button bNoticias = findViewById(R.id.btn_lojas);
        bNoticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button bFavoritos = findViewById(R.id.btn_favoritos);
        bNoticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setList() {
        List<News> contacts = (List<News>) new List_News().getList_news();
        nAdapter = new NewsAdapter(this, contacts);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;


    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Game News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}
