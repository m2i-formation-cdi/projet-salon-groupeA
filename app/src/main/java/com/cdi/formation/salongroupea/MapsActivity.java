package com.cdi.formation.salongroupea;

import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Double latitude;
    private Double longitude;

    private String title;
    private String theme;
    private String day;
    private String startHour;

    private TextView TextTitle;
    private TextView TextTheme;
    private TextView TextDay;
    private TextView TextStartHour;

    private Button btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        this.setTitle("Carte");

        TextTitle = findViewById(R.id.title);
        TextTheme = findViewById(R.id.theme);
        TextDay = findViewById(R.id.day);
        TextStartHour = findViewById(R.id.startHour);
        btnRetour = findViewById(R.id.btnReturn);

        Bundle params = getIntent().getExtras();
        latitude = params.getDouble("latitude");
        longitude = params.getDouble("longitude");
        title = params.getString("title");
        theme = params.getString("theme");
        day = params.getString("day");
        startHour = params.getString("startHour");

        TextTitle.setText(title);
        TextTheme.setText(theme);
        TextDay.setText(day);
        TextStartHour.setText(startHour);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng location = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(location).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

        mMap.setMinZoomPreference(14.0f);
    }
    private void navigateToFragment(Fragment targetFragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, targetFragment)
                .commit();
    }

}
