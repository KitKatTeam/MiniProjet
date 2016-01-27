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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniprojet.miniprojet.R;
import com.example.miniprojet.miniprojet.api.klicws.InterestAPI;
import com.example.miniprojet.miniprojet.api.klicws.dto.InterestDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.UserDto;
import com.example.miniprojet.miniprojet.api.util.Coordinates;
import com.example.miniprojet.miniprojet.geolocalisation.MainLocationListener;
import com.example.miniprojet.miniprojet.rendu.CustomMarker;
import com.example.miniprojet.miniprojet.rendu.CustomMarkernRendered;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    LocationManager locationManager;
    Location location;
    MainLocationListener locationListener;
    private ImageButton bouton;
    private Uri imageUri;
    private InterestAPI interestAPI;
    List<InterestDto> interests;
    private ImageView image;
    private File photo;
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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        this.map = googleMap;

        locationListener = new MainLocationListener(this.locationManager, this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);


        clusterManager = new ClusterManager<CustomMarker>(this, this.map);
        customMarkernRendered = new CustomMarkernRendered(getApplicationContext(), getMap(), clusterManager);
        clusterManager.setRenderer(customMarkernRendered);

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

    private void initInterests(ClusterManager<CustomMarker> clusterManager) {
        List<String> tagsListString = new ArrayList<String>();
        for(TagDto tag : this.tagList){
            tagsListString.add(tag.getNom());
        }
        this.interests = this.interestAPI.findByTags(tagsListString);
        // Filtre des intérets : quand des intérêts sont situés exactement aux mêmes coordonnées, le clustering reste activé et les marker ne
        // sont pas séléctionnables. Don, on affiche qu'un seul intérêt par position géographique exacte
        List<Coordinates> coordinatesAlreadyUsed = new ArrayList<>();
        for (InterestDto interest : interests) {
            Coordinates coordinates = new Coordinates(interest.getPositionX(), interest.getPositionY());
            if(!coordinatesAlreadyUsed.contains(coordinates))
            {
                Float lat = interest.getPositionX();
                Float lng = interest.getPositionY();
                String title = interest.getDescription();
                clusterManager.addItem(new CustomMarker(lat, lng, title, interest));
                coordinatesAlreadyUsed.add(coordinates);
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
                        //Affichage de l'infobulle
                        Toast.makeText(this, selectedImage.toString(),
                                Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(MapsActivity.this, PhotoEditorActivity.class);
                        intent.putExtra("imageUri", selectedImage);
                        intent.putExtra("connectedUser", connectedUser);
                        intent.putExtra("location", this.locationListener.getLocation());
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
