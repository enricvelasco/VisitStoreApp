package com.visitapp.visitstoreapp.sistema.domain.genericos;

public class Direccion {
    private String calle;
    private String numCalle;
    private String postalCode;
    private String ciudad;
    private String pais;

    private double longtud;
    private double latitud;

    public Direccion() {
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumCalle() {
        return numCalle;
    }

    public void setNumCalle(String numCalle) {
        this.numCalle = numCalle;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public double getLongtud() {
        return longtud;
    }

    public void setLongtud(double longtud) {
        this.longtud = longtud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    @Override
    public String toString() {
        return calle + ", " + numCalle + ", " + postalCode + " " + ciudad + ", " +pais;
    }

    public String tituloMap(){
        return calle+", "+numCalle;
    }

    public String direccionFormulario(){
        return calle + ", " + numCalle + ", " + ciudad;
    }
    public String direccionItemPopUp(){
        return calle + ", " + numCalle;
    }
}
