package com.visitapp.visitstoreapp.sistema.controllers.usuarios;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.util.Date;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class UsuarioParametrosController {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("usuariosParametros");

    public UsuarioParametrosController() {
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

    public void read(UsuarioParametros usuarioParametros, final OnGetDataListener listener){
        listener.onStart();
        myRef.child(usuarioParametros.get_id()).addListenerForSingleValueEvent(new ValueEventListener() {
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


    public void save(UsuarioParametros usuarioParametros){
        System.out.println("HACE EL SAVE");
        myRef.child(usuarioParametros.get_id()).setValue(usuarioParametros);
    }

    public void update(UsuarioParametros usuarioParametros){

        try {
            //usuarioParametros.setFechaModificacion(new Date());
            //usuarioParametros.setUsuarioModificacion(USUARIO_ACTUAL.getParametrosUsuarioActual());
            myRef.child(usuarioParametros.get_id()).setValue(usuarioParametros);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void delete(UsuarioParametros usuarioParametros){
        myRef.child(usuarioParametros.get_id()).removeValue();
    }
}
