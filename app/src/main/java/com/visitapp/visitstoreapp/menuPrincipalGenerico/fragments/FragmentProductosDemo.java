package com.visitapp.visitstoreapp.menuPrincipalGenerico.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.sistema.controllers.asociaciones.AsociacionController;
import com.visitapp.visitstoreapp.sistema.controllers.asociaciones.DistritoAsociacionController;
import com.visitapp.visitstoreapp.sistema.controllers.productos.ProductoController;
import com.visitapp.visitstoreapp.sistema.controllers.productos.ProductoTipoController;
import com.visitapp.visitstoreapp.sistema.controllers.tiendas.TiendaController;
import com.visitapp.visitstoreapp.sistema.controllers.usuarios.UsuarioParametrosController;
import com.visitapp.visitstoreapp.sistema.domain.asociaciones.Asociacion;
import com.visitapp.visitstoreapp.sistema.domain.asociaciones.DistritoAsociacion;
import com.visitapp.visitstoreapp.sistema.domain.productos.Producto;
import com.visitapp.visitstoreapp.sistema.domain.productos.ProductoTipo;
import com.visitapp.visitstoreapp.sistema.domain.tiendas.Tienda;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentProductosDemo extends Fragment{
    Button botonGenerarDemo;
    Button generarAsociaciones;
    Button generarTiendas;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        System.out.println("ENTRA EN EL FRAGMENT");



        return inflater.inflate(R.layout.fragment_productos_demo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        botonGenerarDemo = view.findViewById(R.id.buttonGenerarDataDemo);
        /*generarAsociaciones = view.findViewById(R.id.buttonGenerarAsociaciones);
        generarAsociaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcGenerarAsociaciones();
            }
        });

        generarTiendas = view.findViewById(R.id.buttonGenerarTiendas);
        generarTiendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcGenerarTiendas();
            }
        });*/


        botonGenerarDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //generarDatos();
                generarDemo();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void funcGenerarTiendas() {
        //Tiendas Asociacion Comerç i arts
        /*Tienda t1 = new Tienda();
        t1.setNif();
        t1.setNombreEmpresa();
        t1.setNombreNombrePublico();
        t1.setAsociacion();*/


    }

    private void generarDemo(){

        myRef.child("distritosAsociaciones").removeValue();
        myRef.child("asociaciones").removeValue();
        myRef.child("tiendas").removeValue();
        //myRef.child("productosTipo").removeValue();
        myRef.child("productos").removeValue();

        //funcTiposDeProducto();

        DistritoAsociacion distritoAsociacion = new DistritoAsociacion();
        distritoAsociacion.setNombre("Gràcia");
        distritoAsociacion.setObservaciones("Districte de la zona de Gràcia");
        DistritoAsociacionController dc = new DistritoAsociacionController();
        dc.save(distritoAsociacion);

        final Asociacion a2 = funcGenerarAsociaciones(distritoAsociacion);
        //distritoAsociacion.setAsociaciones(asociacionesD1);
        dc.update(distritoAsociacion);

        final UsuarioParametrosController usuarioParametrosController = new UsuarioParametrosController();
        final UsuarioParametros u1 = new UsuarioParametros();
        u1.set_id("vKnkdtJbUwXHWCPFRJtLHAIVX4z1");//asociacion demo

        UsuarioParametros u2 = new UsuarioParametros();
        u2.set_id("oJv8tKmbI7NJD0XzYFl5omBM0pr2");//local demo
        usuarioParametrosController.read(u1, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                UsuarioParametros up1 = data.getValue(UsuarioParametros.class);
                up1.setAcceso_asociacion_id(a2.get_id());
                up1.setAcceso_tienda_id("all");
                usuarioParametrosController.update(up1);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
        usuarioParametrosController.read(u2, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                UsuarioParametros up1 = data.getValue(UsuarioParametros.class);
                up1.setAcceso_asociacion_id(a2.get_id());
                up1.setAcceso_tienda_id("insertar valor");
                usuarioParametrosController.update(up1);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    private Asociacion funcGenerarAsociaciones(DistritoAsociacion distritoAsociacion) {

        Map object = new HashMap();
        AsociacionController ac1 = new AsociacionController();

        Asociacion a1 = new Asociacion();
        a1.setNombre("Associació de Comerç i Arts de la Riera Sant Miquel i Voltants");
        a1.setObservaciones("");
        a1.setDireccion("C Riera de Sant Miquel 29");
        a1.setTelefono("635989470");
        a1.setLogo("pendiente/url");
        a1.setDistrito_id(distritoAsociacion.get_id());
        //arrayTiendasDistrito1.add(a1);
        object.put(a1.get_id(), a1);

        Asociacion a2 = new Asociacion();
        a2.setNombre("Associació de Comerciants Gràcia Centre");
        a2.setObservaciones("");
        a2.setDireccion("C Bonavista 19");
        a2.setTelefono("609035162");
        a2.setLogo("pendiente/url");
        a2.setDistrito_id(distritoAsociacion.get_id());
        //arrayTiendasDistrito1.add(a2);
        object.put(a2.get_id(), a2);

        Asociacion a3 = new Asociacion();
        a3.setNombre("Associació Comerciants Travessera de Gràcia Centre i Puigmartí");
        a3.setObservaciones("");
        a3.setDireccion("Trav Gràcia 191");
        a3.setTelefono("932135604");
        a3.setLogo("pendiente/url");
        a3.setDistrito_id(distritoAsociacion.get_id());
        //arrayTiendasDistrito1.add(a3);
        object.put(a3.get_id(), a3);


        Asociacion a5 = new Asociacion();
        a5.setNombre("Associació de Comerciants Torrijos La Nova de Baix");
        a5.setObservaciones("");
        a5.setDireccion(" C Torrijos 28");
        a5.setTelefono("");
        a5.setLogo("pendiente/url");
        a5.setDistrito_id(distritoAsociacion.get_id());
        //arrayTiendasDistrito1.add(a5);
        object.put(a5.get_id(), a5);


        Asociacion a4 = new Asociacion();
        a4.setNombre("Associació de Comerciants de Travessera de Dalt");
        a4.setObservaciones("");
        a4.setDireccion("Trav Dalt 40");
        a4.setTelefono("606608669");
        a4.setLogo("pendiente/url");
        a4.setDistrito_id(distritoAsociacion.get_id());
        //arrayTiendasDistrito1.add(a4);
        object.put(a4.get_id(), a4);


        funcionGenerarTiendas(a1,1,2);
        funcionGenerarTiendas(a2,2,3);
        funcionGenerarTiendas(a3,3,4);
        funcionGenerarTiendas(a4,4,5);
        funcionGenerarTiendas(a5,5,6);
        /*Map tiendadasAsoc1 = funcionGenerarTiendas(a1,1,10);
        Map tiendadasAsoc2 = funcionGenerarTiendas(a2,2,6);
        Map tiendadasAsoc3 = funcionGenerarTiendas(a3,3,4);
        Map tiendadasAsoc4 = funcionGenerarTiendas(a4,4,5);
        Map tiendadasAsoc5 = funcionGenerarTiendas(a5,5,2);*/

        /*a1.setTiendas(tiendadasAsoc1);
        a2.setTiendas(tiendadasAsoc2);
        a3.setTiendas(tiendadasAsoc3);
        a4.setTiendas(tiendadasAsoc4);
        a5.setTiendas(tiendadasAsoc5);*/

        ac1.save(a1);
        ac1.save(a2);
        ac1.save(a3);
        ac1.save(a4);
        ac1.save(a5);

        return a2;
    }

    private void funcionGenerarTiendas(Asociacion a1, int numBase, int cantidad) {

        Map object = new HashMap();
        TiendaController tiendaController = new TiendaController();

        for(int i=0;i<cantidad;i++){
            Tienda tienda = new Tienda();
            tienda.setNif("00"+numBase+i);
            tienda.setNombreFiscal("Local Fiscal "+numBase+i);
            tienda.setNombreNombrePublico("Tienda "+numBase+i);
            tienda.setAsociacion_id(a1.get_id());


            //Map productosTipo = funcTiposDeProducto(tienda);
            //tienda.setProductosTipo(productosTipo);
            tiendaController.save(tienda);

            generarProductosTienda(tienda, numBase, 8);
            //Map productos = funcGenerarProductos(tienda, numBase, 5);


        }

        //asignar los productos
        /*ProductoController productoController = new ProductoController();
        productoController.getList(new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                int cont = 0;
                for(DataSnapshot element : data.getChildren()){
                    Producto p = element.getValue(Producto.class);
                    if(){

                    }
                    cont ++;
                }

            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });*/

        //return object;
    }

    private void generarProductosTienda(Tienda tienda, int numBase, int cantidad) {
        ProductoController productoController = new ProductoController();
        for(int i=0;i<cantidad;i++){
            Producto producto = new Producto();
            producto.setNombre("PROD_"+numBase+i);
            producto.setCodigo("00"+numBase+i);
            producto.setTienda_id(tienda.get_id());
            if(i < 4){
                producto.setProductosTipo_id("4fd1dd19-f2a3-4bce-94f9-78233d88d1b2");
            }else{
                producto.setProductosTipo_id("cb028cec-ccf9-4e51-b5ec-816d447446f2");
            }
            productoController.save(producto);
        }
    }

    private void funcGenerarProductos(ProductoTipo productoTipo, int numBase, int cantidad) {
        ProductoController productoController = new ProductoController();
        Map object = new HashMap();
        for(int i=0;i<cantidad;i++){
            Producto producto = new Producto();
            producto.setNombre(productoTipo.getDescripcion()+numBase+i);
            producto.setCodigo("00"+numBase+i);

            productoController.save(producto);

            object.put(producto.get_id(),producto);
        }

        //return object;
    }

    private void funcTiposDeProducto() {
        Map object = new HashMap();
        ProductoTipoController productoTipoController = new ProductoTipoController();
        ProductoTipo productoTipo1 = new ProductoTipo();
        productoTipo1.setDescripcion("Camisetas");
        //productoTipo1.setTienda_id(tienda.get_id());
        funcGenerarProductos(productoTipo1, 1, 15);

        ProductoTipo productoTipo2 = new ProductoTipo();
        productoTipo2.setDescripcion("Pantalones");
        funcGenerarProductos(productoTipo2, 2, 11);
        //productoTipo2.setTienda_id(tienda.get_id());

        ProductoTipo productoTipo3 = new ProductoTipo();
        productoTipo3.setDescripcion("Camisas");
        funcGenerarProductos(productoTipo3, 3, 9);
        //productoTipo3.setTienda_id(tienda.get_id());

        ProductoTipo productoTipo4 = new ProductoTipo();
        productoTipo4.setDescripcion("Vestido Largo");
        funcGenerarProductos(productoTipo4, 4, 6);
        //productoTipo4.setTienda_id(tienda.get_id());

        /*Map productos1 = funcGenerarProductos(productoTipo1,1,12);
        Map productos2 = funcGenerarProductos(productoTipo2,2,16);
        Map productos3 = funcGenerarProductos(productoTipo3,3,10);
        Map productos4 = funcGenerarProductos(productoTipo4,4,15);

        productoTipo1.setProductos(productos1);
        productoTipo2.setProductos(productos2);
        productoTipo3.setProductos(productos3);
        productoTipo4.setProductos(productos4);*/

        productoTipoController.save(productoTipo1);
        productoTipoController.save(productoTipo2);
        productoTipoController.save(productoTipo3);
        productoTipoController.save(productoTipo4);

        /*object.put(productoTipo1.get_id(),productoTipo1);
        object.put(productoTipo2.get_id(),productoTipo2);
        object.put(productoTipo3.get_id(),productoTipo3);
        object.put(productoTipo4.get_id(),productoTipo4);*/

        //return object;
    }
}
