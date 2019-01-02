package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.tronc.gamesdaily.Adapter.NewsAdapter;
import com.example.tronc.gamesdaily.Data.List_News;
import com.example.tronc.gamesdaily.Data.MyDB;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.API.AppNewsContainer;
import com.example.tronc.gamesdaily.Models.Chat;
import com.example.tronc.gamesdaily.Models.News;
import com.example.tronc.gamesdaily.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , Callback<AppNewsContainer>{
    private MyDB sampleDatabase;
    private static Activity mRefActivity;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private NewsAdapter nAdapter;
    private Bundle extras;
    private List<News> ListNews;
    private DrawerLayout drawer;
    private Context contexto;

    private List<Integer> ids = new ArrayList<>();
    private int idPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mRefActivity = this;
        extras = getIntent().getExtras();
        sampleDatabase = Room.databaseBuilder(getApplicationContext(), MyDB.class, this.getString(R.string.database_value)).build();

        setToolbar();
        setFragments();
        setDrawer();


        ListNews = (List<News>) new List_News().getList_news();
        ids.add(730);
        ids.add(440);

        getNews();
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Game News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void setFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HeaderFragment f = new HeaderFragment();
        fragmentTransaction.add(R.id.frame_layout_header, f);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    private void setDrawer() {
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_viewer);
        navView.setNavigationItemSelectedListener( this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar,
                R.string.navigation_open, R.string.navigation_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    //Lista original
    private void setList() {
        nAdapter = new NewsAdapter(this, ListNews);
        mRecyclerView = findViewById(R.id.rvGames);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(nAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    //Uso da API

    /**
     * Uso da API
     * Inicia aqui ao correr a função getAPI
     */
    private void getNews() {
        if (!ids.isEmpty()) {
            getApi().getListOfNews(ids.get(idPosition), 3, 1).enqueue(this);
            idPosition++;
        }
    }

    private SteamNews getApi() {
        return getRetrofit().create(SteamNews.class);
    }

    private Retrofit getRetrofit() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder().baseUrl("https://api.steampowered.com/ISteamNews/GetNewsForApp/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Override
    public void onResponse(retrofit2.Call<AppNewsContainer> call, Response<AppNewsContainer> response) {
        AppNewsContainer appNewsContainer = response.body();


        for (int i = 0; i <= 2; i++) {
            String Name = String.valueOf(appNewsContainer.getAppnews().getNewsitems("").get(i).getTitle());
            CharSequence content = String.valueOf(appNewsContainer.getAppnews().getNewsitems("").get(i).getContents());
            String data = getDate(appNewsContainer.getAppnews().getNewsitems("").get(i).getDate());

            News news = new News(i, Name, data, stripHtml((String) content));
            ListNews.add(news);
        }

        if (idPosition < ids.size()) {
            getNews();
        } else {
            setList();
        }
    }

    @Override
    public void onFailure(retrofit2.Call<AppNewsContainer> call, Throwable t) {
        Toast.makeText(contexto, "ERRO GET LIST", Toast.LENGTH_SHORT).show();
    }

    public String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.UK);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }


    /**
     * Barra de menu
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (extras.getInt("Type") == 2) {
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
                Intent i = new Intent(NewsActivity.this, AdminActivity.class);
                i.putExtra("KEY", extras.getString("KEY"));
                startActivity(i);
                return true;
            case R.id.action_definitions:
                Intent y = new Intent(NewsActivity.this, DefenitionActivity.class);
                y.putExtra("KEY", "a");
                startActivity(y);
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

    /**
     * Função que executa quando o utlizador deseja realizar a adição de uma chat na app
     */
    private void setClickMenuAddChat() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_chat, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        final EditText tituloChat = (EditText) view.findViewById(R.id.tituloChat);
        final EditText descricao_Chat = (EditText) view.findViewById(R.id.descricaoChat);

        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button addBtn = (Button) view.findViewById(R.id.btn_add_chat);
        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddChat addchat = new AddChat(tituloChat.getText().toString(), descricao_Chat.getText().toString(), dialog);
                addchat.execute();
            }
        });
    }

    public class AddChat extends AsyncTask<Void, Void, Boolean> {
        public String titulo;
        public String descricao;
        public AlertDialog dialog;

        public AddChat(String t, String d, AlertDialog di) {
            titulo = t;
            descricao = d;
            dialog = di;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date hoje = new Date();
            String data = dateFormat.format(hoje);

            //int id_game, String Data,String Titulo, String Descricao
            Chat chat = new Chat(-1, data, titulo, descricao);
            sampleDatabase.geral().addChat(chat);
            return true;
        }

        protected void onPostExecute(Boolean bool){
            Context context = getApplicationContext();
            CharSequence text = "Chat Adicionado";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            dialog.dismiss();
        }
    }

    private void setClickMenuSearch() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_search, null);

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        final EditText tituloChat = (EditText) view.findViewById(R.id.procurar);
        Button confirmar = (Button) view.findViewById(R.id.btn_confirm);
        confirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /**
                 * Realizar o sort
                 * (Por Fazer)
                 */
            }
        });
        Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_games:
                Intent i = new Intent(NewsActivity.this, GamesActivity.class);
                i.putExtra("KEY", extras.getString("KEY"));
                startActivity(i);
                break;
            case R.id.nav_chat:
                Intent e = new Intent(NewsActivity.this, ChatActivity.class);
                e.putExtra("KEY", extras.getString("KEY"));
                startActivity(e);
                break;
            case R.id.nav_store:
                Intent x = new Intent(NewsActivity.this, StoresActivity.class);
                x.putExtra("KEY", extras.getString("KEY"));
                startActivity(x);
                break;
            case R.id.nav_fav:
                Intent g = new Intent(NewsActivity.this, FavoritesActivity.class);
                g.putExtra("KEY", extras.getString("KEY"));
                startActivity(g);
                break;
            case R.id.nav_exit:
                Intent j = new Intent(NewsActivity.this, MainActivity.class);
                j.putExtra("KEY", extras.getString("KEY"));
                startActivity(j);
                break;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
