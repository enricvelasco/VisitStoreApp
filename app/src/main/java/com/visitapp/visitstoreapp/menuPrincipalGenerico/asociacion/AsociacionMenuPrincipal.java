package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.VariablesGlobales;
import com.visitapp.visitstoreapp.login.PantallaLogIn;
import com.visitapp.visitstoreapp.sistema.controllers.asociaciones.AsociacionController;
import com.visitapp.visitstoreapp.sistema.domain.asociaciones.Asociacion;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

public class AsociacionMenuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asociacion_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.asociacion_menu_principal, menu);

        final TextView nombre = findViewById(R.id.idNombreAsociacion);
        final TextView direccion = findViewById(R.id.idDireccionAsociacion);
        final TextView telefono = findViewById(R.id.idTelefonoAsociacion);

        final ImageView imagenPerfil = findViewById(R.id.idImagenPerfil);



        UsuarioParametros usuarioParametros = ((VariablesGlobales) this.getApplication()).getUsuarioParametros();
        nombre.setText(usuarioParametros.getNombre());
        direccion.setText("...");
        telefono.setText("...");
        Asociacion asociacion = new Asociacion();
        asociacion.set_id(usuarioParametros.getAcceso_asociacion_id());
        AsociacionController asociacionController = new AsociacionController();
        asociacionController.read(asociacion, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                Asociacion recu = data.getValue(Asociacion.class);
                //nombre.setText(recu.getNombre());
                direccion.setText(recu.getNombre());
                telefono.setText("...");
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });


        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference profilePicture = mStorageRef.child("asociacion").child(usuarioParametros.getAcceso_asociacion_id()).child("logo.jpg");
        //riversRef.getFile()*/
        //System.out.println("LA URL DE LA FOTO "+profilePicture);

        profilePicture.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //System.out.println("LA URL DE DESCARGA ES "+uri.toString());
                Picasso.with(getApplicationContext()).load(String.valueOf(uri.toString())).resize(50,50).centerCrop().into(imagenPerfil);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/visitstoreapp.appspot.com/o/default%2Flogo_profile%2Flogo_default.jpg?alt=media&token=272762af-838f-42d8-aa45-a7919e4d1e59").resize(50,50).centerCrop().into(imagenPerfil);
            }
        });


        //String imagen = "https://firebasestorage.googleapis.com/v0/b/visitstoreapp.appspot.com/o/asociacion%2F5f6b1e08-3156-4a4b-88a1-23e9bc610549%2Flogo.jpg?alt=media&token=8e3eca5e-f53a-4df7-a972-c2ab6667b953";

        //Picasso.with(this).load(String.valueOf(profilePicture)).resize(50,50).centerCrop().into(imagenPerfil);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_configParametros:
                System.out.println("CLICK EN CONFIGURAR PARAMETROS");
                break;
            case R.id.action_logOut:
                System.out.println("CLICK EN LOG OUT");
                mAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), PantallaLogIn.class);
                startActivity(i);
                break;
        }

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){

        }

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
