package com.visitapp.visitstoreapp.sistema.domain.tiendas;

import com.visitapp.visitstoreapp.sistema.domain.asociaciones.Asociacion;
import com.visitapp.visitstoreapp.sistema.domain.productos.Producto;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class Tienda{

    String codigo;
    String nif;
    String nombreFiscal;
    String nombreNombrePublico;
    String observaciones;
    String direccion;
    String logo;
    String asociacion_id;
    Map sectores;
    Map productosTipo;

    String _id = UUID.randomUUID().toString();
    Date fechaCreacion = new Date();
    Date fechaModificacion = new Date();
    UsuarioParametros usuarioCreacion = USUARIO_ACTUAL.getParametrosUsuarioActual();
    UsuarioParametros usuarioModificacion = USUARIO_ACTUAL.getParametrosUsuarioActual();

    public Tienda() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombreFiscal() {
        return nombreFiscal;
    }

    public void setNombreFiscal(String nombreFiscal) {
        this.nombreFiscal = nombreFiscal;
    }

    public String getNombreNombrePublico() {
        return nombreNombrePublico;
    }

    public void setNombreNombrePublico(String nombreNombrePublico) {
        this.nombreNombrePublico = nombreNombrePublico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAsociacion_id() {
        return asociacion_id;
    }

    public void setAsociacion_id(String asociacion_id) {
        this.asociacion_id = asociacion_id;
    }

    public Map getSectores() {
        return sectores;
    }

    public void setSectores(Map sectores) {
        this.sectores = sectores;
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

    public Map getProductosTipo() {
        return productosTipo;
    }

    public void setProductosTipo(Map productosTipo) {
        this.productosTipo = productosTipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
