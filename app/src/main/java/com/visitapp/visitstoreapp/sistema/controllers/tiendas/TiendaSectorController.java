package com.visitapp.visitstoreapp.sistema.controllers.tiendas;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visitapp.visitstoreapp.sistema.domain.tiendas.TiendaSector;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.util.Date;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class TiendaSectorController {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("tiendasSectores");

    public TiendaSectorController() {
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

    public void read(TiendaSector tiendaSector, final OnGetDataListener listener){
        listener.onStart();
        myRef.child(tiendaSector.get_id()).addListenerForSingleValueEvent(new ValueEventListener() {
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


    public void save(TiendaSector tiendaSector){
        System.out.println("HACE EL SAVE");
        myRef.child(tiendaSector.get_id()).setValue(tiendaSector);
    }

    public void update(TiendaSector tiendaSector){

        try {
            tiendaSector.setFechaModificacion(new Date());
            tiendaSector.setUsuarioModificacion(USUARIO_ACTUAL.getParametrosUsuarioActual());
            myRef.child(tiendaSector.get_id()).setValue(tiendaSector);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void delete(TiendaSector tiendaSector){
        myRef.child(tiendaSector.get_id()).removeValue();
    }
}
