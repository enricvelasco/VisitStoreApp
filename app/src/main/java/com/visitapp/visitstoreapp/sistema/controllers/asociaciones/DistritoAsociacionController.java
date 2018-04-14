package com.visitapp.visitstoreapp.sistema.controllers.asociaciones;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visitapp.visitstoreapp.sistema.domain.asociaciones.DistritoAsociacion;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.util.Date;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class DistritoAsociacionController {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("distritosAsociaciones");

    public DistritoAsociacionController() {
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

    public void read(DistritoAsociacion distritoAsociacion, final OnGetDataListener listener){
        myRef.child(distritoAsociacion.get_id()).addListenerForSingleValueEvent(new ValueEventListener() {
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


    public void save(DistritoAsociacion distritoAsociacion){
        System.out.println("HACE EL SAVE");
        myRef.child(distritoAsociacion.get_id()).setValue(distritoAsociacion);
    }

    public void update(DistritoAsociacion distritoAsociacion){
        try {
            distritoAsociacion.setFechaModificacion(new Date());
            distritoAsociacion.setUsuarioModificacion(USUARIO_ACTUAL.getParametrosUsuarioActual());
            myRef.child(distritoAsociacion.get_id()).setValue(distritoAsociacion);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void delete(DistritoAsociacion distritoAsociacion){
        myRef.child(distritoAsociacion.get_id()).removeValue();
    }
}
