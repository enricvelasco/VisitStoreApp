package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.sistema.controllers.tiendas.TiendaController;
import com.visitapp.visitstoreapp.sistema.domain.productos.Producto;
import com.visitapp.visitstoreapp.sistema.domain.tiendas.Tienda;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

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
        System.out.println("ASIGNAR PRODUCTO POSICION +++");
        // 1
        final Producto producto = (Producto) listaProductos.get(position);

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_producto_listado, null);
        }

        // 3
        //RelativeLayout frame = convertView.findViewById(R.id.frameContenidoIconoMenu);
        final ImageView imageView = convertView.findViewById(R.id.idPictureProductoListado);
        TextView nombre = convertView.findViewById(R.id.idNombreItemNombreProducto);
        TextView precio = convertView.findViewById(R.id.idPrecioItemProducto);
        final TextView nombreTienda = convertView.findViewById(R.id.idNombreTiendaItemProducto);

        nombre.setText(producto.getNombre());
        precio.setText(producto.getPrecio());

        TiendaController tiendaController = new TiendaController();
        tiendaController.read(producto.getTienda_id(), new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                Tienda tienda = data.getValue(Tienda.class);
                if(tienda != null){
                    nombreTienda.setText(tienda.getNombrePublico());
                }

            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
        /*nombre.setText(producto.getNombre());
        precio.setText("€€€");*/
        if(producto.getImagen() != null){
            //Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/visitstoreapp.appspot.com/o/tienda%2Fbf65155f-75de-45a9-b4e0-63ee60884db6%2Flogo_store_1.jpg?alt=media&token=27194309-19ab-42a1-bbe2-c15dc0983d69").into(imageView);
            Picasso.with(context).load(producto.getImagen()).into(imageView);
        }

        return convertView;
    }
}
