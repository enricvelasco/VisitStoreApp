package com.visitapp.visitstoreapp.domain.asociaciones;

import com.visitapp.visitstoreapp.domain.tiendas.Tienda;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Asociacion {
    UUID _id = UUID.randomUUID();
    String nombre;
    String observaciones;
    List<Tienda> tiendas;
    Date fechaCreacion = new Date();
    Date fechaModificacion = new Date();

    public Asociacion() {
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

    public List<Tienda> getTiendas() {
        return tiendas;
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

    public void setTiendas(List<Tienda> tiendas) {
        this.tiendas = tiendas;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
