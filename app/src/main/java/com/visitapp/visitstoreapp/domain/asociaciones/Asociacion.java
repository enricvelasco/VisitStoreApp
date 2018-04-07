package com.visitapp.visitstoreapp.domain.asociaciones;

import com.visitapp.visitstoreapp.domain.tiendas.Tienda;

import java.util.List;

public class Asociacion {
    String nombre;
    String observaciones;
    List<Tienda> tiendas;

    public Asociacion() {
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

    public List<Tienda> getTiendas() {
        return tiendas;
    }

    public void setTiendas(List<Tienda> tiendas) {
        this.tiendas = tiendas;
    }
}
