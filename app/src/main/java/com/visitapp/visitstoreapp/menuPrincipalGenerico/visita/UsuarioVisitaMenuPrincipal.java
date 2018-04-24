package com.visitapp.visitstoreapp.menuPrincipalGenerico.visita;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.huxq17.swipecardsview.SwipeCardsView;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.login.PantallaLogIn;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.adapter.CardAdapter;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.model.Model;
import com.visitapp.visitstoreapp.sistema.controllers.productos.ProductoController;
import com.visitapp.visitstoreapp.sistema.domain.productos.Producto;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.util.ArrayList;
import java.util.List;

public class UsuarioVisitaMenuPrincipal extends AppCompatActivity {
    private SwipeCardsView swipeCardsView;
    private List<Model> modelList = new ArrayList<>();

    private FirebaseAuth mAuth;
    private Button botonSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_visita_menu_principal);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        botonSalir = findViewById(R.id.idSalirDesdeVisita);

        swipeCardsView = findViewById(R.id.swipeCardsView);
        swipeCardsView.retainLastCard(false);
        swipeCardsView.enableSwipe(true);
        getData();

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), PantallaLogIn.class);
                startActivity(i);
            }
        });

    }
    private void getData() {
       /* modelList.add(new Model("Camisa", "https://i2.linio.com/p/e7ad556f25504c845047bd9a43e511ef-product.jpg"));
        modelList.add(new Model("Pantalon", "https://media.wuerth.com/stmedia/modyf/shop/900px/1370434.jpg"));
        modelList.add(new Model("Zapatos", "https://cdn.ferragamo.com/wcsstore/FerragamoCatalogAssetStore/images/products/551035/551035_00.png"));*/

        /*CardAdapter cardAdapter = new CardAdapter(modelList, this);
        swipeCardsView.setAdapter(cardAdapter);*/

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

                CardAdapter cardAdapter = new CardAdapter(productoControllerList, getApplicationContext());
                swipeCardsView.setAdapter(cardAdapter);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });

    }

}
