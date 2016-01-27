package com.example.miniprojet.miniprojet.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miniprojet.miniprojet.R;
import com.example.miniprojet.miniprojet.api.amazon.ManageImages;
import com.example.miniprojet.miniprojet.api.klicws.dto.InterestDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.UserDto;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

/**
 * Copyright (C) 2016 Chaloupy Julien, Leger Loic, Magras Steeve

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along
 with this program; if not, write to the Free Software Foundation, Inc.,
 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

/**
 * Permet d'afficher le détail d'un intérêt
 * Created by Julien on 25/01/2016.
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

        if(this.interest.getDescription() != null)
        {
            this.descritpionConsultation.setText(this.interest.getDescription());
        }

        if (this.interest.getDate() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyyy" );
            String dateString = formatter.format(this.interest.getDate());
            this.dateAndName.setText("posté le " + dateString + " par " + connectedUser.getUsername());

        }

        Button buttonRetour = (Button)  findViewById(R.id.buttonRetour);
        buttonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InterestDisplayerActivity.this, ChooseTagActivity.class);
                intent.putExtra("connectedUser", connectedUser);
                startActivity(intent);
            }
        });
    }
}
