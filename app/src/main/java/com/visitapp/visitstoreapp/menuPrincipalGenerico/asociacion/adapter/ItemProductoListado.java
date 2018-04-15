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
import com.visitapp.visitstoreapp.sistema.domain.productos.Producto;
import com.visitapp.visitstoreapp.sistema.domain.tiendas.Tienda;

import java.util.List;

public class ItemProductoListado extends BaseAdapter {
    private List<Producto> listaProductos;
    private Context context;

    public ItemProductoListado(List<Producto> modelList, Context context) {
        this.listaProductos = modelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaProductos.size();
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
        final Producto producto = (Producto) listaProductos.get(position);

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_producto_listado, null);
        }

        // 3
        //RelativeLayout frame = convertView.findViewById(R.id.frameContenidoIconoMenu);
        ImageView imageView = convertView.findViewById(R.id.idPictureProductoListado);
        TextView nombre = convertView.findViewById(R.id.idNombreProductoEnGrid);
        TextView precio = convertView.findViewById(R.id.idPrecioProductoListado);

        nombre.setText(producto.getNombre());
        precio.setText("€€€");

        Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/visitstoreapp.appspot.com/o/tienda%2Fbf65155f-75de-45a9-b4e0-63ee60884db6%2Flogo_store_1.jpg?alt=media&token=27194309-19ab-42a1-bbe2-c15dc0983d69").resize(200,200).centerCrop().into(imageView);

        return convertView;
    }
}
