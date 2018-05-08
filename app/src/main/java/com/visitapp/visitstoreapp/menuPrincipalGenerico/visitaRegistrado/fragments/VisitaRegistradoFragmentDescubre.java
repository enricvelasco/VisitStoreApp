package com.visitapp.visitstoreapp.menuPrincipalGenerico.visitaRegistrado.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.huxq17.swipecardsview.SwipeCardsView;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.adapter.CardAdapter;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.visitaRegistrado.VisitaRegistradoMenuPrincipal;
import com.visitapp.visitstoreapp.sistema.controllers.asociaciones.AsociacionController;
import com.visitapp.visitstoreapp.sistema.controllers.productos.ProductoController;
import com.visitapp.visitstoreapp.sistema.controllers.tiendas.TiendaController;
import com.visitapp.visitstoreapp.sistema.domain.asociaciones.Asociacion;
import com.visitapp.visitstoreapp.sistema.domain.productos.Producto;
import com.visitapp.visitstoreapp.sistema.domain.tiendas.Tienda;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;
import com.visitapp.visitstoreapp.sistema.services.GeneralServices;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class VisitaRegistradoFragmentDescubre extends Fragment {

    private SwipeCardsView swipeCardsView;
    private FusedLocationProviderClient mFusedLocationClient;

    GeneralServices generalServices = new GeneralServices();

    Location mLastLocation;
    LocationRequest mLocationRequest;

    LatLng posicionUsuario;

    List<Producto> productoControllerList = new ArrayList<>();

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                //Place current location marker
                posicionUsuario = new LatLng(location.getLatitude(), location.getLongitude());
                System.out.println("LATITUD"+posicionUsuario.latitude);
                System.out.println("LONGITUD"+posicionUsuario.longitude);

                empezarCargaDeDatos();


            }
        }
    };

    private void empezarCargaDeDatos() {
        AsociacionController asociacionController = new AsociacionController();
        asociacionController.getList(new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                final List<Asociacion> asociacionList = new ArrayList<>();
                for(DataSnapshot item : data.getChildren()){
                    Asociacion asociacion = item.getValue(Asociacion.class);
                    asociacionList.add(asociacion);
                }

                TiendaController tiendaController = new TiendaController();
                tiendaController.getList(new OnGetDataListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(DataSnapshot data) {
                        List<Tienda> tiendaList = new ArrayList<>();
                        for(DataSnapshot item : data.getChildren()){
                            Tienda tienda = item.getValue(Tienda.class);
                            tiendaList.add(tienda);
                        }

                        respuestaDeDatos(asociacionList, tiendaList);

                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });


    }

    private void respuestaDeDatos(List<Asociacion> asociacionList, List<Tienda> tiendaList) {
        List<Map> listado = generalServices.detectarAsociacionActualPosicion(posicionUsuario, asociacionList, tiendaList);



        for(Map item : listado){
            System.out.println("ASOCIACION: "+item.get("asociacion"));
            //boolean isIn = (boolean) item.get("mapIsIn");
            System.out.println("IS IN: "+item.get("mapIsIn"));
            //double distancia = (double) item.get("distancia");
            System.out.println("DISTANCIA: "+item.get("distancia"));

            //cargar el listado de los productos y poner la asociacion como el primero
            if(Boolean.parseBoolean(String.valueOf(item.get("mapIsIn")))){

                //Creamos un producto que en realidad Sera una asociacion para que salga el primero
                Producto producto = new Producto();
                producto.setNombre((String) item.get("asociacionNombre"));
                producto.setImagen((String) item.get("asociacionImagen"));

                productoControllerList.add(producto);

                getData(String.valueOf(item.get("asociacion")));
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_visita_registrado_descubre, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        //mFusedLocationClient.
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

        //LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        /*double longitude = location.getLongitude();
        double latitude = location.getLatitude();*/

        System.out.println("POSICION USUARIO " + mFusedLocationClient);

        swipeCardsView = view.findViewById(R.id.idCartaDescubre);
        swipeCardsView.retainLastCard(false);
        swipeCardsView.enableSwipe(true);
        //getData();
        super.onViewCreated(view, savedInstanceState);
    }

    private void getData(String idAsociacion) {
        /*modelList.add(new Model("Camisa", "https://i2.linio.com/p/e7ad556f25504c845047bd9a43e511ef-product.jpg"));
        modelList.add(new Model("Pantalon", "https://media.wuerth.com/stmedia/modyf/shop/900px/1370434.jpg"));
        modelList.add(new Model("Zapatos", "https://cdn.ferragamo.com/wcsstore/FerragamoCatalogAssetStore/images/products/551035/551035_00.png"));*/

        ProductoController productoController = new ProductoController();
        productoController.queryEquals("asociacion_id", idAsociacion, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                //List<Producto> productoControllerList = new ArrayList<>();
                for (DataSnapshot item : data.getChildren()) {
                    Producto producto = item.getValue(Producto.class);
                    productoControllerList.add(producto);
                }

                CardAdapter cardAdapter = new CardAdapter(productoControllerList, getActivity());
                swipeCardsView.setAdapter(cardAdapter);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });

        /*@Override
        public void onPause() {
            super.onPause();

            //stop location updates when Activity is no longer active
            if (mFusedLocationClient != null) {
                mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            }
        }*/



        /*CardAdapter cardAdapter = new CardAdapter(modelList, getActivity());
        swipeCardsView.setAdapter(cardAdapter);*/

    }
/*
    @Override
    public void onLocationChanged(Location location) {
        System.out.println("MI LOCALIZACION LATITUD "+location.getLatitude());
        System.out.println("MI LOCALIZACION LONGITUD "+location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getActivity(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }*/
}
