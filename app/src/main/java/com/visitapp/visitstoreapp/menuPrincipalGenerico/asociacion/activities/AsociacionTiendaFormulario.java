package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.activities;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.UsuarioActual;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.AsociacionMenuPrincipal;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.fragments.AsociacionFragmentProductos;
import com.visitapp.visitstoreapp.sistema.controllers.tiendas.TiendaController;
import com.visitapp.visitstoreapp.sistema.domain.genericos.Direccion;
import com.visitapp.visitstoreapp.sistema.domain.tiendas.Tienda;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class AsociacionTiendaFormulario extends AppCompatActivity implements OnMapReadyCallback {

    //popUp Direcciones
    Dialog direccionesPopUp;
    EditText direccionCalle;
    EditText direccionNumero;
    EditText direccionCodigoPostal;
    EditText direccionCiudad;
    EditText direccionPais;
    FloatingActionButton aceptarDireccion;
    FloatingActionButton cancelarDireccion;

    Direccion direccionObj = new Direccion();
    //---

    //AIzaSyC3isf42YFY3WOe6VCZGmgDXiZon5YKn4k
    FloatingActionButton botonEdicion;

    TextView codigo;
    TextView nombreComercial;
    TextView nombreFiscal;
    TextView nif;
    TextView email;
    TextView telefono;
    TextView direccion;

    ImageView logoTienda;

    FloatingActionButton botonCargarImagen;

    FloatingActionButton botonGuardarTienda;

    Tienda tienda;

    String idEdicion;
    String estadoFormulario;

    EditText codigoEd;
    EditText nombreComercialEd;
    EditText nombreFiscalEd;
    EditText nifEd;
    //EditText direccionEd;
    Switch permitePromocionesEd;
    TextView introducirDireccionEd;

    Dialog dialogTiendaEdicion;

    Bitmap bmpLogoTienda = null;
    Uri imagenSeleccionadaUri;

    TiendaController tiendaController = new TiendaController();
    StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asociacion_tienda_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        //--------
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/

        arrancarMapFragment();

        Intent myIntent = getIntent();
        idEdicion = myIntent.getStringExtra("tienda_id");
        declararPopUp();
        declararPopUpDirecciones();
        if(idEdicion == null){
            System.out.println("ESTADO NUEVO");
            estadoFormulario = "nuevo";
            tienda = new Tienda();
            popUpFormularioTienda();
        }else{
            estadoFormulario = "edicion";
            tienda = new Tienda();
            tienda.set_id(idEdicion);
            tienda.setAsociacion_id(USUARIO_ACTUAL.getParametrosUsuarioActual().getAcceso_asociacion_id());
            tiendaController.read(idEdicion, new OnGetDataListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(DataSnapshot data) {
                    tienda = data.getValue(Tienda.class);
                    codigo.setText(tienda.getCodigo());
                    nombreComercial.setText(tienda.getNombrePublico());
                    nombreFiscal.setText(tienda.getNombreFiscal());
                    nif.setText(tienda.getNif());
                    direccion.setText(tienda.getDireccion().direccionFormulario());

                    //posicionar la direccion en el mapa
                    direccionObj = tienda.getDireccion();
                    arrancarMapFragment();

                    Picasso.with(getApplicationContext()).load(tienda.getLogo()).resize(768,768).centerCrop().into(logoTienda);
                    logoTienda.buildDrawingCache();
                    bmpLogoTienda = logoTienda.getDrawingCache();

                    direccionCalle.setText(tienda.getDireccion().getCalle());
                    direccionNumero.setText(tienda.getDireccion().getNumCalle());
                    direccionCodigoPostal.setText(tienda.getDireccion().getPostalCode());
                    direccionCiudad.setText(tienda.getDireccion().getCiudad());
                    direccionPais.setText(tienda.getDireccion().getPais());
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }

        inicializarCampos();

        botonEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigoEd.setText(codigo.getText());
                nombreComercialEd.setText(nombreComercial.getText());
                nombreFiscalEd.setText(nombreFiscal.getText());
                nifEd.setText(nif.getText());

                //direccionEd.setText(direccion.getText());
                popUpFormularioTienda();
            }
        });

        botonCargarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
            }
        });

        botonGuardarTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo.equals("") || nombreFiscal.equals("") || bmpLogoTienda == null){
                    Toast.makeText(getApplicationContext(), "Faltan campos obligatorios", Toast.LENGTH_SHORT).show();
                }else{
                    guardadoTienda();
                }
            }
        });

        logoTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("GIRAR IMAGEN 90'");
                girarImagen90();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void girarImagen90() {
        try {
            System.out.println("VALOR BITMAP "+bmpLogoTienda);
            imagenSeleccionadaUri = getImageUri(AsociacionTiendaFormulario.this, bmpLogoTienda);
            bmpLogoTienda = MediaStore.Images.Media.getBitmap(
                    getContentResolver(), imagenSeleccionadaUri);
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bmpLogoTienda = Bitmap.createBitmap(bmpLogoTienda, 0, 0, bmpLogoTienda.getWidth(), bmpLogoTienda.getHeight(), matrix, true);
            logoTienda.buildDrawingCache();
            Picasso.with(getApplicationContext()).load(imagenSeleccionadaUri).rotate(90f).resize(768,768).centerCrop().into(logoTienda);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error al girar", Toast.LENGTH_SHORT).show();
        }

    }


    private void inicializarCampos() {
        logoTienda = findViewById(R.id.idLogoTiendaFormulario);
        botonCargarImagen = findViewById(R.id.idButtonCargarImagenLogoTiendaFormulario);
        botonGuardarTienda = findViewById(R.id.idButtonGruardarTiendaFormulario);

        codigo = findViewById(R.id.idCodigoTiendaFormulario);
        nombreComercial = findViewById(R.id.idNombrePublicoTiendaFormulario);
        nombreFiscal = findViewById(R.id.idNombreFiscalTiendaFormulario);
        nif = findViewById(R.id.idNifTiendaFormulario);
        email = findViewById(R.id.idEmailTiendaFormulario);
        telefono = findViewById(R.id.idTelefonoTiendaFormulario);
        direccion = findViewById(R.id.idDireccionTiendaFormulario);

        botonEdicion = findViewById(R.id.idButtonEdicionTiendaFormulario);
    }

    private void declararPopUpDirecciones() {
        direccionesPopUp = new Dialog(AsociacionTiendaFormulario.this);
        direccionesPopUp.setContentView(R.layout.pop_up_introducir_direccion_tienda);
        direccionCalle = direccionesPopUp.findViewById(R.id.idCalleTiendaPopUp);
        direccionNumero = direccionesPopUp.findViewById(R.id.idNumeroTiendaPopup);
        direccionCodigoPostal = direccionesPopUp.findViewById(R.id.idCodigoPostalTiendaPopUp);
        direccionCiudad = direccionesPopUp.findViewById(R.id.idCiudadTiendaPopUp);
        direccionPais = direccionesPopUp.findViewById(R.id.idPaisTiendaPopUp);

        aceptarDireccion = direccionesPopUp.findViewById(R.id.idButtonAplicarCambiosDireccionTienda);
        cancelarDireccion = direccionesPopUp.findViewById(R.id.idButtonCancelarCambiosDireccionTienda);
    }


    private void declararPopUp(){
        dialogTiendaEdicion = new Dialog(AsociacionTiendaFormulario.this);
        dialogTiendaEdicion.setContentView(R.layout.pop_up_asociacion_edicion_tienda);
        codigoEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaCodigo);
        nombreComercialEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNombreComercial);
        nombreFiscalEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNombreFiscal);
        nifEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNif);
        EditText emailEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNombreFiscal);
        EditText telefonoEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaTelefono);
        //direccionEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaDireccion);
        permitePromocionesEd = dialogTiendaEdicion.findViewById(R.id.switchFormularioTiendaPermitePromociones);

        introducirDireccionEd = dialogTiendaEdicion.findViewById(R.id.idMontarDireccionTiendaPopUp);
    }

    private void popUpFormularioTienda(){
        FloatingActionButton botonGuardarPopUp = dialogTiendaEdicion.findViewById(R.id.idButtonGuardarCambiosTienda);
        FloatingActionButton botonCancelarPopUo = dialogTiendaEdicion.findViewById(R.id.idCanelarCambiosTienda);

        dialogTiendaEdicion.show();

        introducirDireccionEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direccionesPopUp.show();

            }
        });

        aceptarDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //direccionObj = new Direccion();
                direccionObj.setCalle(direccionCalle.getText().toString());
                direccionObj.setNumCalle(direccionNumero.getText().toString());
                direccionObj.setPostalCode(direccionCodigoPostal.getText().toString());
                direccionObj.setCiudad(direccionCiudad.getText().toString());
                direccionObj.setPais(direccionPais.getText().toString());

                introducirDireccionEd.setText(direccionObj.toString());

                Barcode.GeoPoint direcCoor = getLocationFromAddress(direccionObj.toString());
                if(direcCoor != null){
                    direccionObj.setLatitud(direcCoor.lat);
                    direccionObj.setLongtud(direcCoor.lng);
                }

                direccionesPopUp.dismiss();
            }
        });

        cancelarDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direccionesPopUp.dismiss();
            }
        });

        botonGuardarPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tienda.setAsociacion_id(USUARIO_ACTUAL.getParametrosUsuarioActual().getAcceso_asociacion_id());
                tienda.setCodigo(codigoEd.getText().toString());
                tienda.setNombrePublico(nombreComercialEd.getText().toString());
                tienda.setNombreFiscal(nombreFiscalEd.getText().toString());
                tienda.setNif(nifEd.getText().toString());
                //tienda. //introducir EMAIL
                //tienda. //introducir TELEFONO
                //tienda.setDireccion(direccionEd.getText().toString());
                tienda.setDireccion(direccionObj);
                tienda.setPermitePromociones(permitePromocionesEd.isChecked());

                codigo.setText(tienda.getCodigo());
                nombreComercial.setText(tienda.getNombrePublico());
                nombreFiscal.setText(tienda.getNombreFiscal());
                nif.setText(tienda.getNif());
                direccion.setText(tienda.getDireccion().direccionFormulario());

                //indicar en el mapa la direccion
                LatLng posicion = new LatLng(direccionObj.getLatitud(), direccionObj.getLongtud());
                //marcarEnElMapa(posicion);
                arrancarMapFragment();
                dialogTiendaEdicion.dismiss();
            }
        });

        botonCancelarPopUo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTiendaEdicion.dismiss();
            }
        });
    }

    private void guardadoTienda() {
        final StorageReference filepath = mStorageRef.child("tiendas").child(tienda.get_id()).child(tienda.get_id());
        Uri uriImagenGuardar = getImageUri(getApplicationContext(), bmpLogoTienda);

        filepath.putFile(uriImagenGuardar).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        tienda.setLogo(uri.toString());
                        if(estadoFormulario.equals("nuevo")){
                            tiendaController.save(tienda);
                        }else if(estadoFormulario.equals("edicion")){
                            tienda.set_id(idEdicion);
                            tiendaController.update(tienda);
                        }
                        Toast.makeText(getApplicationContext(), "Guardado Finalizado", Toast.LENGTH_SHORT).show();
                        //Cambiar a MENU PRINCIPAL
                        Intent i = new Intent(getApplicationContext(), AsociacionMenuPrincipal.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Toast.makeText(getApplicationContext(), "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("ERROR SUBIR IMAGEN");
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("ACTIVITY RESULT"+resultCode);
        switch(requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    System.out.println("ASIGNA LA IMAGEN");
                    imagenSeleccionadaUri = data.getData();
                    logoTienda.setImageURI(imagenSeleccionadaUri);

                    ///
                    try {
                        bmpLogoTienda = MediaStore.Images.Media.getBitmap(
                                getContentResolver(), imagenSeleccionadaUri);
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        bmpLogoTienda = Bitmap.createBitmap(bmpLogoTienda, 0, 0, bmpLogoTienda.getWidth(), bmpLogoTienda.getHeight(), matrix, true);
                        Picasso.with(getApplicationContext()).load(imagenSeleccionadaUri).rotate(90f).resize(768,768).centerCrop().into(logoTienda);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                    }
                    //Object imageurl = getRealPathFromURI(selectedImage);

                    /*Matrix matrix = new Matrix();
                    matrix.postRotate(90);*/
                    //thumbnail = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight(), matrix, true);

                    //imagenProducto.setImageBitmap(thumbnail);

                }

                break;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("ON MAP READY************************************************");

        //LatLng sydney = new LatLng(41.372918, 2.157812);
        LatLng posicion = new LatLng(direccionObj.getLatitud(), direccionObj.getLongtud());
        googleMap.addMarker(new MarkerOptions().position(posicion)
                .title(direccionObj.tituloMap()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 17));
        setLocation(posicion);

        //Barcode.GeoPoint direccion =  getLocationFromAddress("Carrer de la Mare de Déu del Remei, 23, 08004 Barcelona, España");
        //Barcode.GeoPoint direccion =  getLocationFromAddress("08004 Barcelona");

        /*System.out.println("LATITUD STRING::"+direccion.lat);
        System.out.println("LONGITUD STRING::"+direccion.lng);*/
    }

    public void setLocation(LatLng loc) {
        System.out.println("ENTRA A OBTENER DIRECCION "+loc.longitude + " - " +loc.latitude);
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.latitude != 0.0 && loc.longitude != 0.0) {
            System.out.println("ENTRA::::");
            try {
                System.out.println("TRY::::");
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.latitude, loc.longitude, 1);
                System.out.println("LISTADO::::"+ list.size());
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    /*mensaje2.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));*/
                    System.out.println("LA DIRECCION DE LA CALLE ES: "+DirCalle.getAddressLine(0));
                    //Carrer de la Mare de Déu del Remei, 23, 08004 Barcelona, España
                }

            } catch (IOException e) {
                System.out.println("ERROR::::");
                e.printStackTrace();
            }
        }
    }

    private void addressFromString(){

    }

    public Barcode.GeoPoint getLocationFromAddress(String strAddress){
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        Barcode.GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new Barcode.GeoPoint((double) (location.getLatitude()),
                    (double) (location.getLongitude()));

            return p1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p1;
    }

    private void arrancarMapFragment(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /*private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }*/
}
