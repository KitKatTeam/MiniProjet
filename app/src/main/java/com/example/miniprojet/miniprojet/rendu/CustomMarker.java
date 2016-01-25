package com.example.miniprojet.miniprojet.rendu;

import com.example.miniprojet.miniprojet.api.klicws.dto.InterestDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;
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
    private final InterestDto interestDto;
    private BitmapDescriptor icon;

    public CustomMarker(Float lat, Float lng, String title, InterestDto interestDto) {
        this.position = new LatLng(lat, lng);
        this.title = title;
        this.interestDto = interestDto;

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

    public InterestDto getInterestDto() {
        return interestDto;
    }

}
