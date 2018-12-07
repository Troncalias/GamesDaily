package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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

public class GamesActivity extends AppCompatActivity {

    private static Activity mRefActivity;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private GamesAdapter gAdapter;

    public static String getRatingGame(int id) {
        return new List_Games().search(id).getRating();
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
        gAdapter = new GamesAdapter(this, contacts);
        mRecyclerView = findViewById(R.id.rvGames);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(gAdapter);}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        if(extras.getString("KEY") == "admin") {
            getMenuInflater().inflate(R.menu.sub_menu_admin, menu);
        }else if(extras.getString("KEY") == "admin2"){
            getMenuInflater().inflate(R.menu.sub_menu_admin_store_runer, menu);
        }else if (extras.getString("KEY") == "a"){
            getMenuInflater().inflate(R.menu.sub_menu_store_runer, menu);
        }else{
            getMenuInflater().inflate(R.menu.sub_menu, menu);
        }
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
            case R.id.action_add_store:
                return true;
            case R.id.action_store:
                return true;
            default:
                return false;
        }
    }

}
