package com.example.miniprojet.miniprojet;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
//import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Tales of symphonia on 22/01/2016.
 */
public class CustomMarker implements ClusterItem {

    private final LatLng position;
    private final String title;
    private BitmapDescriptor icon;

    public CustomMarker(Float lat, Float lng, String title) {
        this.position = new LatLng(lat, lng);
        this.title = title;

    }

    /*@Override*/
    public LatLng getPosition() {
        return this.position;
    }

    public BitmapDescriptor getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }
}
