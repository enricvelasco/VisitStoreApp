package com.visitapp.visitstoreapp.domain.productos;

import com.visitapp.visitstoreapp.domain.tiendas.Tienda;

public class Producto{
    String descripcion;
    String imagen;

    public Producto() {
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
}
