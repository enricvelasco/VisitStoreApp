package com.visitapp.visitstoreapp.menuPrincipalGenerico;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.VariablesGlobales;
import com.visitapp.visitstoreapp.login.LoginMailPassword;
import com.visitapp.visitstoreapp.login.PantallaLogIn;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.fragments.FragmentCapturaCamaraMenu;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.fragments.FragmentPrincipal;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.fragments.FragmentProductosDemo;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;


public class MenuPrincipalGenerico extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;

    //datos perfil
    TextView nombreUsuario;
    TextView direccionUsuario;
    TextView telefonoUsuario;
    //--------
    //UsuarioParametros usuarioParametros = ((VariablesGlobales) this.getApplication()).getUsuarioParametros();

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
        getMenuInflater().inflate(R.menu.menu_principal_generico, menu);

        UsuarioParametros usuarioParametros = ((VariablesGlobales) this.getApplication()).getUsuarioParametros();
        nombreUsuario = findViewById(R.id.idNombreMenuInfo);
        direccionUsuario = findViewById(R.id.idDireccionMenuInfo);
        telefonoUsuario = findViewById(R.id.idTelefonoMenuInfo);

        nombreUsuario.setText(usuarioParametros.getNombre());
        direccionUsuario.setText("---");
        telefonoUsuario.setText("****");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                System.out.println("CLICK EN SETTINGS");
                break;
            case R.id.action_logOut:
                System.out.println("CLICK EN LOG OUT");
                mAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), PantallaLogIn.class);
                startActivity(i);
                break;
        }
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentPrincipal fragment = new FragmentPrincipal();
            fragmentTransaction.replace(R.id.fragmentPrincipal, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_gallery) {
            ///FragmentPrincipal f1 = findViewById(R.id.fragmentPrincipal);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentProductosDemo fragment = new FragmentProductosDemo();
            fragmentTransaction.replace(R.id.fragmentPrincipal, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_slideshow) {
            UsuarioParametros usuarioParametros = ((VariablesGlobales) this.getApplication()).getUsuarioParametros();
            System.out.println("MOSTRAR VARIABLE GLOBAL:");
            System.out.println("_ID: "+usuarioParametros.get_id());
            System.out.println("NOMBRE: "+usuarioParametros.getNombre());
            System.out.println("EMAIL: "+usuarioParametros.getEmail());
            //System.out.println("NIVEL: "+usuarioParametros.getNivel_id());

        } else if (id == R.id.nav_manage) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentCapturaCamaraMenu fragment = new FragmentCapturaCamaraMenu();
            fragmentTransaction.replace(R.id.fragmentPrincipal, fragment);
            fragmentTransaction.commit();

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
