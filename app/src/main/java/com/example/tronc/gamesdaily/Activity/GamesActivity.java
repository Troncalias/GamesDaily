package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import com.example.tronc.gamesdaily.Data.List_Games;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class GamesActivity extends AppCompatActivity {

    private static Activity mRefActivity;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private GamesAdapter gAdapter;

    public static String getRatingGame(int id) {
        return new List_Games().search(id).getRating();
    }

    public static void openAnime(Games game, Activity mActivity) {
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
            getMenuInflater().inflate(R.menu.sub_menu_admin, menu);
        }else if(extras.getString("KEY").equals("admin2")){
            getMenuInflater().inflate(R.menu.sub_menu_admin_store_runer, menu);
        }else if (extras.getString("KEY").equals("a")){
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
