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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.VariablesGlobales;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.activities.AsociacionProductoFormulario;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.activities.AsociacionTiendaFormulario;
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
    FloatingActionButton botonAnadir;
    List<Tienda> listadoTiendasList = new ArrayList<>();
    TiendaController tiendaController = new TiendaController();
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
        botonAnadir = view.findViewById(R.id.idButtonAddNuevaTienda);

        //TiendaController tiendaController = new TiendaController();
        tiendaController.queryEquals("asociacion_id", usuarioParametros.getAcceso_asociacion_id(), new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                //List<Tienda> listadoTiendasList = new ArrayList<>();
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

        botonAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambiar a MENU PRINCIPAL
                Intent i = new Intent(getActivity(), AsociacionTiendaFormulario.class);
                startActivity(i);
            }
        });

        listadoTiendas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //Tienda tienda = listadoTiendasList.get(position);

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.pop_up_edicion_producto_listado);
                dialog.show();
                TextView tituloProductoPopUp = dialog.findViewById(R.id.idTituloPopUpProductoListado);
                FloatingActionButton botonEdicion = dialog.findViewById(R.id.idFloatButtonEdicionProductoPopUp);
                FloatingActionButton botonDelete = dialog.findViewById(R.id.idFloatButtonDeleteProductoListadoPopUp);
                String concatTitulo = listadoTiendasList.get(position).getCodigo()+" - "+listadoTiendasList.get(position).getNombrePublico();
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
                        boolean deleted = tiendaController.delete(listadoTiendasList.get(position).get_id());
                        if(deleted){
                            Toast.makeText(getActivity(), "Eliminar", Toast.LENGTH_SHORT).show();
                            //cargarListadoProductos(productoController, usuarioParametros);
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getActivity(), "Error al eliminar", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                botonEdicion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //abrir la edicion
                        Intent intent = new Intent(getActivity(), AsociacionTiendaFormulario.class);
                        intent.putExtra("tienda_id",listadoTiendasList.get(position).get_id());
                        startActivity(intent);
                    }
                });


                return false;
            }
        });

    }
}
