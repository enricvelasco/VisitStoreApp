package com.visitapp.visitstoreapp.domain.productos;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ProductoTipo {
    UUID _id = UUID.randomUUID();
    String descripcion;
    String imagen;
    Date fechaCreacion = new Date();
    Date fechaModificacion = new Date();
    List<Producto> productos;

    public ProductoTipo() {
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

    public List<Producto> getProductos() {
        return productos;
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

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
