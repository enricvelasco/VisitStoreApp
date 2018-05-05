package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.VariablesGlobales;
import com.visitapp.visitstoreapp.login.PantallaLogIn;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.activities.AsociacionConfiguracionLimites;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.fragments.AsociacionFragmentPrincipal;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.fragments.AsociacionFragmentProductos;
import com.visitapp.visitstoreapp.sistema.controllers.asociaciones.AsociacionController;
import com.visitapp.visitstoreapp.sistema.domain.asociaciones.Asociacion;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

public class AsociacionMenuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private ListView listadoTiendas;

    int SOLICITUD_PERMISO_CAMARA = 1;
    int SOLICITUD_PERMISO_READ = 101;
    int SOLICITUD_PERMISO_WRITE= 201;
    int SOLICITUD_PERMISO_POSICIONAMIENTO= 301;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comprobarPermisoCamara();
        comprobarPermisoRead();
        comprobarPermisoWrite();
        comprobarPermisoLocalizacion();

        setContentView(R.layout.activity_asociacion_menu_principal);

        String estadoActual = ((VariablesGlobales) getApplication()).getMenuActual();
        System.out.println("ENTRA EN MENU PRINCIPAL ----------------"+estadoActual);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        switch (estadoActual){
            case "login":
                break;
            case "formularioProductos":
                goToFragmentProductosListado();
                break;
            default:
                goToFragmentTiendasListado();
                break;
        }


        listadoTiendas = findViewById(R.id.idListaTiendas);
        listadoTiendas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                System.out.println("CLICK EN ELEMENTO DEL LISTADO");
            }
        });
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

        final ImageView imagenPerfil = findViewById(R.id.idImagenCartaProducto);



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
                //Asociacion recu = data.getValue(Asociacion.class);
                //nombre.setText(recu.getNombre());
                /*direccion.setText(recu.getDireccion().direccionFormulario());
                telefono.setText("...");*/
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
                //Picasso.with(getApplicationContext()).load(String.valueOf(uri.toString())).resize(200,200).centerCrop().into(imagenPerfil);
                Picasso.with(getApplicationContext()).load(String.valueOf(uri.toString())).transform(new CircleTransform()).into(imagenPerfil);
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
            case R.id.nav_asociacion_tiendas:
                goToFragmentTiendasListado();
                /*System.out.println("CLICK EN MENU DE TINDAS");
                AsociacionFragmentPrincipal fragment = new AsociacionFragmentPrincipal();
                fragmentTransaction.replace(R.id.fragmentAsociacionPrincipal, fragment);
                fragmentTransaction.commit();*/

                break;
            case R.id.nav_asociacion_productos:
                goToFragmentProductosListado();
               /* System.out.println("CLICK EN MENU DE PRODUCTOS");
                AsociacionFragmentProductos fragmentProductos = new AsociacionFragmentProductos();
                fragmentTransaction.replace(R.id.fragmentAsociacionPrincipal, fragmentProductos);
                fragmentTransaction.commit();*/
                break;
            case R.id.nav_configuracion_limites:
                goToConfiguracionLimites();
                break;
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

    private void goToFragmentTiendasListado() {
        System.out.println("CLICK EN MENU DE TINDAS");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AsociacionFragmentPrincipal fragment = new AsociacionFragmentPrincipal();
        fragmentTransaction.replace(R.id.fragmentAsociacionPrincipal, fragment);
        fragmentTransaction.commit();
    }

    private void goToFragmentProductosListado() {
        System.out.println("CLICK EN MENU DE PRODUCTOS");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AsociacionFragmentProductos fragmentProductos = new AsociacionFragmentProductos();
        fragmentTransaction.replace(R.id.fragmentAsociacionPrincipal, fragmentProductos);
        fragmentTransaction.commit();
    }

    private void goToConfiguracionLimites(){
        Intent intent = new Intent(getApplicationContext(), AsociacionConfiguracionLimites.class);
        startActivity(intent);
    }

    private void comprobarPermisoLocalizacion(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("TIENE PERMISO DE POSICION:::::::::::::::::::::::");
        }else{
            System.out.println("NO TIENE PERMISO POSICION!!!!!!!!!!!!!!!!!!!");
            solicitarPermisoCamara(Manifest.permission.ACCESS_FINE_LOCATION, "Sin el permiso"+
                            " no se podrá acceder al posicionamiento",
                    SOLICITUD_PERMISO_POSICIONAMIENTO, this);
        }
    }

    private void comprobarPermisoWrite(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("TIENE PERMISO DE READ:::::::::::::::::::::::");
        }else{
            System.out.println("NO TIENE PERMISO READ!!!!!!!!!!!!!!!!!!!");
            solicitarPermisoCamara(Manifest.permission.WRITE_EXTERNAL_STORAGE, "Sin el permiso"+
                            " no se podrá accedes a la gestión de imagenes.",
                    SOLICITUD_PERMISO_WRITE, this);
        }
    }

    private void comprobarPermisoRead(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("TIENE PERMISO DE READ:::::::::::::::::::::::");
        }else{
            System.out.println("NO TIENE PERMISO READ!!!!!!!!!!!!!!!!!!!");
            solicitarPermisoCamara(Manifest.permission.READ_EXTERNAL_STORAGE, "Sin el permiso"+
                            " no se podrá accedes a la gestión de imagenes.",
                    SOLICITUD_PERMISO_READ, this);
        }
    }

    private void comprobarPermisoCamara() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("TIENE PERMISO DE CAMARA:::::::::::::::::::::::");
        }else{
            System.out.println("NO TIENE PERMISO CAMARA!!!!!!!!!!!!!!!!!!!");
            solicitarPermisoCamara(Manifest.permission.CAMERA, "Sin el permiso"+
                            " no hay acceso a la camara.",
                    SOLICITUD_PERMISO_CAMARA, this);
        }
    }

    private void solicitarPermisoCamara(final String permiso, String justificacion, final int requestCode, final Activity actividad){
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
}

class CircleTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}


