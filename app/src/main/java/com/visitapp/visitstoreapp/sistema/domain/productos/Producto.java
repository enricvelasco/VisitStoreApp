package com.visitapp.visitstoreapp.sistema.domain.productos;

import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class Producto{
    String nombre;
    String imagen;
    String codigo;
    String codigoBarras;
    String codigoQR;
    String descripcion;
    String productosTipo_id;
    String asociacion_id;
    String tienda_id;
    Map productosComposicion_productos;
    String precio;
    Boolean esPromocion;

    String _id = UUID.randomUUID().toString();
    Date fechaCreacion = new Date();
    Date fechaModificacion = new Date();
    UsuarioParametros usuarioCreacion = USUARIO_ACTUAL.getParametrosUsuarioActual();
    UsuarioParametros usuarioModificacion = USUARIO_ACTUAL.getParametrosUsuarioActual();

    public Producto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getProductosTipo_id() {
        return productosTipo_id;
    }

    public void setProductosTipo_id(String productosTipo_id) {
        this.productosTipo_id = productosTipo_id;
    }

    public Map getProductosComposicion_productos() {
        return productosComposicion_productos;
    }

    public void setProductosComposicion_productos(Map productosComposicion_productos) {
        this.productosComposicion_productos = productosComposicion_productos;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    /*public String getPrecio() {
        if(precio == null){
            precio = 0.0;
        }

        String strPrecio = Double.toString(precio)+"€";

        return strPrecio;
        //return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }*/

    public String getAsociacion_id() {
        return asociacion_id;
    }

    public void setAsociacion_id(String asociacion_id) {
        this.asociacion_id = asociacion_id;
    }

    public String getTienda_id() {
        return tienda_id;
    }

    public void setTienda_id(String tienda_id) {
        this.tienda_id = tienda_id;
    }

    public Boolean getEsPromocion() {
        return esPromocion;
    }

    public void setEsPromocion(Boolean esPromocion) {
        this.esPromocion = esPromocion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
