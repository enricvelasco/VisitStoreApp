package com.visitapp.visitstoreapp.sistema.controllers.tiendas;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.visitapp.visitstoreapp.sistema.domain.tiendas.Tienda;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.util.Date;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class TiendaController {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("tiendas");

    public TiendaController() {
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

    public void read(String idTienda, final OnGetDataListener listener){
        listener.onStart();
        myRef.child(idTienda).addListenerForSingleValueEvent(new ValueEventListener() {
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


    public void save(Tienda tienda){
        System.out.println("HACE EL SAVE");
        myRef.child(tienda.get_id()).setValue(tienda);
    }

    public void update(Tienda tienda){

        try {
            tienda.setFechaModificacion(new Date());
            tienda.setUsuarioModificacion(USUARIO_ACTUAL.getParametrosUsuarioActual());
            myRef.child(tienda.get_id()).setValue(tienda);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Boolean delete(String idTienda){
        try {
            myRef.child(idTienda).removeValue();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
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
