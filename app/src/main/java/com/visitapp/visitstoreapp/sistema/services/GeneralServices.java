package com.visitapp.visitstoreapp.sistema.services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.visitapp.visitstoreapp.sistema.controllers.asociaciones.AsociacionController;
import com.visitapp.visitstoreapp.sistema.controllers.tiendas.TiendaController;
import com.visitapp.visitstoreapp.sistema.domain.asociaciones.Asociacion;
import com.visitapp.visitstoreapp.sistema.domain.tiendas.Tienda;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralServices {
    public Barcode.GeoPoint getLocationFromAddress(String strAddress, Context context){
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        Barcode.GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new Barcode.GeoPoint((double) (location.getLatitude()),
                    (double) (location.getLongitude()));

            return p1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Map> detectarAsociacionActualPosicion(LatLng posicionUsuario, List<Asociacion> listadoAsociaciones, List<Tienda> listadoTiendas){
        String idAsociacion = "";

        double distancia = 0;
        boolean estaDentro = false;

        //Map<String, Map> mapaContenido = new HashMap<>();
        List<Map> list = new ArrayList<>();

        Location locationUsuario = new Location("usuario");
        locationUsuario.setLatitude(posicionUsuario.latitude);
        locationUsuario.setLongitude(posicionUsuario.longitude);

        for(Asociacion asociacion :listadoAsociaciones){
            //calcularCentroAsociacion(listadoTiendas);
            List<Tienda> listadoCorrespondientes = new ArrayList<>();
            for (Tienda tienda : listadoTiendas){
                if(tienda.getAsociacion_id().equals(asociacion.get_id())){
                    listadoCorrespondientes.add(tienda);
                }
            }

            if(listadoCorrespondientes.size() > 0){
                Map<String, Double> mapDatos = calcularCentroAsociacion(asociacion, listadoCorrespondientes);

                Location locationCenterAsoc = new Location("asoc");
                locationCenterAsoc.setLatitude(mapDatos.get("centroX"));
                locationCenterAsoc.setLongitude(mapDatos.get("centroY"));

                Map<String, Boolean> mapIsIn = new HashMap<>();

                distancia = locationUsuario.distanceTo(locationCenterAsoc);

                System.out.println("DISTANCIA: "+ distancia);
                System.out.println("RADIO: "+mapDatos.get("maxDistance"));

                boolean isIn;
                if(distancia <= mapDatos.get("maxDistance")){
                    mapIsIn.put("isIn", true);
                    isIn = true;
                }else{
                    mapIsIn.put("isIn", false);
                    isIn = false;
                }

                Map<String, Asociacion> asociacionMap = new HashMap<>();
                asociacionMap.put("asociacion", asociacion);

                Map<String, Double> mapDistancia = new HashMap<>();
                mapDistancia.put("distancia", distancia);

                Map<String, String> carga = new HashMap<>();
                carga.put("asociacion", asociacion.get_id());
                carga.put("asociacionNombre", asociacion.getNombre());
                carga.put("asociacionImagen", asociacion.getLogo());
                carga.put("distancia", String.valueOf(distancia));
                carga.put("mapIsIn", String.valueOf(isIn));

                list.add(carga);
                //mapaContenido.put(asociacion.get_id(), carga);
            }

           /* Map<String, Double> mapDatos = calcularCentroAsociacion(asociacion, listadoCorrespondientes);

            Location locationCenterAsoc = new Location("asoc");
            locationCenterAsoc.setLatitude(mapDatos.get("centroX"));
            locationCenterAsoc.setLongitude(mapDatos.get("centroY"));

            Map<String, Boolean> mapIsIn = new HashMap<>();

            distancia = locationUsuario.distanceTo(locationCenterAsoc);
            if(distancia <= mapDatos.get("maxDistance")){
                mapIsIn.put("isIn", true);
            }else{
                mapIsIn.put("isIn", false);
            }

            Map<String, Asociacion> asociacionMap = new HashMap<>();
            asociacionMap.put("asociacion", asociacion);

            Map<String, Double> mapDistancia = new HashMap<>();
            mapDistancia.put("distancia", distancia);

            mapaContenido.put("asociacion", asociacionMap);
            mapaContenido.put("distancia", mapDistancia);
            mapaContenido.put("mapIsIn", mapIsIn);*/


        }

        return list;
    }

    private Map<String, Double> calcularCentroAsociacion(Asociacion asociacion, List<Tienda> listadoTiendas) {
        Map<String, Double> mapa = new HashMap<>();

        double x1 = listadoTiendas.get(0).getDireccion().getLatitud();//menor
        double x2 = listadoTiendas.get(0).getDireccion().getLatitud();//mayor

        double y1 = listadoTiendas.get(0).getDireccion().getLongtud();//menor
        double y2 = listadoTiendas.get(0).getDireccion().getLongtud();//mayor

        for(Tienda tienda : listadoTiendas){
            if(tienda.getDireccion().getLatitud() < x1){
                x1 = tienda.getDireccion().getLatitud();
            }else if(tienda.getDireccion().getLatitud() > x2){
                x2 = tienda.getDireccion().getLatitud();
            }

            if(tienda.getDireccion().getLongtud() < y1){
                y1 = tienda.getDireccion().getLongtud();
            }else if(tienda.getDireccion().getLongtud() > y2){
                y2 = tienda.getDireccion().getLongtud();
            }
        }

        double centroX = x1 + ((x2 - x1) / 2);
        double centroY = y1 + ((y2 - y1) / 2);

        double distanciaMax = encontrarDistanciaMaxima(centroX, centroY, listadoTiendas);

        mapa.put("maxDistance", distanciaMax);
        mapa.put("centroX", centroX);
        mapa.put("centroY", centroY);

        return mapa;
    }

    private Location crearLocation(Tienda tienda){
        Location locationA = new Location(tienda.getNombrePublico());
        locationA.setLatitude(tienda.getDireccion().getLatitud());
        locationA.setLongitude(tienda.getDireccion().getLongtud());
        return locationA;
    }

    private double encontrarDistanciaMaxima(double latitud, double longitud, List<Tienda> listadoTiendas) {
        double distancia = 0;

        Location centro = new Location("centro");
        centro.setLatitude(latitud);
        centro.setLongitude(longitud);

        if(listadoTiendas.size() > 1) {
            for (Tienda tienda : listadoTiendas) {
                Location location1 = crearLocation(tienda);
                if (centro.distanceTo(location1) > distancia) {
                    distancia = centro.distanceTo(location1);
                }
            }
        }
        return distancia;
    }
}
