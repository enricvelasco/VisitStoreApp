package com.visitapp.visitstoreapp.menuPrincipalGenerico.visitaRegistrado;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.login.PantallaLogIn;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.visitaRegistrado.fragments.VisitaRegistradoFragmentDescubre;

public class VisitaRegistradoMenuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {
    private int SOLICITUD_PERMISO_POSICIONAMIENTO = 100;
    private FirebaseAuth mAuth;

    double longitudeGPS, latitudeGPS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita_registrado_menu_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        System.out.println("ARRANCA VISTA REGISTRADO");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //solicitar permiso de localizacion
        comprobarPermisoLocalizacion();

        //arrancar fragment principal
        goToFragmentDescubre();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.visita_registrado_menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //int id = item.getItemId();
        switch (id){
            /*case R.id.action_configParametros:
                System.out.println("CLICK EN CONFIGURAR PARAMETROS");
                break;*/
            case R.id.action_logOut:
                System.out.println("CLICK EN LOG OUT");
                mAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), PantallaLogIn.class);
                startActivity(i);
                break;
        }

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_logOut) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void comprobarPermisoLocalizacion(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        }else{
            solicitarPermiso(Manifest.permission.ACCESS_FINE_LOCATION, "Sin el permiso"+
                            " no se podrá acceder al posicionamiento",
                    SOLICITUD_PERMISO_POSICIONAMIENTO, this);
        }
    }

    private void solicitarPermiso(final String permiso, String justificacion, final int requestCode, final Activity actividad){
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad,
                permiso)){
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad,
                                    new String[]{permiso}, requestCode);
                        }})
                    .show();
        } else {
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);
        }
    }

    private void goToFragmentDescubre() {
        System.out.println("CLICK EN MENU DE TINDAS");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        VisitaRegistradoFragmentDescubre fragment = new VisitaRegistradoFragmentDescubre();
        fragmentTransaction.replace(R.id.fragmentVisitaRegistradoPrincipal, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("LOCATION CHANGE");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        System.out.println("STATUS CHANGED");
    }

    @Override
    public void onProviderEnabled(String provider) {
        System.out.println("GPS ACTIVADO");
    }

    @Override
    public void onProviderDisabled(String provider) {
        System.out.println("GPS DESACTIVADO");
    }

    /*private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeGPS = location.getLongitude();
            latitudeGPS = location.getLatitude();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(VisitaRegistradoMenuPrincipal.this, "GPS Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }
        @Override
        public void onProviderDisabled(String s) {
        }
    };*/

}
