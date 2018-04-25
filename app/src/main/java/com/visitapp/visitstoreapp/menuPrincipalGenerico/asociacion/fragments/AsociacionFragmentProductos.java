package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.AsociacionMenuPrincipal;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.activities.AsociacionProductoFormulario;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.adapter.ItemProductoListado;
import com.visitapp.visitstoreapp.sistema.controllers.productos.ProductoController;
import com.visitapp.visitstoreapp.sistema.domain.productos.Producto;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.util.ArrayList;
import java.util.List;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class AsociacionFragmentProductos extends Fragment {
    GridView gridProductos;
    FloatingActionButton botonAddProducto;
    List<Producto> productoList;
    private PopupWindow popUpWindow;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asociacion_productos, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final UsuarioParametros usuarioParametros = USUARIO_ACTUAL.getParametrosUsuarioActual();
        botonAddProducto = view.findViewById(R.id.idButtonAddProducto);
        gridProductos = view.findViewById(R.id.idGridViewProductosAsociacion);
        ProductoController productoController = new ProductoController();

        productoController.queryEquals("asociacion_id", usuarioParametros.getAcceso_asociacion_id(), new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                productoList = new ArrayList<>();
                for(DataSnapshot element : data.getChildren()){
                    Producto producto = element.getValue(Producto.class);
                    productoList.add(producto);
                }
                ItemProductoListado item = new ItemProductoListado(productoList, view.getContext());
                gridProductos.setAdapter(item);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });

        /*productoController.getList(new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                List<Producto> lista = new ArrayList<>();
                for(DataSnapshot element : data.getChildren()){
                    Producto producto = element.getValue(Producto.class);
                    if(producto.getAsociacion_id().equals(usuarioParametros.getAcceso_asociacion_id())){
                        lista.add(producto);
                    }
                }
                ItemProductoListado item = new ItemProductoListado(lista, view.getContext());
                gridProductos.setAdapter(item);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });*/

        botonAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("TENDRIA QUE CAMBIAR DE ACTIVITY");
                Intent a = new Intent(getActivity(), AsociacionProductoFormulario.class);
                startActivity(a);
            }
        });

        gridProductos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("LONG CLICK EN ITEM"+productoList.get(position).getNombre());

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.pop_up_edicion_producto_listado);
                dialog.show();
                TextView tituloProductoPopUp = dialog.findViewById(R.id.idTituloPopUpProductoListado);
                FloatingActionButton botonEdicion = dialog.findViewById(R.id.idFloatButtonEdicionProductoPopUp);
                FloatingActionButton botonDelete = dialog.findViewById(R.id.idFloatButtonDeleteProductoListadoPopUp);
                String concatTitulo = productoList.get(position).getCodigo()+" - "+productoList.get(position).getNombre();
                tituloProductoPopUp.setText(concatTitulo);
                botonEdicion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("EDICION");
                    }
                });

                botonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("DELETE");
                    }
                });





                return false;
            }
        });

        /*gridProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("CLICK EN ITEM"+productoList.get(position).getNombre());
            }
        });*/
    }

    /*private void goToCrearNuevoProducto() {
        Intent a = new Intent(getActivity(), AsociacionMenuPrincipal.class);
        startActivity(a);
    }*/
}
