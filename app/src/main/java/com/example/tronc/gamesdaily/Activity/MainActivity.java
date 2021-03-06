package com.example.tronc.gamesdaily.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tronc.gamesdaily.R;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_STORAGE_PERMISSION = 23;

    /**
     * Funçao que inicia o funcionamento da actividade
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isOnline() != true){
            setNoInternetDialog(this);
        }


        Button bLogin = findViewById(R.id.buttonLogin);
        Button bRegister = findViewById(R.id.buttonRegister);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }

    /**
     * Pede ao user por permições
     * @param activity
     */
    public static void requestStoragePermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        }
    }

   /* public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }*/

    /**
     * Verefica se existe conequeção a internete
     * @return
     */
    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Define e apresenta o dialog a informar que a internet não está disponível
     *
     * @param activity
     */
    public void setNoInternetDialog(final Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.confirmationDialog);
        builder.setMessage(activity.getString(R.string.msg_no_internet));
        builder.setTitle(activity.getResources().getString(R.string.title_no_internet));

        builder.setPositiveButton(R.string.button_retry, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (isOnline() != true){
                    setNoInternetDialog(activity);
                }
            }
        });

        final AlertDialog ad = builder.create();
        ad.setCancelable(false);

        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                ad.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(activity.getResources().getColor(R.color.whiteLetter));
            }
        });
        ad.show();

    }
}
