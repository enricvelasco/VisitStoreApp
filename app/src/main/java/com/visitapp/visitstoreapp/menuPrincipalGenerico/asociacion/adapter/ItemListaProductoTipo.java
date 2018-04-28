package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;

import com.visitapp.visitstoreapp.sistema.domain.productos.ProductoTipo;

import java.util.ArrayList;

public class ItemListaProductoTipo extends ArrayAdapter<ProductoTipo> implements View.OnClickListener{
    private ArrayList<ProductoTipo> dataSet;
    Context mContext;

    public ItemListaProductoTipo(@NonNull Context context, int resource, ArrayList<ProductoTipo> dataSet, Context mContext) {
        super(context, resource);
        this.dataSet = dataSet;
        this.mContext = mContext;
    }

    @Override
    public void onClick(View v) {

    }
}
