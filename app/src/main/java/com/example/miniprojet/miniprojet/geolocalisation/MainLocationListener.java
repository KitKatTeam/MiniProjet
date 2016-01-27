package com.example.miniprojet.miniprojet.geolocalisation;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.miniprojet.miniprojet.activity.MapsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Copyright (C) 2016 Chaloupy Julien, Leger Loic, Magras Steeve

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along
 with this program; if not, write to the Free Software Foundation, Inc.,
 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

public class MainLocationListener implements LocationListener {

    private LocationManager locationManager;
    private final Activity mapActivity;
    private String locatioManagerListened = "";
    Location location;
    private boolean positionUserDisplayed;

    public MainLocationListener(LocationManager locationManager, Activity activity) {
        this.locationManager = locationManager;
        this.mapActivity = activity;
        this.positionUserDisplayed = false;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        String newStatus = "";
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                newStatus = "OUT_OF_SERVICE";
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                newStatus = "TEMPORARILY_UNAVAILABLE";
                break;
            case LocationProvider.AVAILABLE:
                newStatus = "AVAILABLE";
                break;
        }

        Toast.makeText(this.mapActivity, "Status changed : " + newStatus, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderEnabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER) && this.locatioManagerListened.equals(LocationManager.GPS_PROVIDER)) {
            this.locatioManagerListened = LocationManager.GPS_PROVIDER;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 10, this);
        }
        if (provider.equals(LocationManager.NETWORK_PROVIDER.toString()) && this.locatioManagerListened.equals(LocationManager.NETWORK_PROVIDER)) {
            this.locatioManagerListened = LocationManager.NETWORK_PROVIDER;
            if (ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 10, this);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {


        if (provider.equals(LocationManager.GPS_PROVIDER.toString()) && this.locatioManagerListened.equals(LocationManager.GPS_PROVIDER))

        {
            this.locatioManagerListened = "";
            locationManager.removeUpdates(this);
        }

        if (provider.equals(LocationManager.NETWORK_PROVIDER.toString()) && this.locatioManagerListened.equals(LocationManager.NETWORK_PROVIDER))

        {
            this.locatioManagerListened = "";
            if (ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(this);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        if (this.mapActivity instanceof MapsActivity) {
        this.location = location;
        ((MapsActivity)this.mapActivity).getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 13));
            if(!this.positionUserDisplayed)
            {
//                // Ne pas afficher le marker de géolocalisation sinon il sera superposé au marker d'une photo qui sera prise au même endroit
//                MarkerOptions icon = new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Vous êtes ici").icon(BitmapDescriptorFactory
//                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//                ((MapsActivity)this.mapActivity).getMap().addMarker(icon);
                this.positionUserDisplayed = true;
            }
        }
        ((MapsActivity)this.mapActivity).setLocation(location);
    }

    public void pause() {
        if (this.locatioManagerListened != "") {
            if (ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(this);

        }
    }

    public void restart() {
        if (this.locatioManagerListened != "") {
            if (ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(this.locatioManagerListened, 5000, 10, this);

        }
    }

    public Location getLocation() {
        return location;
    }
}
