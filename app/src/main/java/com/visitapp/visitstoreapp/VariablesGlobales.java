package com.visitapp.visitstoreapp;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;

public class VariablesGlobales extends Application{
    private UsuarioParametros usuarioParametros;
    private FirebaseAuth firebaseAuth;
    private String menuActual;

    public UsuarioParametros getUsuarioParametros() {
        return usuarioParametros;
    }

    public void setUsuarioParametros(UsuarioParametros usuarioParametros) {
        this.usuarioParametros = usuarioParametros;
    }


    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public String getMenuActual() {
        return menuActual;
    }

    public void setMenuActual(String menuActual) {
        this.menuActual = menuActual;
    }
}
