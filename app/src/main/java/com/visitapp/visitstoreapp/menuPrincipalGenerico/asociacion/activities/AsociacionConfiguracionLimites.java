package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.visitapp.visitstoreapp.R;

public class AsociacionConfiguracionLimites extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asociacion_configuracion_limites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        arrancarMapFragment();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void arrancarMapFragment(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapLimites);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onPolygonClick(Polygon polygon) {
        System.out.println("ON POLYGON CLICK");
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        System.out.println("ON POLYLINE CLICK");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-35.016, 143.321),
                        new LatLng(-34.747, 145.592),
                        new LatLng(-34.364, 147.891),
                        new LatLng(-33.501, 150.217),
                        new LatLng(-32.306, 149.248),
                        new LatLng(-32.491, 147.309),
                        new LatLng(-35.016, 143.321)));*/

        Polygon polygon1 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(-27.457, 153.040),
                        new LatLng(-33.852, 151.211),
                        new LatLng(-37.813, 144.962),
                        new LatLng(-34.928, 138.599)));
// Store a data object with the polygon, used here to indicate an arbitrary type.
        //polygon1.setTag("alpha");
        polygon1.setFillColor(0x3F00FF00);
        polygon1.setStrokeColor(0xffF57F17);

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.852, 151.211), 10));

        // Set listeners for click events.
        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);
    }
}
