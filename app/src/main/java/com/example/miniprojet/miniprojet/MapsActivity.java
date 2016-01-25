package com.example.miniprojet.miniprojet;

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
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.miniprojet.miniprojet.api.amazon.ManageImages;
import com.example.miniprojet.miniprojet.api.klicws.InterestAPI;
import com.example.miniprojet.miniprojet.api.klicws.dto.InterestDto;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.ClusterManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.interestAPI = InterestAPI.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        this.bouton = (ImageButton) findViewById(R.id.imageButton);
        this.bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Création d'un intent
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                //Intent intent2 = new Intent(this, "android.media.action.IMAGE_CAPTURE");

                //Création du fichier image
                photo = new File("/mnt/emmc/",  "Pic.jpg");
                // On fait le lien entre la photo prise et le fichier que l'on vient de créer
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));

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
        List<String> enabledProv = locationManager.getProviders(true);
        List<String> allProv = locationManager.getAllProviders();

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Log.d("CONNEXION", "CONNEXION");
        }



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
         ClusterManager<CustomMarker> cM;
        cM = new ClusterManager<CustomMarker>(this, this.map);
        cM.setRenderer(new CustomMarkernRendered(getApplicationContext(), getMap(), cM));

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        this.map.setOnCameraChangeListener(cM);
        this.map.setOnMarkerClickListener(cM);




        this.initInterests(cM);
    }

    private void initInterests(ClusterManager<CustomMarker> cM) {

        this.interests = this.interestAPI.getAll();
        for (InterestDto interest : interests) {
            Float lat = interest.getPositionX();
            Float lng = interest.getPositionY();
            String title = interest.getDescription();
           // mClusterManager.addItem(new CustomMarker(lat, lng, title));
            //this.map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(title));
            cM.addItem(new CustomMarker(lat, lng, title));

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
                        bitmap = MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);
                        this.image.setImageBitmap(bitmap);
                        ManageImages manageImages = new ManageImages();
                        manageImages.setContext(getApplicationContext());
                        manageImages.setPhoto(photo);
                        manageImages.execute();
                        //Affichage de l'infobulle
                        Toast.makeText(this, selectedImage.toString(),
                                Toast.LENGTH_LONG).show();


//                        startActivity(new Intent(this, PhotoEditorActivity.class)
//                                .setData(selectedImage));

                        Intent intent = new Intent(MapsActivity.this, PhotoEditorActivity.class);
                        intent.putExtra("imageUri", selectedImage);
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
        }
    }
}
