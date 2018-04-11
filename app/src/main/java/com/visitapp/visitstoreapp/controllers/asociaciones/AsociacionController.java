package com.visitapp.visitstoreapp.controllers.asociaciones;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visitapp.visitstoreapp.domain.asociaciones.Asociacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsociacionController {
    Asociacion asociacion;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("asociaciones");

    public AsociacionController(Asociacion asociacion) {
        this.asociacion = asociacion;
    }

    public List<Asociacion> list(){
        final List<Asociacion> listadoRespuesta = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("DATOS RECIBIDOS DE ASOCIACIONES"+dataSnapshot.getValue());
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Asociacion get = postSnapshot.getValue(Asociacion.class);
                    listadoRespuesta.add(get);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return listadoRespuesta;
    }

    public Asociacion getById(String id){
        Asociacion asociacionResp = new Asociacion();


        return asociacionResp;
    }


    public void save(Asociacion asociacion){

    }

    public void update(Asociacion asociacion){

    }

    public void delete(Asociacion asociacion){
        
    }
}
