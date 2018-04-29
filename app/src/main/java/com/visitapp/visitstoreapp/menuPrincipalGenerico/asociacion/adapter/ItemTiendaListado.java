package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.model.Model;
import com.visitapp.visitstoreapp.sistema.domain.tiendas.Tienda;

import java.util.List;

public class ItemTiendaListado extends BaseAdapter{

    private List<Tienda> listaTiendas;
    private Context context;

    public ItemTiendaListado(List<Tienda> modelList, Context context) {
        this.listaTiendas = modelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaTiendas.size();
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
        System.out.println("ASIGNA ITEM TIENDA ////"+position);
        // 1
        final Tienda tienda = (Tienda) listaTiendas.get(position);

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_tienda_listado, null);
        }

        // 3
        //RelativeLayout frame = convertView.findViewById(R.id.frameContenidoIconoMenu);
        ImageView imageView = convertView.findViewById(R.id.idPictureTiendaLista);
        TextView nombreTienda = convertView.findViewById(R.id.idNombrePublicoTiendaListado);
        TextView nombreFiscal = convertView.findViewById(R.id.idNombreFiscalTiendaListado);

        nombreFiscal.setText(tienda.getNombreFiscal());
        nombreTienda.setText(tienda.getNombrePublico());

        Picasso.with(context).load(tienda.getLogo()).resize(200,200).centerCrop().into(imageView);

        return convertView;
    }
}
