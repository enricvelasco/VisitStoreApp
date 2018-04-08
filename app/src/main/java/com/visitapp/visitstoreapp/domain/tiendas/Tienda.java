package com.visitapp.visitstoreapp.domain.tiendas;

import com.visitapp.visitstoreapp.domain.asociaciones.Asociacion;
import com.visitapp.visitstoreapp.domain.productos.Producto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Tienda{

    UUID _id = UUID.randomUUID();
    String nombre;
    String observaciones;
    String ubicacion;
    String direccion;
    List<Producto> productos;
    Date fechaCreacion = new Date();
    Date fechaModificacion = new Date();

    public Tienda() {
    }

    public UUID get_id() {
        return _id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
