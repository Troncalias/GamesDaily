package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tronc.gamesdaily.Adapter.GamesAdapter;
import com.example.tronc.gamesdaily.Adapter.StoresAdapter;
import com.example.tronc.gamesdaily.Data.List_Games;
import com.example.tronc.gamesdaily.Data.List_Stores;
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
    private Toolbar mToolbar;
    private StoresAdapter sAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        mRefActivity = this;

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
        sAdapter = new StoresAdapter(this.getApplicationContext(), contacts, mRefActivity);
        mRecyclerView = findViewById(R.id.rvGames);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(sAdapter);}

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void openGame(Stores stores, Activity mActivity) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.dialog_store, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();
        final ImageView imageView = view.findViewById(R.id.imageView);
        if(stores.getImagem() != null){
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
        AlertDialog.Builder builder =  new AlertDialog.Builder(mActivity);
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

        setListMensagens(mActivity);
    }

    private static void setListMensagens(Activity activity) {
        ArrayList<Games> contacts = (ArrayList<Games>) new List_Games().getLista_games();
        GamesAdapter adapter = new GamesAdapter(activity, contacts, mRefActivity);
        rvUtilizadores.setAdapter(adapter);
        rvUtilizadores.setLayoutManager(new LinearLayoutManager(activity));
    }
}
