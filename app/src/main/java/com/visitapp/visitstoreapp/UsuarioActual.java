package com.visitapp.visitstoreapp;

import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;

public class UsuarioActual {
    UsuarioParametros parametrosUsuarioActual;

    public UsuarioActual(UsuarioParametros parametrosUsuarioActual) {
        this.parametrosUsuarioActual = parametrosUsuarioActual;
    }

    public UsuarioParametros getParametrosUsuarioActual() {
        return parametrosUsuarioActual;
    }

    public void setParametrosUsuarioActual(UsuarioParametros parametrosUsuarioActual) {
        this.parametrosUsuarioActual = parametrosUsuarioActual;
    }
}
