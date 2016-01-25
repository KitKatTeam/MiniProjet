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
 * Created by Tales of symphonia on 22/01/2016.
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
            markerOptions.title(item.getTitle() + " (Klicker ICI pour plus de détail");

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
