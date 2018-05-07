package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.adapter.ItemDireccionesListado;
import com.visitapp.visitstoreapp.sistema.controllers.asociaciones.AsociacionController;
import com.visitapp.visitstoreapp.sistema.controllers.tiendas.TiendaController;
import com.visitapp.visitstoreapp.sistema.domain.asociaciones.Asociacion;
import com.visitapp.visitstoreapp.sistema.domain.genericos.Direccion;
import com.visitapp.visitstoreapp.sistema.domain.tiendas.Tienda;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;
import com.visitapp.visitstoreapp.sistema.services.GeneralServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class AsociacionConfiguracionLimites extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener {

    GeneralServices generalServices = new GeneralServices();

    TextView botonPopUpDirecciones;
    Dialog dialogListadoDirecciones;
    Dialog dialogCrearDireccion;
    AsociacionController asociacionController = new AsociacionController();
    Asociacion asociacion = new Asociacion();
    TiendaController tiendaController = new TiendaController();
    List<Tienda> listadoTiendasAsociacion = new ArrayList<>();

    //PopUp
    FloatingActionButton botonNuevaDireccion;
    FloatingActionButton botonEliminarDireccion;

    //popUp direcciones
    Dialog direccionesPopUp;
    EditText direccionCalle;
    EditText direccionNumero;
    EditText direccionCodigoPostal;
    EditText direccionCiudad;
    EditText direccionPais;
    FloatingActionButton aceptarDireccion;
    FloatingActionButton cancelarDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asociacion_configuracion_limites);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cargarDatosAsociacion();

        //arrancarMapFragment();

        inicializarCampos();

        //boton popUp
        botonPopUpDirecciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPopUpListadoDirecciones();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void startPopUpListadoDirecciones() {
        System.out.println("POP UP LISTA DIRECCIONES "+asociacion.getDireccionesLimite());
        dialogListadoDirecciones = new Dialog(AsociacionConfiguracionLimites.this);
        dialogListadoDirecciones.setContentView(R.layout.pop_up_direcciones_listado);

        final ListView listadoDirecciones = dialogListadoDirecciones.findViewById(R.id.idListadoDireccionesLimites);
        ItemDireccionesListado itemDireccionesListado = new ItemDireccionesListado(
                asociacion.getDireccionesLimite(), AsociacionConfiguracionLimites.this);
        listadoDirecciones.setAdapter(itemDireccionesListado);

        //FloatingActionButton prueba = dialogListadoDirecciones.findViewById(R.id.floatingPrueba);

        botonNuevaDireccion = dialogListadoDirecciones.findViewById(R.id.idNuevaDireccionLimite);
        botonEliminarDireccion = dialogListadoDirecciones.findViewById(R.id.idBorrarDireccionLimite);
        FloatingActionButton cerrarPopUpDireccionesListado = dialogListadoDirecciones.findViewById(R.id.idCerrarPopUpDireccionesListado);

        dialogListadoDirecciones.show();

        cerrarPopUpDireccionesListado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CERRAR");
                dialogListadoDirecciones.dismiss();
            }
        });

        botonEliminarDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICK EN ELIMINAR");
            }
        });

        botonNuevaDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("NUEVA DIRECCION");
                dialogCrearDireccion = new Dialog(AsociacionConfiguracionLimites.this);
                dialogCrearDireccion.setContentView(R.layout.pop_up_introducir_direccion_tienda);

                direccionCalle = dialogCrearDireccion.findViewById(R.id.idCalleTiendaPopUp);
                direccionNumero = dialogCrearDireccion.findViewById(R.id.idNumeroTiendaPopup);
                direccionCodigoPostal = dialogCrearDireccion.findViewById(R.id.idCodigoPostalTiendaPopUp);
                direccionCiudad = dialogCrearDireccion.findViewById(R.id.idCiudadTiendaPopUp);
                direccionPais = dialogCrearDireccion.findViewById(R.id.idPaisTiendaPopUp);

                aceptarDireccion = dialogCrearDireccion.findViewById(R.id.idButtonAplicarCambiosDireccionTienda);
                cancelarDireccion = dialogCrearDireccion.findViewById(R.id.idButtonCancelarCambiosDireccionTienda);

                dialogCrearDireccion.show();

                aceptarDireccion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Direccion direccionGuardar = new Direccion();
                        direccionGuardar.setCalle(direccionCalle.getText().toString());
                        direccionGuardar.setNumCalle(direccionNumero.getText().toString());
                        direccionGuardar.setPostalCode(direccionCodigoPostal.getText().toString());
                        direccionGuardar.setCiudad(direccionCiudad.getText().toString());
                        direccionGuardar.setPais(direccionPais.getText().toString());

                        Barcode.GeoPoint direcCoor = generalServices.getLocationFromAddress(
                                direccionGuardar.toString(), AsociacionConfiguracionLimites.this);
                        if(direcCoor != null){
                            direccionGuardar.setLatitud(direcCoor.lat);
                            direccionGuardar.setLongtud(direcCoor.lng);
                        }

                        List<Direccion> listaDirecciones = asociacion.getDireccionesLimite();
                        if(listaDirecciones == null){
                            listaDirecciones = new ArrayList<>();
                        }
                        System.out.println("DIRECCION: "+direccionGuardar.getCalle());
                        listaDirecciones.add(direccionGuardar);

                        asociacion.setDireccionesLimite(listaDirecciones);
                        asociacionController.update(asociacion);

                        System.out.println("ACTUALIZADO");

                        dialogCrearDireccion.dismiss();
                        //startPopUpListadoDirecciones();

                        ItemDireccionesListado itemDireccionesListado = new ItemDireccionesListado(
                                asociacion.getDireccionesLimite(), AsociacionConfiguracionLimites.this);
                        listadoDirecciones.setAdapter(itemDireccionesListado);

                        cargarDatosAsociacion();
                    }
                });

                cancelarDireccion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogCrearDireccion.dismiss();
                    }
                });
            }
        });




    }

    private void cargarDatosAsociacion() {
        System.out.println("ID ASOCIACION "+USUARIO_ACTUAL.getParametrosUsuarioActual().getAcceso_asociacion_id());
        asociacion.set_id(USUARIO_ACTUAL.getParametrosUsuarioActual().getAcceso_asociacion_id());
        asociacionController.read(asociacion, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                asociacion = data.getValue(Asociacion.class);
                //System.out.println("ASOCIACION DATOS:::"+asociacion.get_id()+" - "+asociacion.getNombre());
                cargarTiendasAsociacion();
                //arrancarMapFragment();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    private void cargarTiendasAsociacion() {
        tiendaController.queryEquals("asociacion_id", USUARIO_ACTUAL.getParametrosUsuarioActual().getAcceso_asociacion_id(), new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                for(DataSnapshot item : data.getChildren()){
                    Tienda tienda = item.getValue(Tienda.class);
                    listadoTiendasAsociacion.add(tienda);
                }
                arrancarMapFragment();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarCampos() {
        botonPopUpDirecciones = findViewById(R.id.idBotonDireccionesMapaLimite);
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


        /*Polygon polygon1 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(-27.457, 153.040),
                        new LatLng(-33.852, 151.211),
                        new LatLng(-37.813, 144.962),
                        new LatLng(-34.928, 138.599)));*/
        //LatLng centrar = new LatLng();

        PolygonOptions options = new PolygonOptions().clickable(true);
        //Asigna direcciones Limite
        /*for(Direccion direccion : asociacion.getDireccionesLimite()){
            options.add(new LatLng(direccion.getLatitud(), direccion.getLongtud()));
        }*/

        Map centro = calcularCentro();

        //asigna como area las direcciones de las tiendas
        //Marcadores de las tiendas
        for(Tienda tienda : listadoTiendasAsociacion){
            System.out.println("RECORRE TIENDAS");
            LatLng posicion = new LatLng(tienda.getDireccion().getLatitud(), tienda.getDireccion().getLongtud());
            googleMap.addMarker(new MarkerOptions()
                    .position(posicion)
                    .title(tienda.getNombrePublico())
                    .snippet(tienda.getDireccion().direccionItemPopUp())
                    /*.flat(true)*/
                    /*.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_asociacion_menu_tiendas))*/
                    /*.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))*/);
            options.add(new LatLng(tienda.getDireccion().getLatitud(), tienda.getDireccion().getLongtud()));

            /*googleMap.addCircle(new CircleOptions()
                    .center(new LatLng(tienda.getDireccion().getLatitud(), tienda.getDireccion().getLongtud()))
                    .radius(100)
                    .strokeColor(Color.RED)
                    .fillColor(Color.BLUE));*/
        }

        /*googleMap.addMarker(new MarkerOptions()
                        .position(centro)
                        .title("Centro")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        googleMap.addCircle(new CircleOptions()
                .center(centro)
                .radius(300)
                .strokeColor(0x3F00FF00)
                .fillColor(0x3F00FF00)
                .clickable(true));*/

        Polygon polygon1 = googleMap.addPolygon(options);
// Store a data object with the polygon, used here to indicate an arbitrary type.
        //polygon1.setTag("alpha");
        polygon1.setFillColor(0x3F00FF00);//color contenido area
        polygon1.setStrokeColor(0x3F00FF00);//color contenido borde
        //polygon1.setStrokeColor(0xffF57F17);

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centro, 15));

        // Set listeners for click events.
        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);
    }

    private Map calcularCentro() {
        Map<String, Double> mapa = new HashMap<>();
        double x1 = listadoTiendasAsociacion.get(0).getDireccion().getLatitud();//menor
        double x2 = listadoTiendasAsociacion.get(0).getDireccion().getLatitud();//mayor

        double y1 = listadoTiendasAsociacion.get(0).getDireccion().getLongtud();//menor
        double y2 = listadoTiendasAsociacion.get(0).getDireccion().getLongtud();//mayor

        for (Tienda tienda : listadoTiendasAsociacion){
            if(tienda.getDireccion().getLatitud() < x1){
                x1 = tienda.getDireccion().getLatitud();
            }else if(tienda.getDireccion().getLatitud() > x2){
                x2 = tienda.getDireccion().getLatitud();
            }

            if(tienda.getDireccion().getLongtud() < y1){
                y1 = tienda.getDireccion().getLongtud();
            }else if(tienda.getDireccion().getLongtud() > y2){
                y2 = tienda.getDireccion().getLongtud();
            }
        }

        double centroX = x1 + ((x2 - x1) / 2);
        double centroY = y1 + ((y2 - y1) / 2);

        double distanciaMax = encontrarDistanciaMaxima();

        mapa.put("maxDistance", distanciaMax);
        mapa.put("centroX", centroX);
        mapa.put("centroY", centroY);

        return mapa;
    }

    private Location crearLocation(Tienda tienda){
        Location locationA = new Location(tienda.getNombrePublico());
        locationA.setLongitude(tienda.getDireccion().getLatitud());
        locationA.setLatitude(tienda.getDireccion().getLongtud());
        return locationA;
    }

    private double encontrarDistanciaMaxima() {
        double distancia;
        if(listadoTiendasAsociacion.size() > 1){
            Location locationA = crearLocation(listadoTiendasAsociacion.get(0));
            Location locationB = crearLocation(listadoTiendasAsociacion.get(1));
            distancia = locationA.distanceTo(locationB);
        }else{
            distancia = 0.0;
        }

        /*double distanciaMax = LatLng(
                listadoTiendasAsociacion.get(0).getDireccion().getLongtud(),
                listadoTiendasAsociacion.get(0).getDireccion().getLatitud()).*/

        for (Tienda tienda : listadoTiendasAsociacion){
            Location location1 = crearLocation(tienda);
            for (Tienda tienda2 : listadoTiendasAsociacion){
                Location location2 = crearLocation(tienda2);
                double distancia2 = location1.distanceTo(location2);
                if(distancia2 > distancia){
                    distancia = distancia2;
                }
            }

        }
        return distancia;
    }
}
