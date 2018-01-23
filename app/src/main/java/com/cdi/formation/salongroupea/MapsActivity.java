package com.cdi.formation.salongroupea;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        TextTitle = findViewById(R.id.title);
        TextTheme = findViewById(R.id.theme);
        TextDay = findViewById(R.id.day);
        TextStartHour = findViewById(R.id.startHour);

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


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng location = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(location).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

        mMap.setMinZoomPreference(14.0f);
    }
}
