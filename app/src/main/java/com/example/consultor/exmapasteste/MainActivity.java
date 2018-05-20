package com.example.consultor.exmapasteste;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity  implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback {
    public GoogleMap mapa;
    public SupportMapFragment mapFragment;
    public GoogleApiClient mGoogleApiClient;


    public Button btgps;
    public LatLng localizacao = new LatLng(-23.951137, -46.339025);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.nossoMapa);


        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        botoes();

    }

    private void botoes() {
        btgps = (Button) findViewById(R.id.btnPosicao);
        btgps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapa.setMyLocationEnabled(true);
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED)
                {

                    Location getL = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    LatLng minhaPosicao = new LatLng(getL.getLatitude(), getL.getLongitude());
                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(minhaPosicao, 16);
                    mapa.animateCamera(update);
                    Log.d("Bt","foi");

                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    protected void onStart()
    {
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(localizacao, 18);
        mapa.animateCamera(update);
        Log.d("Mapa","foi");


        mapa.addMarker(new MarkerOptions().position(localizacao).title("SFC"));

    }
}
