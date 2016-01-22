package com.example.miniprojet.miniprojet;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;

/**
 * Created by Temporaire on 17/01/2016.
 */
public class MainMapActivity extends Activity implements OnMapReadyCallback {
    private LocationManager locationManager;
    private TextView locationTV;


    MainLocationListener locationListener = new MainLocationListener(this.locationManager, this);
    //private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity_main);

        this.locationTV = (TextView) findViewById(R.id.location);
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        List<String> enabledProv = locationManager.getProviders(true);
        List<String> allProv = locationManager.getAllProviders();

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Log.d("CONNEXION", "CONNEXION");
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
   // loc = locationManager.get

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);

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

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.locationListener.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.locationListener.restart();
    }

    public TextView getLocationTV() {
        return locationTV;
    }
}


