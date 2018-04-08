package com.visitapp.visitstoreapp.domain.usuarios;

import java.util.UUID;

public class UsuarioParametros extends Usuario{

    String nombre;
    String nivel_id;

    public UsuarioParametros() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNivel_id() {
        return nivel_id;
    }

    public void setNivel_id(String nivel_id) {
        this.nivel_id = nivel_id;
    }
}
