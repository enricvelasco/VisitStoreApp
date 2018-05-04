package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.sistema.domain.genericos.Direccion;

import java.util.List;

public class ItemDireccionesListado extends BaseAdapter {
    private List<Direccion> listaDirecciones;
    private Context context;

    public ItemDireccionesListado(List<Direccion> listaDirecciones, Context context) {
        this.listaDirecciones = listaDirecciones;
        this.context = context;
    }

    @Override
    public int getCount() {
        int retorno = 0;
        if(listaDirecciones != null){
            retorno = listaDirecciones.size();
        }
        return retorno;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Direccion direccion = listaDirecciones.get(position);
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_direccion_listado, null);
        }
        TextView calle = convertView.findViewById(R.id.idCalleItem);
        TextView codigoPostal = convertView.findViewById(R.id.idCodigoPostalItem);
        TextView ciudad = convertView.findViewById(R.id.idCiudadItem);
        TextView pais = convertView.findViewById(R.id.idPaisItem);

        calle.setText(direccion.direccionItemPopUp());
        codigoPostal.setText(direccion.getPostalCode());
        ciudad.setText(direccion.getCiudad());
        pais.setText(direccion.getPais());

        return convertView;
    }
}
