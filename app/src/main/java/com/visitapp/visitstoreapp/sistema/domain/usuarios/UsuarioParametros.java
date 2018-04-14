package com.visitapp.visitstoreapp.sistema.domain.usuarios;

public class UsuarioParametros extends Usuario{

    String nombre;
    int nivel_id;
    String acceso_asociacion_id;
    String acceso_tienda_id;

    public UsuarioParametros() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAcceso_asociacion_id() {
        return acceso_asociacion_id;
    }

    public void setAcceso_asociacion_id(String acceso_asociacion_id) {
        this.acceso_asociacion_id = acceso_asociacion_id;
    }

    public String getAcceso_tienda_id() {
        return acceso_tienda_id;
    }

    public void setAcceso_tienda_id(String acceso_tienda_id) {
        this.acceso_tienda_id = acceso_tienda_id;
    }

    public int getNivel_id() {
        return nivel_id;
    }

    public void setNivel_id(int nivel_id) {
        this.nivel_id = nivel_id;
    }
}
