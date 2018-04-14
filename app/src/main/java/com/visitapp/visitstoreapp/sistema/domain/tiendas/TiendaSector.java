package com.visitapp.visitstoreapp.sistema.domain.tiendas;

import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;

import java.util.Date;
import java.util.UUID;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class TiendaSector {

    String nombre;
    String observaciones;


    String _id = UUID.randomUUID().toString();
    Date fechaCreacion = new Date();
    Date fechaModificacion = new Date();
    UsuarioParametros usuarioCreacion = USUARIO_ACTUAL.getParametrosUsuarioActual();
    UsuarioParametros usuarioModificacion = USUARIO_ACTUAL.getParametrosUsuarioActual();

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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public UsuarioParametros getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(UsuarioParametros usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public UsuarioParametros getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(UsuarioParametros usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }
}
