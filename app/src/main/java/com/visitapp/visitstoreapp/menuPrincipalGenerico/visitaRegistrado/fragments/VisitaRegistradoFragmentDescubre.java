package com.visitapp.visitstoreapp.menuPrincipalGenerico.visitaRegistrado.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.huxq17.swipecardsview.SwipeCardsView;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.adapter.CardAdapter;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.visitaRegistrado.VisitaRegistradoMenuPrincipal;
import com.visitapp.visitstoreapp.sistema.controllers.productos.ProductoController;
import com.visitapp.visitstoreapp.sistema.domain.productos.Producto;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class VisitaRegistradoFragmentDescubre extends Fragment {

    private SwipeCardsView swipeCardsView;
    private FusedLocationProviderClient mFusedLocationClient;

    GoogleApiClient mGoogleApiClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_visita_registrado_descubre, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        //LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        /*double longitude = location.getLongitude();
        double latitude = location.getLatitude();*/

        System.out.println("POSICION USUARIO " + mFusedLocationClient);

        swipeCardsView = view.findViewById(R.id.idCartaDescubre);
        swipeCardsView.retainLastCard(false);
        swipeCardsView.enableSwipe(true);
        getData();
        super.onViewCreated(view, savedInstanceState);
    }

    private void getData() {
        /*modelList.add(new Model("Camisa", "https://i2.linio.com/p/e7ad556f25504c845047bd9a43e511ef-product.jpg"));
        modelList.add(new Model("Pantalon", "https://media.wuerth.com/stmedia/modyf/shop/900px/1370434.jpg"));
        modelList.add(new Model("Zapatos", "https://cdn.ferragamo.com/wcsstore/FerragamoCatalogAssetStore/images/products/551035/551035_00.png"));*/

        ProductoController productoController = new ProductoController();
        productoController.getList(new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                List<Producto> productoControllerList = new ArrayList<>();
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
