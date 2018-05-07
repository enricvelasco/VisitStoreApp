package com.visitapp.visitstoreapp.menuPrincipalGenerico.visitaRegistrado.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.huxq17.swipecardsview.SwipeCardsView;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.adapter.CardAdapter;
import com.visitapp.visitstoreapp.sistema.controllers.productos.ProductoController;
import com.visitapp.visitstoreapp.sistema.domain.productos.Producto;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.util.ArrayList;
import java.util.List;

public class VisitaRegistradoFragmentDescubre extends Fragment {

    private SwipeCardsView swipeCardsView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_visita_registrado_descubre, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        swipeCardsView = view.findViewById(R.id.idCartaDescubre);
        swipeCardsView.retainLastCard(false);
        swipeCardsView.enableSwipe(true);
        getData();
        super.onViewCreated(view, savedInstanceState);
    }

    private void getData() {
        /*modelList.add(new Model("Camisa", "https://i2.linio.com/p/e7ad556f25504c845047bd9a43e511ef-product.jpg"));
        modelList.add(new Model("Pantalon", "https://media.wuerth.com/stmedia/modyf/shop/900px/1370434.jpg"));
        modelList.add(new Model("Zapatos", "https://cdn.ferragamo.com/wcsstore/FerragamoCatalogAssetStore/images/products/551035/551035_00.png"));*/

        ProductoController productoController = new ProductoController();
        productoController.getList(new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                List<Producto> productoControllerList = new ArrayList<>();
                for(DataSnapshot item : data.getChildren()){
                    Producto producto = item.getValue(Producto.class);
                    productoControllerList.add(producto);
                }

                CardAdapter cardAdapter = new CardAdapter(productoControllerList, getActivity());
                swipeCardsView.setAdapter(cardAdapter);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });

        /*CardAdapter cardAdapter = new CardAdapter(modelList, getActivity());
        swipeCardsView.setAdapter(cardAdapter);*/

    }

}
