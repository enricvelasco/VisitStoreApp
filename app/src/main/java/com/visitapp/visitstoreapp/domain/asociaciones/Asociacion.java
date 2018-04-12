package com.visitapp.visitstoreapp.domain.asociaciones;

import com.visitapp.visitstoreapp.domain.tiendas.Tienda;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Asociacion {
    String _id = UUID.randomUUID().toString();
    String nombre;
    String observaciones;
    String logo;
    //List<Tienda> tiendas;
    AsociacionParametrosConfig asociacionParametrosConfig;

    Date fechaCreacion = new Date();
    Date fechaModificacion = new Date();

    public Asociacion() {
    }

    public String get_id() {
        return _id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    /*public List<Tienda> getTiendas() {
        return tiendas;
    }*/

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

    /*public void setTiendas(List<Tienda> tiendas) {
        this.tiendas = tiendas;
    }*/

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public AsociacionParametrosConfig getAsociacionParametrosConfig() {
        return asociacionParametrosConfig;
    }

    public void setAsociacionParametrosConfig(AsociacionParametrosConfig asociacionParametrosConfig) {
        this.asociacionParametrosConfig = asociacionParametrosConfig;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
