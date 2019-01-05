package com.example.tronc.gamesdaily.Activity;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
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
 * Atividade que regista o utliazador e o admin caso este ainda não exista
 */
public class RegisterActivity extends Activity {
    //Base de dados completa
    private MyDB sampleDatabase;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordConfirm;
    private View mProgressView;
    private View mLoginFormView;
    private EditText mUserName;
    private EditText mUserView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sampleDatabase = Room.databaseBuilder(getApplicationContext(), MyDB.class, this.getString(R.string.database_value)).build();
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mUserName = (EditText) findViewById(R.id.Name);
        mUserView = (EditText) findViewById(R.id.user);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordConfirm = (EditText) findViewById(R.id.passwordRepeat);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

        final Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mPasswordView.getText().toString().equals(mPasswordConfirm.getText().toString()) || mUserView.getText().toString().equals("") ||
                        mUserName.getText().toString().equals("") || mPasswordView.getText().toString().equals("") ||
                        mEmailView.getText().toString().equals("")){
                    Context context = getApplicationContext();
                    CharSequence text = "Valores introduzidos não aceites";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else{
                    RegistUser regist = new RegistUser(mUserView.getText().toString(), mUserName.getText().toString(),mPasswordView.getText().toString(), mEmailView.getText().toString());
                    regist.execute();
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public class RegistUser extends AsyncTask<Void, Void, ArrayList<User>> {

        private String name;
        private String pass;
        private String email;
        private String username;
        private boolean existed;

        public RegistUser(String name, String username,String pass, String email) {
            this.name = name;
            this.pass = pass;
            this.email = email;
            this.username = username;
            this.existed = true;
        }

        @Override
        protected ArrayList<User> doInBackground(Void... voids) {
            ArrayList<User> listUsers = (ArrayList<User>) sampleDatabase.geral().loadAllUsers();
            ArrayList<User> list = (ArrayList<User>) sampleDatabase.geral().getUserByName(name);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date hoje = new Date();
            String data = dateFormat.format(hoje);

            if(listUsers.size() == 0){
                sampleDatabase.geral().addUser(new User("admin","admin","admin",data,"admin@gmail.com"));
            }

            if(list.size() ==0){
                User user = new User(name, pass, username, data, email);
                sampleDatabase.geral().addUser(user);
                list = (ArrayList<User>) sampleDatabase.geral().getUserByName(name);
                this.existed = false;}
            return list;
        }

        protected void onPostExecute(ArrayList<User> list){
            LoadingAsyncTask lat = new LoadingAsyncTask(RegisterActivity.this, getResources().getString(R.string.msg_async_task_loading_login));
            lat.execute();
            if(this.existed){
                Context context = getApplicationContext();
                CharSequence text = "Username já existe!";
                int duration = Toast.LENGTH_SHORT;
                lat.cancel(false);
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }else{
                Intent i = new Intent(RegisterActivity.this, NewsActivity.class);
                i.putExtra("KEY",String.valueOf(name));
                startActivity(i);
                lat.cancel(false);
            }
        }
    }
}
