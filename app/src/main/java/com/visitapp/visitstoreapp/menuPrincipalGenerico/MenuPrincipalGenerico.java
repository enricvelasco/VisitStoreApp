package com.visitapp.visitstoreapp.menuPrincipalGenerico;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.domain.asociaciones.Asociacion;
import com.visitapp.visitstoreapp.domain.productos.Producto;
import com.visitapp.visitstoreapp.domain.tiendas.Tienda;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuPrincipalGenerico extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //private FirebaseAuth mAuth;
    Button generador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_generico);

        //inicializa Firebase
        //mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        generador = findViewById(R.id.buttonGenerarDatos);
        generador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarDatos();
            }
        });
        //BUTTONS
        //findViewById(R.id.buttonGenerarDatos).setOnClickListener((View.OnClickListener) this);

        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("resp");

        myRef.setValue("Hello, World2!"+new Date());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("DATOS RECIBIDOS"+dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    private void generarDatos() {
        List<Producto> productosTienda1 = new ArrayList<>();
        List<Producto> productosTienda2 = new ArrayList<>();
        List<Producto> productosTienda3 = new ArrayList<>();

        for(int i=0; i<60; i++){
            Producto p = new Producto();
            p.setDescripcion("Producto_"+i);
            p.setImagen("imagen_"+i);
            if(i<41){
                productosTienda1.add(p);
            }else if(i>41 && i<51){
                productosTienda2.add(p);
            }else if(i>54){
                productosTienda3.add(p);
            }
        }

        List<Tienda> listaTiendas1 = new ArrayList<>();
        //List<Tienda> listaTiendas2 = new ArrayList<>();

        for(int i=0; i<3; i++){
            Tienda t = new Tienda();
            t.setDireccion("Direccion_"+i);
            t.setNombre("NombreTienda_"+i);
            t.setObservaciones("ObservacionesTienda_"+i);
            t.setUbicacion("UbicacionTienda:"+i);
            if(i==0){
                t.setProductos(productosTienda1);
                listaTiendas1.add(t);
            }else if(i==1){
                t.setProductos(productosTienda2);
                listaTiendas1.add(t);
            }else if(i==2){
                t.setProductos(productosTienda3);
                listaTiendas1.add(t);
            }
        }

        Asociacion a = new Asociacion();
        a.setNombre("Asociacion_1");
        a.setObservaciones("observaciones asociacion 1");
        a.setTiendas(listaTiendas1);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("asociaciones2");
        myRef.setValue(a);
        //myRef.push().setValue(a);
    }

    /*@Override
    public void onStart() {
        super.onStart();
        System.out.println("ENTRA A ON START: comprueba si el usuario esta conectado o no");
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }*/


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
        getMenuInflater().inflate(R.menu.menu_principal_generico, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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

    /*@Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.buttonGenerarDatos){
        }
    }*/
}
