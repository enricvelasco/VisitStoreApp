package com.visitapp.visitstoreapp.sistema.controllers.productos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visitapp.visitstoreapp.sistema.domain.productos.ProductoTipo;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.util.Date;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class ProductoTipoController {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("productosTipo");

    public ProductoTipoController() {
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

    public void read(ProductoTipo productoTipo, final OnGetDataListener listener){
        listener.onStart();
        myRef.child(productoTipo.get_id()).addListenerForSingleValueEvent(new ValueEventListener() {
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


    public void save(ProductoTipo productoTipo){
        System.out.println("HACE EL SAVE");
        myRef.child(productoTipo.get_id()).setValue(productoTipo);
    }

    public void update(ProductoTipo productoTipo){

        try {
            productoTipo.setFechaModificacion(new Date());
            productoTipo.setUsuarioModificacion(USUARIO_ACTUAL.getParametrosUsuarioActual());
            myRef.child(productoTipo.get_id()).setValue(productoTipo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void delete(ProductoTipo productoTipo){
        myRef.child(productoTipo.get_id()).removeValue();
    }
}
