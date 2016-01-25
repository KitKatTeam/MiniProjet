package com.example.miniprojet.miniprojet;

import android.content.Intent;
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

import com.example.miniprojet.miniprojet.api.klicws.InterestAPI;
import com.example.miniprojet.miniprojet.api.klicws.dto.InterestDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.TypeLocation;
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
        image.setScaleType(ImageView.ScaleType.FIT_XY);

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

                String tagsList = tagListTextField.getText().toString();

                String descriptionInterest = descriptionTextField.getText().toString();

                InterestAPI interestAPI = InterestAPI.getInstance();
                InterestDto interestDto = new InterestDto();
                interestDto.setDescription(descriptionInterest);
                Double lat = bitmapLocation.getLatitude();
                interestDto.setPositionX(lat.floatValue());
                Double lon = bitmapLocation.getLongitude();
                interestDto.setPositionY(lon.floatValue());
                interestDto = interestAPI.add(interestDto);

                for (String tag : tagsList.split(",")){
                    TagDto tagDto = new TagDto();
                    tagDto.setNom(tag);
                    tagDto.setType(TypeLocation.AREA);
                    interestAPI.addTag(interestDto.getId(),tagDto);
                }

                Intent intent = new Intent(InterestEditorActivity.this, MapsActivity.class);
                intent.putExtra("connectedUser", connectedUser);
                startActivity(intent);

            }
        });

    }
}
