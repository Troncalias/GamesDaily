package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tronc.gamesdaily.Adapter.GamesEditAdapter;
import com.example.tronc.gamesdaily.Data.List_Games;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.R;

import java.util.ArrayList;

public class DefenitionActivity extends AppCompatActivity {
    private static Activity mRefActivity;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private GamesEditAdapter gAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRefActivity = this;
        Bundle extras = getIntent().getExtras();
            setContentView(R.layout.activity_definitions2);
            setButtons2();/**
            setContentView(R.layout.activity_definitions1);
            setButtons1();**/

        setToolbar();
        setFragments();
    }

    private void setButtons1() {
        setButtonEditUser();
        setButtonAddStore();
    }


    private void setButtons2() {
        setButtonEditUser();
        setButtonAddGames();
        setButtonEditGames();
        setButtonEditStore();
    }

    private void setButtonEditUser() {
        Button editUser = (Button) findViewById(R.id.btn_edit_user);
        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(DefenitionActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_edit_user, null);

                builder.setView(view);
                final AlertDialog dialog = builder.show();
                //nomeUser; edit_email, edit_pass, edit_pass_confirm
                final EditText nomeuser = (EditText) view.findViewById(R.id.nomeUser);
                final EditText editemail = (EditText) view.findViewById(R.id.edit_email);
                final EditText editpass = (EditText) view.findViewById(R.id.edit_pass);
                final EditText editpassconfirm = (EditText) view.findViewById(R.id.edit_pass_confirm);
            }
        });
    }

    private void setButtonAddStore() {
        Button addStore = (Button) findViewById(R.id.btn_add_storeD);
        addStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(DefenitionActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_add_store, null);

                builder.setView(view);
                final AlertDialog dialog = builder.show();

                final EditText nomeLoja = (EditText) view.findViewById(R.id.nomeLoja);
                final EditText descricaoLoja = (EditText) view.findViewById(R.id.descricaoLoja);
                Button button_escolher = (Button) view.findViewById(R.id.button_choose_place);
                Button button_add_image = (Button) view.findViewById(R.id.button_add_image_game);
                Button addBtn = (Button) view.findViewById(R.id.btn_confirm);
                Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);
                final EditText loja = view.findViewById(R.id.lojaTv);
            }
        });
    }

    private void setButtonEditStore() {
        Button editStore = (Button) findViewById(R.id.btn_edit_storeD);
        editStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(DefenitionActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_add_store, null);

                builder.setView(view);
                final AlertDialog dialog = builder.show();
                final EditText nomeLoja = (EditText) view.findViewById(R.id.nomeLoja);
                final EditText descricaoLoja = (EditText) view.findViewById(R.id.descricaoLoja);

                ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
                Button button_add_image = (Button) view.findViewById(R.id.button_add_image);
                Button addBtn = (Button) view.findViewById(R.id.btn_confirm);
                Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);
            }
        });
    }

    private void setButtonAddGames() {
        Button addGames = (Button) findViewById(R.id.btn_add_gamesD);
        addGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(DefenitionActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_add_game, null);

                builder.setView(view);
                final AlertDialog dialog = builder.show();

                final EditText nomeGame = (EditText) view.findViewById(R.id.tituloGame);
                final EditText descricaoGame = (EditText) view.findViewById(R.id.descricaoGame);
                final EditText publicherGame = (EditText) view.findViewById(R.id.publicadorGame);
                Button button_add_image = (Button) view.findViewById(R.id.button_add_image_game);
                Button addBtn = (Button) view.findViewById(R.id.btn_confirm);
                Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);
            }
        });
    }

    private void setButtonEditGames() {
        Button editGames = (Button) findViewById(R.id.btn_edit_gamesD);
        editGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(DefenitionActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_accept, null);
                builder.setView(view);
                final AlertDialog dialog = builder.show();

                mRecyclerView = (RecyclerView) view.findViewById(R.id.rvList);
                DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(DefenitionActivity.this, DividerItemDecoration.VERTICAL);
                mRecyclerView.addItemDecoration(mDividerItemDecoration);
                TextView textView = (TextView) view.findViewById(R.id.tituloTv);
                textView.setText("Accept Game");
                Button closeBtn = (Button) view.findViewById(R.id.button_close);
                Button searchButton = (Button) view.findViewById(R.id.button_search);

                //setListGamesEdit();
            }
        });
    }

    private void setListGamesEdit(){
        ArrayList<Games> contacts = (ArrayList<Games>) new List_Games().getLista_games();
        gAdapter = new GamesEditAdapter(this.getApplicationContext(), contacts, mRefActivity);
        mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(gAdapter);
    }

    private void setToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Defenições");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HeaderFragment f = new HeaderFragment();
        fragmentTransaction.add(R.id.frame_layout_header, f);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_limpo, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}