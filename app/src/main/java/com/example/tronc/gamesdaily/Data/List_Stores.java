package com.example.tronc.gamesdaily.Data;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.example.tronc.gamesdaily.Models.Stores;
import com.example.tronc.gamesdaily.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class List_Stores extends ListActivity {
    private List<Stores> lista_stores = new ArrayList<Stores>();

    public List_Stores() {

        Stores stores = new Stores(1, "Loja 1", "Rua Avenida 1", "Loja de Jogos Steam", "20/10/2019 10:00", null, "user 1");
        this.lista_stores.add(stores);
        stores = new Stores(2, "Loja 2", "Rua Avenida 2", "Loja de Jogos Local", "30/1/2018 10:00", null, "user 4");
        this.lista_stores.add(stores);
    }

    public List<Stores> getLista_stores() {
        return lista_stores;
    }

    public void setLista_stores(ArrayList<Stores> lista_stores) {
        this.lista_stores = lista_stores;
    }

    public Stores search(int id) {
        Stores trues = null;
        for (int i = 0; i < this.lista_stores.size(); i++) {
            if (this.lista_stores.get(i).getId() == id) {
                trues = this.lista_stores.get(i);
            }
        }
        return trues;
    }

    public byte[] image() {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        return imageInByte;
    }
}
