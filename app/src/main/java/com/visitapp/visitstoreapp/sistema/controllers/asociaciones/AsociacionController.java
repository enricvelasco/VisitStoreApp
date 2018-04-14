package com.visitapp.visitstoreapp.sistema.controllers.asociaciones;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visitapp.visitstoreapp.sistema.domain.asociaciones.Asociacion;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class AsociacionController {
    //Asociacion asociacion;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("asociaciones");

    public AsociacionController() {
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

    public void read(Asociacion asociacion, final OnGetDataListener listener){
        listener.onStart();
        myRef.child(asociacion.get_id()).addListenerForSingleValueEvent(new ValueEventListener() {
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


    public void save(Asociacion asociacion){
        System.out.println("HACE EL SAVE");
        myRef.child(asociacion.get_id()).setValue(asociacion);
    }

    public void update(Asociacion asociacion){

        try {
            asociacion.setFechaModificacion(new Date());
            asociacion.setUsuarioModificacion(USUARIO_ACTUAL.getParametrosUsuarioActual());
            myRef.child(asociacion.get_id()).setValue(asociacion);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void delete(Asociacion asociacion){
        myRef.child(asociacion.get_id()).removeValue();
    }
}


