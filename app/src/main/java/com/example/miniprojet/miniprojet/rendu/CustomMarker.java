package com.example.miniprojet.miniprojet.rendu;

import com.example.miniprojet.miniprojet.api.klicws.dto.InterestDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
//import com.google.maps.android.clustering.ClusterItem;

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
