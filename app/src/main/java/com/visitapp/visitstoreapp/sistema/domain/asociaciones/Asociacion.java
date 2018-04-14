package com.visitapp.visitstoreapp.sistema.domain.asociaciones;

import com.visitapp.visitstoreapp.VariablesGlobales;
import com.visitapp.visitstoreapp.sistema.domain.tiendas.Tienda;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.Usuario;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class Asociacion {
    String _id = UUID.randomUUID().toString();
    String nombre;
    String observaciones;
    String distrito_id;
    String direccion;
    String telefono;
    String logo;
    //Map tiendas;
    AsociacionParametrosConfig asociacionParametrosConfig;

    Date fechaCreacion = new Date();
    Date fechaModificacion = new Date();
    UsuarioParametros usuarioCreacion = USUARIO_ACTUAL.getParametrosUsuarioActual();
    UsuarioParametros usuarioModificacion = USUARIO_ACTUAL.getParametrosUsuarioActual();

    public Asociacion() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getDistrito_id() {
        return distrito_id;
    }

    public void setDistrito_id(String distrito_id) {
        this.distrito_id = distrito_id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public AsociacionParametrosConfig getAsociacionParametrosConfig() {
        return asociacionParametrosConfig;
    }

    public void setAsociacionParametrosConfig(AsociacionParametrosConfig asociacionParametrosConfig) {
        this.asociacionParametrosConfig = asociacionParametrosConfig;
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

    /*ublic Map getTiendas() {
        return tiendas;
    }

    public void setTiendas(Map tiendas) {
        this.tiendas = tiendas;
    }*/
}
