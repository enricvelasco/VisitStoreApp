package com.visitapp.visitstoreapp.menuPrincipalGenerico.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.sistema.controllers.asociaciones.AsociacionController;
import com.visitapp.visitstoreapp.sistema.domain.asociaciones.Asociacion;

public class FragmentProductosDemo extends Fragment{
    Button botonGenerarDemo;
    Button generarAsociaciones;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        System.out.println("ENTRA EN EL FRAGMENT");



        return inflater.inflate(R.layout.fragment_productos_demo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        botonGenerarDemo = view.findViewById(R.id.buttonGenerarDataDemo);
        generarAsociaciones = view.findViewById(R.id.buttonGenerarAsociaciones);
        generarAsociaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcGenerarAsociaciones();
            }
        });
        /*botonGenerarDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarDatos();
            }
        });*/
        super.onViewCreated(view, savedInstanceState);
    }

    private void funcGenerarAsociaciones() {
        Asociacion a1 = new Asociacion();
        a1.setNombre("Asociación 1");
        a1.setObservaciones("primera Asociacion creada");
        a1.setLogo("pendiente/url");
        AsociacionController ac1 = new AsociacionController(a1);
        ac1.save();
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
