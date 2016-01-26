package com.example.miniprojet.miniprojet.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniprojet.miniprojet.R;
import com.example.miniprojet.miniprojet.api.amazon.ManageImages;
import com.example.miniprojet.miniprojet.api.klicws.dto.InterestDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.UserDto;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

/**
 * Created by Tales of symphonia on 25/01/2016.
 */
public class InterestDisplayerActivity extends AppCompatActivity {
    private UserDto connectedUser;
    private InterestDto interest;
    private ImageView bitmapConsultable;
    private TextView tagListConsultation;
    private TextView descritpionConsultation;
    private TextView dateInteret;
    private TextView dateAndName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interest_consultation_activity);

        this.interest = (InterestDto) getIntent().getSerializableExtra("interest");


        this.bitmapConsultable = (ImageView) findViewById(R.id.bitmapConsultable);
        Bitmap bitmap = null;
        if (this.interest.getImage() != null && !this.interest.getImage().equals("")){
            ManageImages manageImages = new ManageImages();

            manageImages.setKeyName(this.interest.getImage());
            manageImages.execute("Download");
            try {
                manageImages.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            bitmap = manageImages.getBitmap();
        }

        if (bitmap != null){
            bitmapConsultable.setImageBitmap(bitmap);
            bitmapConsultable.setScaleType(ImageView.ScaleType.FIT_XY);
        }


        this.tagListConsultation = (TextView) findViewById(R.id.tagListConsultation);
        this.descritpionConsultation = (TextView) findViewById(R.id.descriptionConsultation);
        this.dateInteret = (TextView) findViewById(R.id.dateInteret);
        this.dateAndName = (TextView) findViewById(R.id.nameAndDate);

        this.connectedUser = (UserDto) getIntent().getSerializableExtra("connectedUser");

        String tagListText = "";
        for (TagDto tag :
                this.interest.getTags()) {
            tagListText += "#" + tag.getNom() + ", ";
        }
        this.tagListConsultation.setText(tagListText);

        this.descritpionConsultation.setText(this.interest.getDescription());

        if (this.interest.getDate() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyyy" );
            String dateString = formatter.format(this.interest.getDate());
            this.dateAndName.setText("posté le " + dateString + " par " + connectedUser.getEmail());

        }

        Button buttonRetour = (Button)  findViewById(R.id.buttonRetour);
        buttonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InterestDisplayerActivity.this, MapsActivity.class);
                intent.putExtra("connectedUser", connectedUser);
                startActivity(intent);
            }
        });
    }
}
