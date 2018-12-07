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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tronc.gamesdaily.Adapter.ChatAdapter;
import com.example.tronc.gamesdaily.Adapter.MensageAdapter;
import com.example.tronc.gamesdaily.Data.List_Chats;
import com.example.tronc.gamesdaily.Data.List_Mensagens;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Chat;
import com.example.tronc.gamesdaily.Models.Mensage;
import com.example.tronc.gamesdaily.R;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private static Activity mRefActivity;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private static RecyclerView rvUtilizadores;
    private ChatAdapter gAdapter;

    public static void openChat(Chat myItem, Activity mActivity) {
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
        setContentView(R.layout.activity_chat);
        mRefActivity = this;

        setToolbar();
        setFragments();
        setList();

    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Chats");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HeaderFragment f = new HeaderFragment();
        fragmentTransaction.add(R.id.frame_layout_header, f);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    private void setList() {
        ArrayList<Chat> contacts = (ArrayList<Chat>) new List_Chats().getLista_chates();
        gAdapter = new ChatAdapter(this.getApplicationContext(), contacts, this);
        mRecyclerView = findViewById(R.id.rvChat);
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

