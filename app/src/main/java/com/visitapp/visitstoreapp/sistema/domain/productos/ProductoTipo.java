package com.visitapp.visitstoreapp.sistema.domain.productos;

import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class ProductoTipo {
    String codigo;
    String descripcion;
    String imagen;
    //String tienda_id;
    //Map productos;

    String _id = UUID.randomUUID().toString();
    Date fechaCreacion = new Date();
    Date fechaModificacion = new Date();
    UsuarioParametros usuarioCreacion = USUARIO_ACTUAL.getParametrosUsuarioActual();
    UsuarioParametros usuarioModificacion = USUARIO_ACTUAL.getParametrosUsuarioActual();

    public ProductoTipo() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /*public Map getProductos() {
        return productos;
    }

    public void setProductos(Map productos) {
        this.productos = productos;
    }

    public String getTienda_id() {
        return tienda_id;
    }

    public void setTienda_id(String tienda_id) {
        this.tienda_id = tienda_id;
    }*/
}
