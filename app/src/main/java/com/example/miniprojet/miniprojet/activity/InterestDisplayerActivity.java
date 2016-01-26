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
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniprojet.miniprojet.R;
import com.example.miniprojet.miniprojet.api.amazon.ManageImages;
import com.example.miniprojet.miniprojet.api.klicws.dto.InterestDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.UserDto;

import java.io.IOException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interest_consultation_activity);
        ImageView image = (ImageView) findViewById(R.id.imageView);


        this.bitmapConsultable = (ImageView) findViewById(R.id.bitmapConsultation);
        this.tagListConsultation = (TextView) findViewById(R.id.tagListConsultation);
        this.descritpionConsultation = (TextView) findViewById(R.id.descriptionConsultation);
        this.dateInteret = (TextView) findViewById(R.id.dateInteret);

        this.connectedUser = (UserDto) getIntent().getSerializableExtra("connectedUser");

        this.interest = (InterestDto) getIntent().getSerializableExtra("interest");

        ManageImages manageImages = new ManageImages();
        manageImages.setKeyName(this.interest.getImage());
        manageImages.execute("Download");
        try {
            manageImages.get();
            this.bitmapConsultable.setImageBitmap(manageImages.getBitmap());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        String tagListText = "";
        for (TagDto tag :
                this.interest.getTags()) {
            tagListText += "#" + tag.getNom() + " ";
        }
        this.tagListConsultation.setText(tagListText);

        this.descritpionConsultation.setText(this.interest.getDescription());

        if (this.interest.getDate() != null) {
            this.dateInteret.setText(this.interest.getDate().toString());

        }
    }
}
