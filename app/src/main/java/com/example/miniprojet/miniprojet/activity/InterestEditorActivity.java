package com.example.miniprojet.miniprojet.activity;

import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.miniprojet.miniprojet.R;
import com.example.miniprojet.miniprojet.api.klicws.InterestAPI;
import com.example.miniprojet.miniprojet.api.klicws.dto.InterestDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.UserDto;

import java.io.IOException;
import java.util.List;

/**
 * Created by Tales of symphonia on 23/01/2016.
 */
public class InterestEditorActivity extends AppCompatActivity {


    private UserDto connectedUser;
    private Button finalizeButton;
    private Location bitmapLocation;
    private EditText tagListTextField;
    private EditText descriptionTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interest_editor_activity);
        ImageView image = (ImageView) findViewById(R.id.imageView);

        this.connectedUser = (UserDto) getIntent().getSerializableExtra("connectedUser");

        Uri selectedImage = (Uri) getIntent().getExtras().get("imageUri");

        this.bitmapLocation = (Location) getIntent().getParcelableExtra("location");

        tagListTextField = (EditText) findViewById(R.id.tagListTextField);

        descriptionTextField = (EditText) findViewById(R.id.descriptionTextField);

        finalizeButton = (Button) findViewById(R.id.finalizeButton);


        Toast.makeText(this, bitmapLocation.toString(),
                Toast.LENGTH_LONG).show();

        try {
            Bitmap bitmap = MediaStore.Images.Media
                    .getBitmap(getContentResolver(), selectedImage);
            image.setImageBitmap(bitmap);
            Toast.makeText(this, selectedImage.toString(),
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        finalizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] tagList = tagListTextField.getText().toString().trim().split(",");
                String description = descriptionTextField.getText().toString();
                // Ajouter un signe (#, ...) à chaque tag

                // TODO : créer les tag et tout
                // TODO : rediriger sur la MapsActivity en précisant :
                // intent.putExtra("connectedUser", user);
                // TODO : recharger tous les tags, descirptions ? depuis le server
                // TODO : prévoir un champs en bdd pour le chemin de l'image sur le telephone si le download de Loic marche pas
            }
        });
//        new InterestDto()
//        InterestAPI interestAPI = InterestAPI.getInstance();
//        interestAPI.
    }
}
