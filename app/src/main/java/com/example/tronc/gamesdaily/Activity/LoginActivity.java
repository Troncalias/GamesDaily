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

import com.example.tronc.gamesdaily.Data.MyDB;
import com.example.tronc.gamesdaily.Data.ValuesBD;
import com.example.tronc.gamesdaily.Models.User;
import com.example.tronc.gamesdaily.R;

import java.util.ArrayList;

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
        sampleDatabase = Room.databaseBuilder(getApplicationContext(), MyDB.class, new ValuesBD().getNamedabe()).build();
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
            ArrayList<User> list = (ArrayList<User>) sampleDatabase.geral().getUserByName(name);
            return list;
        }

        protected void onPostExecute(ArrayList<User> user){
            if(user.size() != 0 && user.get(0).getPassword().equals(pass)){
                Context context = getApplicationContext();
                CharSequence text = "Size" + user.size();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent i = new Intent(LoginActivity.this, NewsActivity.class);
                i.putExtra("KEY",String.valueOf(user.get(0).getUsername()));
                startActivity(i);
            }else{
                Context context = getApplicationContext();
                CharSequence text = "Username e/ou palavra passe incorretas!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
}
