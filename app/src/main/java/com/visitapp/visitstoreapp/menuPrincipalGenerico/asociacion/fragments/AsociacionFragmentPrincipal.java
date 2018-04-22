package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.VariablesGlobales;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.adapter.ItemTiendaListado;
import com.visitapp.visitstoreapp.sistema.controllers.tiendas.TiendaController;
import com.visitapp.visitstoreapp.sistema.controllers.tiendas.TiendaSectorController;
import com.visitapp.visitstoreapp.sistema.domain.tiendas.Tienda;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.util.ArrayList;
import java.util.List;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class AsociacionFragmentPrincipal extends Fragment{
    ListView listadoTiendas;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asociacion_principal, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final UsuarioParametros usuarioParametros = USUARIO_ACTUAL.getParametrosUsuarioActual();
        listadoTiendas = view.findViewById(R.id.idListaTiendas);

        TiendaController tiendaController = new TiendaController();
        tiendaController.queryEquals("asociacion_id", usuarioParametros.getAcceso_asociacion_id(), new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                List<Tienda> listadoTiendasList = new ArrayList<>();
                for(DataSnapshot element : data.getChildren()){
                    Tienda tienda = element.getValue(Tienda.class);
                    listadoTiendasList.add(tienda);
                }
                System.out.println("CREA ITEM PRODUCTO ***");
                ItemTiendaListado itemTiendaListado = new ItemTiendaListado(listadoTiendasList, view.getContext());
                listadoTiendas.setAdapter(itemTiendaListado);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
        /*tiendaController.getList(new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                System.out.println("HACE EL GET DE TIENDAS "+data.getValue());
                List<Tienda> listadoTiendasList = new ArrayList<>();
                for(DataSnapshot element : data.getChildren()){
                    Tienda tienda = element.getValue(Tienda.class);
                    System.out.println("TIENDA---"+tienda.getAsociacion_id());
                    if(tienda.getAsociacion_id().equals(usuarioParametros.getAcceso_asociacion_id())){
                        System.out.println("---AÑADE---");
                        listadoTiendasList.add(tienda);
                    }
                }
                ItemTiendaListado itemTiendaListado = new ItemTiendaListado(listadoTiendasList, view.getContext());
                listadoTiendas.setAdapter(itemTiendaListado);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });*/
    }
}
