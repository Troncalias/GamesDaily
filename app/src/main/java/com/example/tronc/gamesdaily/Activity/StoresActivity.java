package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
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
import com.example.tronc.gamesdaily.Adapter.StoresAdapter;
import com.example.tronc.gamesdaily.Data.MyDB;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.Models.Stores;
import com.example.tronc.gamesdaily.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class StoresActivity extends AppCompatActivity {

    private static Activity mRefActivity;
    private static RecyclerView rvUtilizadores;
    private static RecyclerView mRecyclerView;
    private static GamesAdapter gAdapter;
    private static MyDB sampleDatabase;
    private Toolbar mToolbar;
    private StoresAdapter sAdapter;
    private Bundle extras;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        mRefActivity = this;
        sampleDatabase = Room.databaseBuilder(getApplicationContext(), MyDB.class, this.getString(R.string.database_value)).build();
        mContext = this;

        setToolbar();
        setFragments();
        LoadStores listStores = new LoadStores();
        listStores.execute();
    }

    private void setToolbar() {
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

    public class LoadStores extends AsyncTask<Void, Void, ArrayList<Stores>> {

        @Override
        protected ArrayList<Stores> doInBackground(Void... voids) {
            /**
             ArrayList<Stores> listStores = (ArrayList<Stores>) sampleDatabase.geral().loadAllStores();
             for(int a=0; a<listStores.size(); a++){
             sampleDatabase.geral().deleteStore(listStores.get(a));
             }**/

            ArrayList<Stores> list = (ArrayList<Stores>) sampleDatabase.geral().loadAllStoresAcepted(true);
            return list;
        }

        protected void onPostExecute(ArrayList<Stores> list) {
            sAdapter = new StoresAdapter(mRefActivity.getApplicationContext(), list, mRefActivity);
            mRecyclerView = findViewById(R.id.rvGames);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRefActivity, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(sAdapter);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        if (extras.getString("KEY").equals("admin")) {
            getMenuInflater().inflate(R.menu.menu_admin, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
                Intent i = new Intent(StoresActivity.this, AdminActivity.class);
                i.putExtra("KEY", extras.getString("KEY"));
                startActivity(i);
                return true;
            case R.id.action_definitions:
                Intent y = new Intent(StoresActivity.this, DefenitionActivity.class);
                y.putExtra("KEY", "a");
                startActivity(y);
                return true;
            default:
                return false;
        }
    }

    private void setClickMenuSort() {

        AlertDialog.Builder builder = new AlertDialog.Builder(StoresActivity.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(StoresActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_chat, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        final EditText tituloChat = (EditText) view.findViewById(R.id.tituloChat);
        final EditText descricao_Chat = (EditText) view.findViewById(R.id.descricaoChat);
        Button addBtn = (Button) view.findViewById(R.id.btn_add_chat);
        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);

    }

    private void setClickMenuSearch() {

        AlertDialog.Builder builder = new AlertDialog.Builder(StoresActivity.this);
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

    public static void openGame(Stores stores, Activity mActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.dialog_store, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();
        final ImageView imageView = view.findViewById(R.id.imageView);
        if (stores.getImagem() != null) {
            ByteArrayInputStream imageStream = new ByteArrayInputStream(stores.getImagem());
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(theImage);
        }

        final TextView nomeTv = (TextView) view.findViewById(R.id.nomeTv);
        nomeTv.setText(stores.getNome());
        final TextView dataInsercaoTv = (TextView) view.findViewById(R.id.moradaTv);
        dataInsercaoTv.setText(stores.getMorada());


        final TextView ratingTv = (TextView) view.findViewById(R.id.descricaoTv);
        ratingTv.setText(stores.getDescricao());
    }


    public static void openGames(Activity mActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.dialog_accept, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();
        builder.setView(view);

        rvUtilizadores = (RecyclerView) view.findViewById(R.id.rvList);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRefActivity, DividerItemDecoration.VERTICAL);
        rvUtilizadores.addItemDecoration(mDividerItemDecoration);
        TextView textView = (TextView) view.findViewById(R.id.tituloTv);
        Button closeBtn = (Button) view.findViewById(R.id.button_close);
        Button searchButton = (Button) view.findViewById(R.id.button_search);

        LoadGames listGames = new LoadGames(mActivity, sampleDatabase);
        listGames.execute();

    }

    public static void openMap(String m) {
       /** AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        TextView text = (TextView) findViewById(R.id.tv1);
        TextView morada
        String s = (TextView) edit.getText().toString();

        final TextView dataInsercaoTv = findViewById(R.id.moradaTv);
        dataInsercaoTv.toString(); */
        Intent x = new Intent(mContext, MapsActivity.class);
        x.putExtra("morada", m);
        mContext.startActivity(x);
    }

    /**
     * private static void setListMensagens(Activity activity) {
     * ArrayList<Games> contacts = (ArrayList<Games>) new List_Games().getLista_games();
     * GamesAdapter adapter = new GamesAdapter(activity, contacts, mRefActivity);
     * rvUtilizadores.setAdapter(adapter);
     * rvUtilizadores.setLayoutManager(new LinearLayoutManager(activity));
     * }
     **/

    public static class LoadGames extends AsyncTask<Void, Void, ArrayList<Games>> {
        public Activity mActivity;
        public MyDB sampleDatabase;

        public LoadGames(Activity mActivity, MyDB database) {
            this.sampleDatabase = database;
            this.mActivity = mActivity;
        }

        @Override
        protected ArrayList<Games> doInBackground(Void... voids) {
            ArrayList<Games> listGames = (ArrayList<Games>) sampleDatabase.geral().loadAllGamesAcepted(true);
            return listGames;
        }

        @Override
        protected void onPostExecute(ArrayList<Games> games) {
            GamesAdapter adapter = new GamesAdapter(mActivity, games, mRefActivity);
            rvUtilizadores.setAdapter(adapter);
            rvUtilizadores.setLayoutManager(new LinearLayoutManager(mActivity));
        }
    }
}
