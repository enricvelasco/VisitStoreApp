package com.visitapp.visitstoreapp.domain.productos;

import com.visitapp.visitstoreapp.domain.asociaciones.Asociacion;
import com.visitapp.visitstoreapp.domain.tiendas.Tienda;

public class ProductoVisita extends Producto {
    Asociacion asociacion;
    Tienda tienda;

    public Asociacion getAsociacion() {
        return asociacion;
    }

    public void setAsociacion(Asociacion asociacion) {
        this.asociacion = asociacion;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }
}
