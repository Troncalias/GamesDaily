package com.example.tronc.gamesdaily.Activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tronc.gamesdaily.Adapter.StoresAdapter;
import com.example.tronc.gamesdaily.Data.List_Stores;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Stores;
import com.example.tronc.gamesdaily.R;

import java.util.List;

public class StoresActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private StoresAdapter sAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);

        setToolbar();
        setFragments();
        setList();
    }

    private void setToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("LOJAS");
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
        List<Stores> contacts = (List<Stores>) new List_Stores().getLista_stores();
        sAdapter = new StoresAdapter(this, contacts);
        mRecyclerView = findViewById(R.id.rvGames);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(sAdapter);}

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
