package com.visitapp.visitstoreapp.sistema.controllers.productos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.visitapp.visitstoreapp.sistema.domain.productos.Producto;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.util.Date;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class ProductoController {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("productos");

    public ProductoController() {
    }

    public void getList(final OnGetDataListener listener){
        listener.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    public void read(Producto producto, final OnGetDataListener listener){
        listener.onStart();
        myRef.child(producto.get_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }


    public void save(Producto producto){
        System.out.println("HACE EL SAVE");
        myRef.child(producto.get_id()).setValue(producto);
    }

    public void update(Producto producto){

        try {
            producto.setFechaModificacion(new Date());
            producto.setUsuarioModificacion(USUARIO_ACTUAL.getParametrosUsuarioActual());
            myRef.child(producto.get_id()).setValue(producto);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void delete(Producto producto){
        myRef.child(producto.get_id()).removeValue();
    }

    public void queryEquals(String campoFiltrar, String valorEqual, final OnGetDataListener listener){
        listener.onStart();
        Query query = myRef.orderByChild(campoFiltrar).equalTo(valorEqual);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }
}
