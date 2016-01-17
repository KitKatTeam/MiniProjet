package com.example.miniprojet.miniprojet;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Temporaire on 17/01/2016.
 */
public class MainMapActivity extends ActionBarActivity {

    //private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity_main);
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);



        /*this.mapView = (MapView) this.findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);

        mc = mapView.getController();
        mc.setZoom(17);*/


        List<LocationProvider> providers = new ArrayList<LocationProvider>();
        List<String> names = locationManager.getProviders(true);
        for(String name : names) {
            providers.add(locationManager.getProvider(name));
        }
        Criteria critere = new Criteria();
// Pour indiquer la précision voulue
// On peut mettre ACCURACY_FINE pour une haute précision ou ACCURACY_COARSE pour une moins bonne précision
        critere.setAccuracy(Criteria.NO_REQUIREMENT);
// Est-ce que le fournisseur doit être capable de donner une altitude ?
        critere.setAltitudeRequired(false);
// Est-ce que le fournisseur doit être capable de donner une direction ?
        critere.setBearingRequired(false);
// Est-ce que le fournisseur peut être payant ?
        critere.setCostAllowed(false);
// Pour indiquer la consommation d'énergie demandée
// Criteria.POWER_HIGH pour une haute consommation, Criteria.POWER_MEDIUM pour une consommation moyenne et Criteria.POWER_LOW pour une basse consommation
        critere.setPowerRequirement(Criteria.NO_REQUIREMENT);
// Est-ce que le fournisseur doit être capable de donner une vitesse ?
        critere.setSpeedRequired(false);

        String bestProvider = locationManager.getBestProvider(critere, true);

        Location lastLocation = locationManager.getLastKnownLocation(bestProvider);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 150, new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
            @Override
            public void onLocationChanged(Location location) {
                Log.d("GPS", "Latitude " + location.getLatitude() + " et longitude " + location.getLongitude());
                TextView locationTV = (TextView) findViewById(R.id.location);
                locationTV.setText(location.toString());
                /*GeoPoint p = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
                mc.animateTo(p);
                mc.setCenter(p);*/
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
