package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.sistema.domain.productos.ProductoTipo;

import java.util.List;

public class ItemProductoTipoListado extends BaseAdapter {
    private List<ProductoTipo> listaProductosTipo;
    private Context context;

    public ItemProductoTipoListado(List<ProductoTipo> listaProductosTipo, Context context) {
        this.listaProductosTipo = listaProductosTipo;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaProductosTipo.size();
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
        ProductoTipo productoTipo = listaProductosTipo.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_producto_tipo_listado, null);
        }

        ImageView imageView = convertView.findViewById(R.id.idImagenTipoProductoItem);

        TextView codigo = convertView.findViewById(R.id.idCodigoProductoTipoItem);
        TextView descripcion = convertView.findViewById(R.id.idDescripcionProductoTipoItem);

        codigo.setText(productoTipo.getCodigo());
        descripcion.setText(productoTipo.getDescripcion());

        if(productoTipo.getImagen() != null){
            Picasso.with(context).load(productoTipo.getImagen()).resize(200,200).centerCrop().into(imageView);
        }

        return convertView;
    }
}
