package com.example.miniprojet.miniprojet;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
/*import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
*/
import java.util.Set;

/**
 * Created by Tales of symphonia on 22/01/2016.
 */
public class CustomMarkernRendered extends DefaultClusterRenderer<CustomMarker> {
   public CustomMarkernRendered(Context applicationContext, GoogleMap map, ClusterManager<CustomMarker> mClusterManager) {
        super(applicationContext, map, mClusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(CustomMarker item, MarkerOptions markerOptions) {
        if(item.getIcon() != null)
        {
            markerOptions.icon(item.getIcon());

        }
        if(item.getTitle() != null)
        {
            markerOptions.title(item.getTitle());

        }
        super.onBeforeClusterItemRendered(item, markerOptions);
    }

}
