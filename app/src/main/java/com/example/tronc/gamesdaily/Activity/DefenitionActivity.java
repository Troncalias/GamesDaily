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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tronc.gamesdaily.Adapter.ChatRemoveAdapter;
import com.example.tronc.gamesdaily.Adapter.GamesAcceptAdapter;
import com.example.tronc.gamesdaily.Adapter.StoresAcceptAdapter;
import com.example.tronc.gamesdaily.Adapter.UserAdapter;
import com.example.tronc.gamesdaily.Data.MyDB;
import com.example.tronc.gamesdaily.Fragment.HeaderFragment;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.Models.Stores;
import com.example.tronc.gamesdaily.Models.User;
import com.example.tronc.gamesdaily.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DefenitionActivity extends AppCompatActivity {
    /**
     *  Constante que guarda o ID do request do imagepicker
     */
    private final int IMAGE_PICKER_REQUEST = 100;
    private static MyDB sampleDatabase;
    private static GamesAcceptAdapter gAdapterGames;
    private static StoresAcceptAdapter gAdapterStores;
    private static ChatRemoveAdapter gAdapterChats;
    private static UserAdapter gAdapterUsers;

    private static Activity mRefActivity;
    private static RecyclerView rvUtilizadores;
    private static Bundle extras;
    private static boolean aVereficar;
    private static Context mContext;

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

    private static String user;
    private static User utilizador;
    private static Stores stores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRefActivity = this;
        extras = getIntent().getExtras();
        user = extras.getString("KEY");
        sampleDatabase = Room.databaseBuilder(getApplicationContext(), MyDB.class, this.getString(R.string.database_value)).build();
        mContext = this;

        LoadStats loading = new LoadStats(extras.getString("KEY"));
        loading.execute();
    }

    public class LoadStats extends AsyncTask<Void, Void, ArrayList<Stores>> {
        public String user;

        public LoadStats(String user) {
            this.user = user;
        }

        @Override
        protected ArrayList<Stores> doInBackground(Void... voids) {
            ArrayList<User> listUser = (ArrayList<User>) sampleDatabase.geral().getUserByName(this.user);
            utilizador = listUser.get(0);

            ArrayList<Stores> listStores = (ArrayList<Stores>) sampleDatabase.geral().getStoresByUser(this.user);
            return listStores;
        }
        @Override
        protected void onPostExecute(ArrayList<Stores> list){//Executa como se fosse na principal
            if(list.size() == 0){
                steType1();
                aVereficar = false;
            }else if(list.get(0).isAcepted()){
                stores = list.get(0);
                steType2();
            }else{
                steType1();
                aVereficar = true;
            }

        }
    }

    public void steType1(){
        setContentView(R.layout.activity_definitions1);
        setButtonEditUser();
        setButtonAddStore();

        setToolbar();
        setFragments();
    }

    public void steType2(){
        setContentView(R.layout.activity_definitions2);
        setButtonEditUser();
        setButtonAddGames();
        setButtonEditGames();
        setButtonEditStore();

        setToolbar();
        setFragments();
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Definições");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HeaderFragment f = new HeaderFragment();
        fragmentTransaction.add(R.id.frame_layout_header, f);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    /**
     * Pro Fazer
     */
    private void setButtonEditUser() {
        Button editUser = (Button) findViewById(R.id.btn_edit_user);
        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(DefenitionActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_edit_user, null);

                builder.setView(view);
                final AlertDialog dialog = builder.show();
                final EditText nomeuser = (EditText) view.findViewById(R.id.nomeUser);
                nomeuser.setText(utilizador.getUsername());
                final EditText editemail = (EditText) view.findViewById(R.id.edit_email);
                editemail.setText(utilizador.getEmail());
                final EditText editpass = (EditText) view.findViewById(R.id.edit_pass);
                editpass.setText(utilizador.getPassword());
                final EditText editpassconfirm = (EditText) view.findViewById(R.id.edit_pass_confirm);

                final Button addImagem = (Button) view.findViewById(R.id.button_add_image);
                addImagem.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        openGallery();
                    }
                });

                final Button confirmar = (Button) view.findViewById(R.id.btn_confirm);
                confirmar.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        /**
                         *
                         */
                    }
                });

                final Button cancelar = (Button) view.findViewById(R.id.button_cancel);
                cancelar.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                        imageIsSet = false;
                    }
                });

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
                final EditText ruaLoja = (EditText) view.findViewById(R.id.ruaLoja);
                final EditText localidadeLoja = (EditText) view.findViewById(R.id.localidadeLoja);
                final Button verefy = (Button) view.findViewById(R.id.button_verefy_place);
                verefy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String morada = ruaLoja.getText().toString() + ", " + localidadeLoja.getText().toString();
                        openMap(morada, "Tets", "Description");
                    }
                });

                imageView = (ImageView) view.findViewById(R.id.imageView);
                final Button button_add_image = (Button) view.findViewById(R.id.button_add_image);

                button_add_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                    }
                });

                Button addBtn = (Button) view.findViewById(R.id.btn_confirm);
                addBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String morada = ruaLoja.getText().toString() + ", " + localidadeLoja.getText().toString();
                        AddStore listSotre = new AddStore(nomeLoja.getText().toString(), descricaoLoja.getText().toString(), morada, dialog);
                        listSotre.execute();

                    }
                });

                Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        imageIsSet = false;
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public class AddStore extends AsyncTask<Void, Void, Boolean> {
        public String Titulo;
        public String Descricao;
        public String Localizacao;
        public AlertDialog Dialog;

        public AddStore(String titulo, String descricao, String localizacao, AlertDialog dialog) {
            Titulo = titulo;
            Descricao = descricao;
            Localizacao = localizacao;
            Dialog = dialog;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date hoje = new Date();
            String data = dateFormat.format(hoje);

            Stores store;
            if(imageIsSet){
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                store = new Stores(Titulo, Localizacao, Descricao, data, imageBytes, user, false);
                sampleDatabase.geral().addStore(store);
            }else{
                store = new Stores(Titulo, Localizacao, Descricao, data, null, user, false);
                sampleDatabase.geral().addStore(store);
            }

            return true;
        }

        protected void onPostExecute(Boolean bool){
            Context context = getApplicationContext();
            CharSequence text = "Store Adicionado";
            int duration = Toast.LENGTH_SHORT;
            imageIsSet = false;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Dialog.dismiss();
        }
    }

    /**
     * Pro Fazer
     */
    private void setButtonEditStore() {
        final Button editStore = (Button) findViewById(R.id.btn_edit_storeD);
        editStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(DefenitionActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_add_store, null);

                builder.setView(view);
                final AlertDialog dialog = builder.show();
                final EditText nomeLoja = (EditText) view.findViewById(R.id.nomeLoja);
                nomeLoja.setText(stores.getNome());
                final EditText descricaoLoja = (EditText) view.findViewById(R.id.descricaoLoja);
                descricaoLoja.setText(stores.getDescricao());

                imageView = (ImageView) view.findViewById(R.id.imageView);

                Button button_add_image = (Button) view.findViewById(R.id.button_add_image);
                button_add_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                    }
                });

                Button addBtn = (Button) view.findViewById(R.id.btn_confirm);
                addBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        /**
                         *
                         */
                    }
                });
                Button cancelBtn = (Button) view.findViewById(R.id.button_cancel);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                        imageIsSet = false;
                    }
                });
            }
        });
    }

    private void setButtonAddGames() {
        Button addGames = (Button) findViewById(R.id.btn_add_gamesD);
        addGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(mRefActivity);
                View view = getLayoutInflater().inflate(R.layout.dialog_add_game, null);

                builder.setView(view);
                final AlertDialog dialog = builder.show();

                final EditText nomeGame = (EditText) view.findViewById(R.id.tituloGame);
                final EditText descricaoGame = (EditText) view.findViewById(R.id.descricaoGame);
                final EditText publicherGame = (EditText) view.findViewById(R.id.publicadorGame);
                final Button button_add_image = (Button) view.findViewById(R.id.button_add_image_game);
                imageView = (ImageView) view.findViewById(R.id.imageView);
                button_add_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                    }
                });

                Button addBtn = (Button) view.findViewById(R.id.btn_add_game_Admin);
                addBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        AddGame addgame = new AddGame(nomeGame.getText().toString(), descricaoGame.getText().toString(), publicherGame.getText().toString(),dialog);
                        addgame.execute();
                    }
                });

                Button cancelBtn = (Button) view.findViewById(R.id.button_cancel_game_Admin);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                        imageIsSet = false;
                    }
                });
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
                game = new Games(Titulo, Publicher, Descricao, data, imageBytes, 0, 0, false);
                sampleDatabase.geral().addGame(game);
            }else{
                game = new Games(Titulo, Publicher, Descricao, data, null, 0, 0, false);
                sampleDatabase.geral().addGame(game);
            }

            return true;
        }

        protected void onPostExecute(Boolean bool){
            Context context = getApplicationContext();
            CharSequence text = "Jogo Adicionado";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Dialog.dismiss();
        }
    }

    /**
     * Pro Fazer
     */
    private void setButtonEditGames() {
        Button editGames = (Button) findViewById(R.id.btn_edit_gamesD);
        editGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(DefenitionActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_accept_simpler, null);
                builder.setView(view);
                final AlertDialog dialog = builder.show();

                rvUtilizadores = (RecyclerView) view.findViewById(R.id.rvList);
                DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(DefenitionActivity.this, DividerItemDecoration.VERTICAL);
                rvUtilizadores.addItemDecoration(mDividerItemDecoration);
                TextView textView = (TextView) view.findViewById(R.id.tituloTv);
                textView.setText("Associar Jogo");
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

    public class LoadGames extends AsyncTask<Void, Void, ArrayList<Games>> {

        @Override
        protected ArrayList<Games> doInBackground(Void... voids) {
            ArrayList<Games> list = (ArrayList<Games>) sampleDatabase.geral().loadAllGamesAcepted(true);
            return list;
        }

        protected void onPostExecute(ArrayList<Games> list){
            gAdapterGames = new GamesAcceptAdapter(mRefActivity, list);
            rvUtilizadores.setAdapter(gAdapterGames);
            rvUtilizadores.setLayoutManager(new LinearLayoutManager(mRefActivity));
        }
    }

    private void openGallery() {
        MainActivity.requestStoragePermissions(mRefActivity);

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


    public static void openMap(String m, String n, String d) {

        Intent x = new Intent(mContext, MapsActivity.class);
        x.putExtra("morada", m);
        x.putExtra("nome", n);
        x.putExtra("descricao", d);
        mContext.startActivity(x);
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