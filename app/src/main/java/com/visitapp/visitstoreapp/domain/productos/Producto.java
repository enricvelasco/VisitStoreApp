package com.visitapp.visitstoreapp.domain.productos;

import com.google.firebase.auth.FirebaseUser;
import com.visitapp.visitstoreapp.domain.tiendas.Tienda;

import java.util.Date;
import java.util.UUID;

public class Producto{
    UUID _id = UUID.randomUUID();
    String descripcion;
    String imagen;
    Date fechaCreacion = new Date();
    Date fechaModificacion = new Date();

    public Producto() {
    }

    public UUID get_id() {
        return _id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
