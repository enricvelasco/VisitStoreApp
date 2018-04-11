package com.visitapp.visitstoreapp.menuPrincipalGenerico.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.domain.asociaciones.Asociacion;
import com.visitapp.visitstoreapp.domain.productos.Producto;
import com.visitapp.visitstoreapp.domain.tiendas.Tienda;

import java.util.ArrayList;
import java.util.List;

public class FragmentProductosDemo extends Fragment{
    Button botonGenerarDemo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        System.out.println("ENTRA EN EL FRAGMENT");



        return inflater.inflate(R.layout.fragment_productos_demo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        botonGenerarDemo = view.findViewById(R.id.buttonGenerarDataDemo);
        /*botonGenerarDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarDatos();
            }
        });*/
        super.onViewCreated(view, savedInstanceState);
    }

    /*private void generarDatos() {
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

        List<Asociacion> asociaciones = new ArrayList<>();
        Asociacion a = new Asociacion();
        a.setNombre("Asociacion_1");
        a.setObservaciones("observaciones asociacion 1");
        a.setTiendas(listaTiendas1);
        asociaciones.add(a);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("asociaciones2");
        myRef.setValue(asociaciones);
        //myRef.push().setValue(a);
    }*/

}
