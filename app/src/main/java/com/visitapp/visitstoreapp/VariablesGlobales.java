package com.visitapp.visitstoreapp;

import android.app.Application;

import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;

public class VariablesGlobales extends Application{
    private UsuarioParametros usuarioParametros;

    public UsuarioParametros getUsuarioParametros() {
        return usuarioParametros;
    }

    public void setUsuarioParametros(UsuarioParametros usuarioParametros) {
        this.usuarioParametros = usuarioParametros;
    }


}
