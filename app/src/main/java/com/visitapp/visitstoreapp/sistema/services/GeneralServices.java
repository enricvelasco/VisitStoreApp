package com.visitapp.visitstoreapp.sistema.services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.List;

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
}
