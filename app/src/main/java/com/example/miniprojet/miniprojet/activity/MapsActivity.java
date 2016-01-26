package com.example.miniprojet.miniprojet.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniprojet.miniprojet.activity.PermissionGps;
import com.example.miniprojet.miniprojet.api.amazon.ManageImages;
import android.widget.ListView;
import android.widget.Toast;

import com.example.miniprojet.miniprojet.R;
import com.example.miniprojet.miniprojet.api.klicws.InterestAPI;
import com.example.miniprojet.miniprojet.api.klicws.UserAPI;
import com.example.miniprojet.miniprojet.api.klicws.dto.InterestDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.UserDto;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.example.miniprojet.miniprojet.geolocalisation.MainLocationListener;
import com.example.miniprojet.miniprojet.rendu.CustomMarker;
import com.example.miniprojet.miniprojet.rendu.CustomMarkernRendered;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
//import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.ClusterManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

//import com.google.maps.android.clustering.ClusterManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    LocationManager locationManager;
    Location location;
    MainLocationListener locationListener;
    private ImageButton bouton;
    private Uri imageUri;
    private InterestAPI interestAPI;
    List<InterestDto> interests;
    // Declare a variable for the cluster manager.
    //private ClusterManager<CustomMarker> mClusterManager;
    private ImageView image;
    private File photo;
    // Declare a variable for the cluster manager.
    UserDto connectedUser;
    private ListView liste;
    private List<TagDto> tagList;
    private ClusterManager<CustomMarker> clusterManager;
    private CustomMarkernRendered customMarkernRendered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.interestAPI = InterestAPI.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        this.connectedUser = (UserDto) getIntent().getSerializableExtra("connectedUser");

        TextView userNameBar = (TextView) findViewById(R.id.userNameBar);
        userNameBar.setText("user: "+connectedUser.getEmail());
        this.bouton = (ImageButton) findViewById(R.id.imageButton);
        this.bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Création d'un intent
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                //Intent intent2 = new Intent(this, "android.media.action.IMAGE_CAPTURE");

                //Création du fichier image
                if(Environment.isExternalStorageEmulated()){
                    photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
                } else {
                    photo = new File("/mnt/emmc/",  "Pic.jpg");
                }

                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photo));
                imageUri = Uri.fromFile(photo);

                //On lance l'intent
                startActivityForResult(intent, 100);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        MapFragment mapFragment2 = (MapFragment) getFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment2.getMapAsync(this);
        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        /** Test si le gps est activé ou non */
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            /** on lance notre activity (qui est une dialog) */
            Intent localIntent = new Intent(this, PermissionGps.class);
            //localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(localIntent);
        }
        List<String> enabledProv = locationManager.getProviders(true);

        List<String> allProv = locationManager.getAllProviders();

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Log.d("CONNEXION", "CONNEXION");
        }


        this.connectedUser = (UserDto) getIntent().getSerializableExtra("connectedUser");
        this.tagList = (List<TagDto>) getIntent().getSerializableExtra("tagList");

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        this.map = googleMap;

        locationListener = new MainLocationListener(this.locationManager, this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);


        // Position the map.
//getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        clusterManager = new ClusterManager<CustomMarker>(this, this.map);
        customMarkernRendered = new CustomMarkernRendered(getApplicationContext(), getMap(), clusterManager);
        clusterManager.setRenderer(customMarkernRendered);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        this.map.setOnCameraChangeListener(clusterManager);
        this.map.setOnMarkerClickListener(clusterManager);
        this.map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                CustomMarker customMarker = customMarkernRendered.getItemMarkerHashmap().get(marker.getId());
                // Le marker de geoloc renvoie null
                if (customMarker != null) {
                    Intent intent = new Intent(MapsActivity.this, InterestDisplayerActivity.class);
                    intent.putExtra("interest", customMarker.getInterestDto());
                    intent.putExtra("connectedUser", connectedUser);
                    startActivity(intent);

                }
            }
        });


        this.initInterests(clusterManager);
    }

    private void initInterests(ClusterManager<CustomMarker> cM) {

        this.interests = this.interestAPI.getAll();
        for (InterestDto interest : interests) {
            if (interest.containsTagsName(this.tagList)) {
                Float lat = interest.getPositionX();
                Float lng = interest.getPositionY();
                String title = interest.getDescription();
                // mClusterManager.addItem(new CustomMarker(lat, lng, title));
                //this.map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(title));
                cM.addItem(new CustomMarker(lat, lng, title, interest));

            }

        }
    }

    public GoogleMap getMap() {
        return map;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //Si l'activité était une prise de photo
            case 100:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ImageView imageView = new ImageView(this.getApplicationContext());

                    // Transfert vers une nouvelle activité
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    this.image = (ImageView) findViewById(R.id.imageView);
                    try {
// GEOLOCALISER
                        //Affichage de l'infobulle
                        Toast.makeText(this, selectedImage.toString(),
                                Toast.LENGTH_LONG).show();


//                        startActivity(new Intent(this, PhotoEditorActivity.class)
//                                .setData(selectedImage));

                        Intent intent = new Intent(MapsActivity.this, PhotoEditorActivity.class);
                        intent.putExtra("imageUri", selectedImage);
                        intent.putExtra("connectedUser", connectedUser);
                        intent.putExtra("location", this.locationListener.getLocation());
                        intent.putExtra("latitude", this.locationListener.getLocation().getLatitude());
                        intent.putExtra("longitude", this.locationListener.getLocation().getLongitude());
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        /** Test si le gps est activé ou non */
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            /** on lance notre activity (qui est une dialog) */
            Intent localIntent = new Intent(this, PermissionGps.class);
            //localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(localIntent);
        }
    }
}
