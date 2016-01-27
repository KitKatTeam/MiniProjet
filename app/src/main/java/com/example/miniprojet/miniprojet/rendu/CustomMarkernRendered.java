package com.example.miniprojet.miniprojet.rendu;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
/*import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
*/
import java.util.HashMap;
import java.util.Map;

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

public class CustomMarkernRendered extends DefaultClusterRenderer<CustomMarker> {
    private Map<String, CustomMarker> ItemMarkerHashmap;

    public CustomMarkernRendered(Context applicationContext, GoogleMap map, ClusterManager<CustomMarker> mClusterManager) {
        super(applicationContext, map, mClusterManager);
        this.ItemMarkerHashmap = new HashMap<>();
    }

    @Override
    protected void onBeforeClusterItemRendered(CustomMarker item, MarkerOptions markerOptions) {
        if(item.getIcon() != null)
        {
            markerOptions.icon(item.getIcon());

        }
        if(item.getTitle() != null)
        {
            markerOptions.title(item.getTitle() + " (Klickez ICI pour plus de détail)");

        }
        super.onBeforeClusterItemRendered(item, markerOptions);
    }

    @Override
    protected void onClusterItemRendered(CustomMarker clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);
        ItemMarkerHashmap.put(marker.getId(), clusterItem);
    }

    public Map<String, CustomMarker> getItemMarkerHashmap() {
        return ItemMarkerHashmap;
    }
}
