package com.example.miniprojet.miniprojet;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Temporaire on 18/01/2016.
 */
public class MainLocationListener implements LocationListener {

    private LocationManager locationManager;
    private final Activity mapActivity;
    private String locatioManagerListened = "";
    Location location;

    public MainLocationListener(LocationManager locationManager, Activity activity) {
        this.locationManager = locationManager;
        this.mapActivity = activity;
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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
        }
        if (provider.equals(LocationManager.NETWORK_PROVIDER.toString()) && this.locatioManagerListened.equals(LocationManager.NETWORK_PROVIDER)) {
            this.locatioManagerListened = LocationManager.NETWORK_PROVIDER;
            if (ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);
        }
        if (this.mapActivity instanceof MainMapActivity) {
            ((MainMapActivity) this.mapActivity).getLocationTV().append("Provider " + this.locatioManagerListened + " enabled");
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        if (this.mapActivity instanceof MainMapActivity) {
            ((MainMapActivity) this.mapActivity).getLocationTV().append("Provider " + this.locatioManagerListened + " disabled");
        }


        if (provider.equals(LocationManager.GPS_PROVIDER.toString()) && this.locatioManagerListened.equals(LocationManager.GPS_PROVIDER))

        {
            this.locatioManagerListened = "";
            locationManager.removeUpdates(this);
        }

        if (provider.equals(LocationManager.NETWORK_PROVIDER.toString()) && this.locatioManagerListened.equals(LocationManager.NETWORK_PROVIDER))

        {
            this.locatioManagerListened = "";
            if (ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(this);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        if (this.mapActivity instanceof MainMapActivity) {
            ((MainMapActivity) this.mapActivity).getLocationTV().append(this.locatioManagerListened + "Latitude " + location.getLatitude() + " et longitude " + location.getLongitude());
//        this.mainMapActivity.getLocationTV().setText(this.mainMapActivity.getLocationTV().getText() + " " + location.getLatitude() + " " + location.getLongitude());
                /*GeoPoint p = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
                mc.animateTo(p);
                mc.setCenter(p);*/
        }
        if (this.mapActivity instanceof MapsActivity) {
        this.location = location;
        ((MapsActivity)this.mapActivity).getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 13));
        ((MapsActivity)this.mapActivity).getMap().addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Itadakimasu !!!").icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            ((MapsActivity)this.mapActivity).getMap().addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Itadakimasu !!!").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
    }

    public void pause() {
        if (this.locatioManagerListened != "") {
            if (ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(this);

        }
    }

    public void restart() {
        if (this.locatioManagerListened != "") {
            if (ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.mapActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(this.locatioManagerListened, 5000, 10, this);

        }
    }

    public Location getLocation() {
        return location;
    }
}
