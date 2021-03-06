package com.example.tronc.gamesdaily.Activity;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tronc.gamesdaily.AsyncTasks.LoadingAsyncTask;
import com.example.tronc.gamesdaily.Data.MyDB;
import com.example.tronc.gamesdaily.Models.User;
import com.example.tronc.gamesdaily.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Atividade que permite o login do user a aplicação
 */
public class LoginActivity extends AppCompatActivity {

    //Base de dados completa
    private MyDB sampleDatabase;

    private EditText mUserView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sampleDatabase = Room.databaseBuilder(getApplicationContext(), MyDB.class, this.getString(R.string.database_value)).build();
        // Set up the login form.
        mUserView = (EditText) findViewById(R.id.user);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

        Button Button = (Button) findViewById(R.id.email_sign_in_button);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConfirmUser confirm = new ConfirmUser(mUserView.getText().toString(), mPasswordView.getText().toString());
                confirm.execute();

            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public class ConfirmUser extends AsyncTask<Void, Void, ArrayList<User>> {
        String name;
        String pass;

        public ConfirmUser(String name, String pass) {
            this.name = name;
            this.pass = pass;
        }

        @Override
        protected ArrayList<User> doInBackground(Void... voids) {
            ArrayList<User> listUsers = (ArrayList<User>) sampleDatabase.geral().loadAllUsers();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date hoje = new Date();
            String data = dateFormat.format(hoje);

            if(listUsers.size() == 0){
                User user = new User("admin","admin","admin",data,"admin@gmail.com", 2);
                sampleDatabase.geral().addUser(user);
            }

            ArrayList<User> list = (ArrayList<User>) sampleDatabase.geral().getUserByName(name);
            return list;
        }

        protected void onPostExecute(ArrayList<User> user) {

            LoadingAsyncTask lat = new LoadingAsyncTask(LoginActivity.this, getResources().getString(R.string.msg_async_task_loading_login));
            lat.execute();


            if (user.size() != 0 && user.get(0).getPassword().equals(pass)) {

                Context context = getApplicationContext();
                CharSequence text = "Bem Vindo " + user.get(0).getUsername();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent i = new Intent(LoginActivity.this, NewsActivity.class);
                i.putExtra("KEY",user.get(0).getUsername());
                i.putExtra("Type",user.get(0).getTipo_utilizador_id());
                startActivity(i);
                lat.cancel(false);
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Username e/ou palavra passe incorretas!";
                int duration = Toast.LENGTH_SHORT;
                lat.cancel(false);
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
}
