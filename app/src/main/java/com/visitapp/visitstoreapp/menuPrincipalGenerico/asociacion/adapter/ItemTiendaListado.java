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

        Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/visitstoreapp.appspot.com/o/tienda%2Fbf65155f-75de-45a9-b4e0-63ee60884db6%2Flogo_store_1.jpg?alt=media&token=27194309-19ab-42a1-bbe2-c15dc0983d69").resize(200,200).centerCrop().into(imageView);

        return convertView;
    }
}
