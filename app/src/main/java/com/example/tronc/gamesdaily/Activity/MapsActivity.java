package com.example.tronc.gamesdaily.Activity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.tronc.gamesdaily.Notifications.MyNotification;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.List;

import com.example.tronc.gamesdaily.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {


    private String morada;
    private String nome;
    private String descricao;
    private SupportMapFragment mMapFragment;
    private GoogleMap mGoogleMaps;
    private Toolbar mToolbar;
    private static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mContext = this;
        setToolbar();

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        String m = b.getString("morada");
        morada = m;
        String n = b.getString("nome");
        nome = n;
        String d = b.getString("descricao");
        descricao = d;

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("MAPA");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMaps = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(true);

        try {
            addMarker(mGoogleMaps);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MarkerClick(googleMap);
    }


    public void addMarker(GoogleMap mGoogleMaps) throws IOException {
        Geocoder geoAddress = new Geocoder(this);
        List<Address> addressList = geoAddress.getFromLocationName(morada, 1);
        if (!addressList.isEmpty()) {
            LatLng latlng = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
            MarkerOptions marker = new MarkerOptions()
                    .position(latlng)
                    .title(nome)
                    .snippet(descricao);

            mGoogleMaps.addMarker(marker);
            mGoogleMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18));
        }else{
            Toast toast = Toast.makeText(mContext, "Os valores que introduziu para o mapa foram rejeitados\nPorfavor edite estes", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    public void MarkerClick(GoogleMap mGoogleMaps) {
        mGoogleMaps.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.equals(marker)) {
                    Log.w("Click", "test");
                    marker.showInfoWindow();
                    return true;
                }
                return false;
            }
        });
    }
}
