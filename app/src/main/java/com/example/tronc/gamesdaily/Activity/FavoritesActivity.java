package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tronc.gamesdaily.Adapter.GamesAdapter;
import com.example.tronc.gamesdaily.Data.List_Games;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.R;


import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private static Activity mRefActivity;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private GamesAdapter gAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        mRefActivity = this;

        setToolbar();
        setFragments();
        setList();


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

    private void setList() {
        List<Games> contacts = (List<Games>) new List_Games().getLista_games();
        gAdapter = new GamesAdapter(this.getApplicationContext(), (ArrayList<Games>) contacts, mRefActivity);
        mRecyclerView = findViewById(R.id.rvGames);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(gAdapter);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_sort:
                return true;
            case R.id.action_add:
                return true;
            case R.id.action_definitions:
                return true;
            default:
                return false;
        }
    }

}
