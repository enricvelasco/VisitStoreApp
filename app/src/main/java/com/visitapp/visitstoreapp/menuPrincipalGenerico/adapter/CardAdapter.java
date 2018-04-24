package com.visitapp.visitstoreapp.menuPrincipalGenerico.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huxq17.swipecardsview.BaseCardAdapter;
import com.squareup.picasso.Picasso;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.sistema.domain.productos.Producto;

import java.util.List;

public class CardAdapter extends BaseCardAdapter{

    private List<Producto> productosList;
    private Context context;

    public CardAdapter(List<Producto> productosList, Context context) {
        this.productosList = productosList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productosList.size();
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.card_item;
    }

    @Override
    public void onBindData(int position, View cardview) {
        if(productosList==null || productosList.size()==0){
            return;
        }
        Producto producto = productosList.get(position);
        ImageView imageView = cardview.findViewById(R.id.idImagenCartaProducto);
        TextView nombre = cardview.findViewById(R.id.idNombreProductoCarta);

        nombre.setText(producto.getNombre());
        Picasso.with(context).load(producto.getImagen()).resize(1024,1024).centerCrop().into(imageView);
        /*textView.setText(model.getTitle());
        Picasso.with(context).load(model.getImage()).into(imageView);*/
    }
}
