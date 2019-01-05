package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.example.tronc.gamesdaily.Adapter.ChatRemoveAdapter;
import com.example.tronc.gamesdaily.Adapter.GamesAcceptAdapter;
import com.example.tronc.gamesdaily.Adapter.NewsAcceptAdapter;
import com.example.tronc.gamesdaily.Adapter.StoresAcceptAdapter;
import com.example.tronc.gamesdaily.Adapter.UserAdapter;
import com.example.tronc.gamesdaily.Data.List_News;
import com.example.tronc.gamesdaily.Data.MyDB;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Chat;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.Models.News;
import com.example.tronc.gamesdaily.Models.Stores;
import com.example.tronc.gamesdaily.Models.User;
import com.example.tronc.gamesdaily.Notifications.MyNotification;
import com.example.tronc.gamesdaily.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdminActivity extends AppCompatActivity {
    /**
     *  Constante que guarda o ID do request do imagepicker
     */
    private final int IMAGE_PICKER_REQUEST = 100;
    private MyDB sampleDatabase;
    private static MyDB sampleDatabaseStatic;
    private static GamesAcceptAdapter gAdapterGames;
    private static StoresAcceptAdapter gAdapterStores;
    private static ChatRemoveAdapter gAdapterChats;
    private static UserAdapter gAdapterUsers;

    private static Activity mRefActivity;
    private static RecyclerView rvUtilizadores;
    private static Bundle extras;

    /**
     * Variavel que indica se uma imagem foi escolhida
     */
    private boolean imageIsSet;
    /**
     * Variavel que guarda a imagem escolhida pelo utilizador ao adicionar anime ou loja
     */
    private Uri imageUri;
    /**
     * Variavel que guarda a imagem no seu estado original
     */
    private ImageView imageView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mRefActivity = this;
        sampleDatabase = Room.databaseBuilder(getApplicationContext(), MyDB.class, this.getString(R.string.database_value)).build();
        sampleDatabaseStatic = Room.databaseBuilder(getApplicationContext(), MyDB.class, this.getString(R.string.database_value)).build();
        extras = getIntent().getExtras();

        setToolbar();
        setFragments();
        setButtons();
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Administração");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HeaderFragment f = new HeaderFragment();
        fragmentTransaction.add(R.id.frame_layout_header, f);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    private void setButtons() {
        setButtonGame();
        setButtonStore();
        setButtonChats();
        setButtonUsers();
    }

    private void setButtonGame(){

        final Button btn_add_game = (Button) findViewById(R.id.btn_add_game);
        btn_add_game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_add_game, null);

                builder.setView(view);
                final AlertDialog dialog = builder.show();
                imageIsSet = false;
                final EditText nomeGame = (EditText) view.findViewById(R.id.tituloGame);
                final EditText descricaoGame = (EditText) view.findViewById(R.id.descricaoGame);
                final EditText publicherGame = (EditText) view.findViewById(R.id.publicadorGame);
                imageView = (ImageView) view.findViewById(R.id.imageView);
                final Button button_add_image = (Button) view.findViewById(R.id.button_add_image_game);
                button_add_image.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        openGallery();
                    }
                });

                final Button addBtn = (Button) view.findViewById(R.id.btn_add_game_Admin);
                addBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        AddGame addGame = new AddGame(nomeGame.getText().toString(), descricaoGame.getText().toString(), publicherGame.getText().toString(),dialog);
                        addGame.execute();
                    }
                });

                Button cancelBtn = (Button) view.findViewById(R.id.button_cancel_game_Admin);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        imageIsSet = false;
                        dialog.dismiss();
                    }
                });


            }
        });

        Button btn_accept_game = (Button) findViewById(R.id.btn_accept_game);
        btn_accept_game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_accept_simpler, null);
                builder.setView(view);
                final AlertDialog dialog = builder.show();

                rvUtilizadores = (RecyclerView) view.findViewById(R.id.rvList);
                DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(AdminActivity.this, DividerItemDecoration.VERTICAL);
                rvUtilizadores.addItemDecoration(mDividerItemDecoration);
                TextView textView = (TextView) view.findViewById(R.id.tituloTv);
                textView.setText("Accept Game");
                Button closeBtn = (Button) view.findViewById(R.id.button_close);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                LoadGames listGames = new LoadGames();
                listGames.execute();
            }
        });


    }

    public class AddGame extends AsyncTask<Void, Void, Boolean> {
        public String Titulo;
        public String Descricao;
        public String Publicher;
        public AlertDialog Dialog;

        public AddGame(String titulo, String descricao, String publicher, AlertDialog dialog) {
            Titulo = titulo;
            Descricao = descricao;
            Publicher = publicher;
            Dialog = dialog;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date hoje = new Date();
            String data = dateFormat.format(hoje);

            Games game;
            if(imageIsSet){
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                game = new Games(Titulo, Publicher, Descricao, data, imageBytes, 0, 0, true);
                sampleDatabase.geral().addGame(game);
            }else{
                game = new Games(Titulo, Publicher, Descricao, data, null, 0, 0, true);
                sampleDatabase.geral().addGame(game);
            }

            ArrayList<Games> list = (ArrayList<Games>) sampleDatabase.geral().loadAllGames();
            sampleDatabase.geral().addChat(new Chat(list.get(list.size()-1).getId(), game.getDate(), "Chat de " + game.getName(), "Opiniões do jogo " + game.getName()));
            return true;
        }

        protected void onPostExecute(Boolean bool){
            Context context = getApplicationContext();
            CharSequence text = "Jogo Adicionado";
            int duration = Toast.LENGTH_SHORT;
            imageIsSet = false;

            MyNotification.addGameNotification(AdminActivity.this, Titulo, Descricao, getString(R.string.notification_add_jogo_title), getApplicationContext());
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Dialog.dismiss();
        }
    }

    public class LoadGames extends AsyncTask<Void, Void, ArrayList<Games>> {

        @Override
        protected ArrayList<Games> doInBackground(Void... voids) {
            java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date hoje = new Date();
            String data = dateFormat.format(hoje);
            //String name, String publicher, String description, String date, byte[] imagem, int numberGamers, float rating, boolean acepted
            Games game1 = new Games("Tests 1", "Pub1", "Descrpt1", data, null, 0, 0, false);
            Games game2 = new Games("Tests 2", "Pub1", "Descrpt1", data, null, 0, 0, false);
            sampleDatabase.geral().addGame(game1);
            sampleDatabase.geral().addGame(game2);
            ArrayList<Games> list = (ArrayList<Games>) sampleDatabase.geral().loadAllGamesAcepted(false);
            return list;
        }

        protected void onPostExecute(ArrayList<Games> list){
            gAdapterGames = new GamesAcceptAdapter(mRefActivity, list);
            rvUtilizadores.setAdapter(gAdapterGames);
            rvUtilizadores.setLayoutManager(new LinearLayoutManager(mRefActivity));
        }
    }

    public static void DecisionGame(Games myItem, boolean b) {
        EditGames listGame = new EditGames(myItem, sampleDatabaseStatic, b, gAdapterGames);
        listGame.execute();
    }

    public static class EditGames extends AsyncTask<Void, Void, ArrayList<Games>> {
        public Games game;
        public MyDB database;
        public boolean decision;
        public GamesAcceptAdapter gAdapter;


        public EditGames(Games myItem, MyDB sampleDatabaseStatic, boolean b, GamesAcceptAdapter gAdapter) {
            game = myItem;
            database = sampleDatabaseStatic;
            decision = b;
            this.gAdapter = gAdapter;
        }

        @Override
        protected ArrayList<Games> doInBackground(Void... voids) {
            if(decision){
                game.setAcepted(true);
                database.geral().updateGame(game);
                database.geral().addChat(new Chat(game.getId(), game.getDate(), "Chat de " + game.getName(), "Opiniões do jogo " + game.getName()));
            }else{
                database.geral().deleteGame(game);
            }
            ArrayList<Games> list = (ArrayList<Games>) database.geral().loadAllGamesAcepted(false);
            return list;
        }

        protected void onPostExecute(ArrayList<Games> list){
            if(decision){
                MyNotification.addGameNotification(mRefActivity, game.getName(), game.getDescription(), "Novo Jogo Adicionado", mRefActivity);
            }else{
                MyNotification.addGameNotification(mRefActivity, game.getName(), game.getDescription(), "Jogo Não Adicionado", mRefActivity);
            }
            gAdapter.setList(list);
        }
    }

    private void setButtonStore(){
        Button btn_accept_store = (Button) findViewById(R.id.btn_accept_store);
        btn_accept_store.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_accept_simpler, null);
                builder.setView(view);
                final AlertDialog dialog = builder.show();

                rvUtilizadores = (RecyclerView) view.findViewById(R.id.rvList);
                DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(AdminActivity.this, DividerItemDecoration.VERTICAL);
                rvUtilizadores.addItemDecoration(mDividerItemDecoration);

                TextView textView = (TextView) view.findViewById(R.id.tituloTv);
                textView.setText("Accept Store");

                Button closeBtn = (Button) view.findViewById(R.id.button_close);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                LoadStores listStores = new LoadStores();
                listStores.execute();
            }
        });
    }

    public class LoadStores extends AsyncTask<Void, Void, ArrayList<Stores>> {

        @Override
        protected ArrayList<Stores> doInBackground(Void... voids) {
            int i = sampleDatabase.geral().getSizeStores();
            i++;    String y1 = String.valueOf(i);
            i++;    String y2 = String.valueOf(i);
            Stores stores = new Stores("Loja " + y1, "Rua de Lagoas, Lousada", "Loja de Jogos Steam", "20/10/2019 10:00", null, "admin", false);
            sampleDatabase.geral().addStore(stores);
            stores = new Stores("Loja " + y2, "Rua Caminhos de S. Tiago, Ferreira", "Loja de Jogos Local", "30/1/2018 10:00", null, "admin", false);
            sampleDatabase.geral().addStore(stores);
            ArrayList<Stores> list = (ArrayList<Stores>) sampleDatabase.geral().loadAllStoresAcepted(false);
            return list;
        }

        protected void onPostExecute(ArrayList<Stores> list){
            gAdapterStores = new StoresAcceptAdapter(mRefActivity, list);
            rvUtilizadores.setAdapter(gAdapterStores);
            rvUtilizadores.setLayoutManager(new LinearLayoutManager(mRefActivity));

        }
    }

    public static void DecisionStores(Stores myItem, boolean b) {
        EditStores listStores = new EditStores(myItem, sampleDatabaseStatic, b, gAdapterStores);
        listStores.execute();

    }

    public static class EditStores extends AsyncTask<Void, Void, ArrayList<Stores>> {
        public Stores store;
        public MyDB database;
        public boolean decision;
        public StoresAcceptAdapter gAdapter;


        public EditStores(Stores myItem, MyDB sampleDatabaseStatic, boolean b, StoresAcceptAdapter gAdapter) {
            store = myItem;
            database = sampleDatabaseStatic;
            decision = b;
            this.gAdapter = gAdapter;
        }

        @Override
        protected ArrayList<Stores> doInBackground(Void... voids) {
            if(decision){
                store.setAcepted(true);
                database.geral().updateStores(store);
            }else{
                database.geral().deleteStore(store);
            }
            ArrayList<Stores> list = (ArrayList<Stores>) database.geral().loadAllStoresAcepted(false);
            return list;
        }

        protected void onPostExecute(ArrayList<Stores> list){
            if(decision){
                MyNotification.addStoreNotification(mRefActivity, store.getNome(), store.getDescricao(), "Nova Loja Adicionado", mRefActivity);
            }else{
                MyNotification.addStoreNotification(mRefActivity, store.getNome(), store.getDescricao(), "Loja Não Aceite", mRefActivity);
            }
            gAdapter.setList(list);
        }
    }

    private void setButtonChats() {
        Button btn_add_chat = (Button) findViewById(R.id.btn_remove_chat);
        btn_add_chat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_accept_simpler, null);
                builder.setView(view);
                final AlertDialog dialog = builder.show();


                rvUtilizadores = (RecyclerView) view.findViewById(R.id.rvList);
                DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(AdminActivity.this, DividerItemDecoration.VERTICAL);
                rvUtilizadores.addItemDecoration(mDividerItemDecoration);

                TextView textView = (TextView) view.findViewById(R.id.tituloTv);
                textView.setText("Remove Chat");
                Button closeBtn = (Button) view.findViewById(R.id.button_close);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                LoadChats listChats = new LoadChats();
                listChats.execute();
            }
        });
    }

    public class LoadChats extends AsyncTask<Void, Void, ArrayList<Chat>> {

        @Override
        protected ArrayList<Chat> doInBackground(Void... voids) {
            ArrayList<Chat> listChat = (ArrayList<Chat>) sampleDatabase.geral().loadAllChatsNormal(-1);
            return listChat;
        }

        protected void onPostExecute(ArrayList<Chat> list){
            gAdapterChats = new ChatRemoveAdapter(mRefActivity, list);
            rvUtilizadores.setAdapter(gAdapterChats);
            rvUtilizadores.setLayoutManager(new LinearLayoutManager(mRefActivity));
        }
    }

    public static void RemoveChat(Chat myItem) {
        EditChats listChats = new EditChats(myItem, sampleDatabaseStatic, gAdapterChats);
        listChats.execute();
    }

    public static class EditChats extends AsyncTask<Void, Void, ArrayList<Chat>> {
        public Chat store;
        public MyDB database;
        public ChatRemoveAdapter gAdapter;


        public EditChats(Chat myItem, MyDB sampleDatabaseStatic, ChatRemoveAdapter gAdapter) {
            store = myItem;
            database = sampleDatabaseStatic;
            this.gAdapter = gAdapter;
        }

        @Override
        protected ArrayList<Chat> doInBackground(Void... voids) {
            database.geral().deleteChat(store);
            ArrayList<Chat> list = (ArrayList<Chat>) database.geral().loadAllChatsNormal(-1);
            return list;
        }

        protected void onPostExecute(ArrayList<Chat> list){
            gAdapter.setList(list);
        }
    }

    /**
     * Funções dedicadas a gestão de users
     */
    private void setButtonUsers() {
        Button btn_utilizadores = (Button) findViewById(R.id.btn_remove_users);
        btn_utilizadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_accept_simpler, null);
                builder.setView(view);
                final AlertDialog dialog = builder.show();

                rvUtilizadores = (RecyclerView) view.findViewById(R.id.rvList);
                DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(AdminActivity.this, DividerItemDecoration.VERTICAL);
                rvUtilizadores.addItemDecoration(mDividerItemDecoration);

                TextView textView = (TextView) view.findViewById(R.id.tituloTv);
                textView.setText("Remove Users");
                Button closeBtn = (Button) view.findViewById(R.id.button_close);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                LoadUsers list = new LoadUsers();
                list.execute();
            }
        });
    }

    public class LoadUsers extends AsyncTask<Void, Void, ArrayList<User>> {

        @Override
        protected ArrayList<User> doInBackground(Void... voids) {
            ArrayList<User> listUsers = (ArrayList<User>) sampleDatabase.geral().loadAllUsers();
            return listUsers;
        }

        protected void onPostExecute(ArrayList<User> list){
            gAdapterUsers = new UserAdapter(mRefActivity, list);
            rvUtilizadores.setAdapter(gAdapterUsers);
            rvUtilizadores.setLayoutManager(new LinearLayoutManager(mRefActivity));
        }
    }

    public static void RemoveUser(User myItem) {
        if(!myItem.getUsername().equals(extras.getString("KEY")) || !myItem.getUsername().equals("Admin")){
            RemoveUser listUser = new RemoveUser(myItem, sampleDatabaseStatic, gAdapterUsers);
            listUser.execute();
        }
    }

    public static class RemoveUser extends AsyncTask<Void, Void, ArrayList<User>> {
        public User user;
        public MyDB database;
        public UserAdapter gAdapter;


        public RemoveUser(User myItem, MyDB sampleDatabaseStatic, UserAdapter gAdapter) {
            user = myItem;
            database = sampleDatabaseStatic;
            this.gAdapter = gAdapter;
        }

        @Override
        protected ArrayList<User> doInBackground(Void... voids) {
            database.geral().deleteUser(user);
            ArrayList<User> list = (ArrayList<User>) database.geral().loadAllUsers();
            return list;
        }

        protected void onPostExecute(ArrayList<User> list){
            gAdapter.setList(list);
        }
    }

    public static void MakeAdmin(User myItem) {
        MakeAdmin listUsers = new MakeAdmin(myItem, sampleDatabaseStatic, gAdapterUsers, extras.getString("KEY"));
        listUsers.execute();
    }

    public static class MakeAdmin extends AsyncTask<Void, Void, ArrayList<User>> {
        public User user;
        public MyDB database;
        public UserAdapter gAdapter;
        public boolean resultado=false;
        public String username;


        public MakeAdmin(User myItem, MyDB sampleDatabaseStatic, UserAdapter gAdapter, String username) {
            user = myItem;
            database = sampleDatabaseStatic;
            this.gAdapter = gAdapter;
            this.username = username;
        }

        @Override
        protected ArrayList<User> doInBackground(Void... voids) {
            if(user.getTipo_utilizador_id()==1){
                user.setTipo_utilizador_id(2);
                database.geral().updateUser(user);
                resultado=true;
            }else{
                if(!user.getUsername().equals(username)){
                    user.setTipo_utilizador_id(1);
                    database.geral().updateUser(user);
                    resultado = true;
                }
            }
            ArrayList<User> list = (ArrayList<User>) database.geral().loadAllUsers();
            return list;
        }

        protected void onPostExecute(ArrayList<User> list){
            if(resultado){
                gAdapter.setList(list);
            }
        }
    }

    private void openGallery() {
        MainActivity.requestStoragePermissions(AdminActivity.this);

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, IMAGE_PICKER_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){

        if(requestCode == IMAGE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){
                imageIsSet = true;
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    /**
     * Funções da barra de navegação
     * TODAS A FUNCIONAR
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_limpo, menu);
        return true;
    }

}

