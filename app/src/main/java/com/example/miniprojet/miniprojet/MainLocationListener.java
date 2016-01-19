package com.example.miniprojet.miniprojet;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Temporaire on 18/01/2016.
 */
public class MainLocationListener implements LocationListener {

    private final LocationManager locationManager;
    private final MainMapActivity mainMapActivity;
    private String locatioManagerListened = "";

    public MainLocationListener(LocationManager locationManager, MainMapActivity mainMapActivity) {
        this.locationManager = locationManager;
        this.mainMapActivity = mainMapActivity;
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

        Toast.makeText(this.mainMapActivity, "Status changed : " + newStatus, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderEnabled(String provider) {
        if(provider.equals(LocationManager.GPS_PROVIDER) && this.locatioManagerListened.equals(LocationManager.GPS_PROVIDER)) {
            this.locatioManagerListened = LocationManager.GPS_PROVIDER;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
        }
        if(provider.equals(LocationManager.NETWORK_PROVIDER.toString()) && this.locatioManagerListened.equals(LocationManager.NETWORK_PROVIDER)) {
            this.locatioManagerListened = LocationManager.NETWORK_PROVIDER;
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);
        }
        this.mainMapActivity.getLocationTV().append("Provider " + this.locatioManagerListened + " enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        this.mainMapActivity.getLocationTV().append("Provider " + this.locatioManagerListened + " disabled");
        if(provider.equals(LocationManager.GPS_PROVIDER.toString()) && this.locatioManagerListened.equals(LocationManager.GPS_PROVIDER)) {
            this.locatioManagerListened = "";
            locationManager.removeUpdates(this);
        }
        if(provider.equals(LocationManager.NETWORK_PROVIDER.toString()) && this.locatioManagerListened.equals(LocationManager.NETWORK_PROVIDER)) {
            this.locatioManagerListened = "";
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.mainMapActivity.getLocationTV().append(this.locatioManagerListened + "Latitude " + location.getLatitude() + " et longitude " + location.getLongitude());
//        this.mainMapActivity.getLocationTV().setText(this.mainMapActivity.getLocationTV().getText() + " " + location.getLatitude() + " " + location.getLongitude());
                /*GeoPoint p = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
                mc.animateTo(p);
                mc.setCenter(p);*/
    }

    public void pause() {
        if(this.locatioManagerListened != "")
        {
            locationManager.removeUpdates(this);

        }
    }

    public void restart() {
        if(this.locatioManagerListened != "")
        {
            locationManager.requestLocationUpdates(this.locatioManagerListened, 5000, 10, this);

        }
    }
}
