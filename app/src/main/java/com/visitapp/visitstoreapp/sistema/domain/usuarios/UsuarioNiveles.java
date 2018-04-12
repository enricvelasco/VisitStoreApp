package com.visitapp.visitstoreapp.sistema.domain.usuarios;

import java.util.List;
import java.util.UUID;

public class UsuarioNiveles {
    String _id = UUID.randomUUID().toString();
    String nombre;
    String observaciones;
    List<Usuario> usuarios;

    public UsuarioNiveles() {
    }

    public String get_id() {
        return _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
