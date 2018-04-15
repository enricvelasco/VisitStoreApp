package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.sistema.controllers.productos.ProductoController;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class AsociacionFragmentProductos extends Fragment {
    GridView gridProductos;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asociacion_productos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final UsuarioParametros usuarioParametros = USUARIO_ACTUAL.getParametrosUsuarioActual();
        gridProductos = view.findViewById(R.id.idGridViewProductosAsociacion);
        ProductoController productoController = new ProductoController();
        productoController.getList(new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                for(DataSnapshot element : data.getChildren()){
                    
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }
}
