package com.visitapp.visitstoreapp.domain.tiendas;

import com.visitapp.visitstoreapp.domain.asociaciones.Asociacion;
import com.visitapp.visitstoreapp.domain.productos.Producto;

import java.util.List;

public class Tienda{
    String nombre;
    String observaciones;
    String ubicacion;
    String direccion;
    List<Producto> productos;

    public Tienda() {
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
