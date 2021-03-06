package com.example.miniprojet.miniprojet.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.miniprojet.miniprojet.R;
import com.example.miniprojet.miniprojet.api.amazon.ManageImages;
import com.example.miniprojet.miniprojet.api.klicws.InterestAPI;
import com.example.miniprojet.miniprojet.api.klicws.dto.InterestDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.TypeLocation;
import com.example.miniprojet.miniprojet.api.klicws.dto.UserDto;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
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
 * Permet de créer un point d'intérêt
 * Created by Julien on 23/01/2016.
 */
public class InterestEditorActivity extends AppCompatActivity {


    private UserDto connectedUser;
    private Button finalizeButton;
    private Location bitmapLocation;
    private EditText tagListTextField;
    private EditText descriptionTextField;
    private Uri selectedImage;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interest_editor_activity);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setScaleType(ImageView.ScaleType.FIT_XY);

        this.connectedUser = (UserDto) getIntent().getSerializableExtra("connectedUser");

        selectedImage = (Uri) getIntent().getExtras().get("imageUri");

        title = (String) getIntent().getExtras().get("title");

        this.bitmapLocation = (Location) getIntent().getParcelableExtra("location");

        tagListTextField = (EditText) findViewById(R.id.tagListTextField);

        descriptionTextField = (EditText) findViewById(R.id.descriptionTextField);

        finalizeButton = (Button) findViewById(R.id.finalizeButton);


//        Toast.makeText(this, bitmapLocation.toString(),
//                Toast.LENGTH_LONG).show();

        try {
            Bitmap bitmap = MediaStore.Images.Media
                    .getBitmap(getContentResolver(), selectedImage);
            image.setImageBitmap(bitmap);
//            Toast.makeText(this, selectedImage.toString(),
//                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        finalizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] tagList = tagListTextField.getText().toString().trim().split(",");
                String description = descriptionTextField.getText().toString();

                if (description.isEmpty() || tagList.length == 0 || tagList[0].isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(InterestEditorActivity.this).create();
                    alertDialog.setTitle("Oups!");
                    alertDialog.setMessage("Il nous faut au moins un tag et une description!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    alertDialog.show();
                }else{

                    // get values
                    String tagsList = tagListTextField.getText().toString();
                    String descriptionInterest = descriptionTextField.getText().toString();
                    Double lat = bitmapLocation.getLatitude();
                    Double lon = bitmapLocation.getLongitude();

                // save image
                String tagsKeys = "";
                for (String tag : tagsList.split(",")) {
                    tag = tag.trim();
                }
                Integer rnfKey = 0 + (int)(Math.random() * ((1000 - 0) + 1));
                String imageKey = tagsKeys+rnfKey.toString()+lat.toString();

                    ManageImages manageImages = new ManageImages();
                    //Création du fichier image
                    File file;
                    if(Environment.isExternalStorageEmulated()){
                        file = new File(Environment.getExternalStorageDirectory(), title);
                    } else {
                        file = new File("/mnt/emmc/",  title);
                    }

                    manageImages.setPhoto(file);
                    manageImages.setKeyName(imageKey);
                    manageImages.execute("Upload");
                    Boolean error = true;
                    try {

                        manageImages.get();

                        // save interest
                        InterestAPI interestAPI = InterestAPI.getInstance();
                        InterestDto interestDto = new InterestDto();
                        interestDto.setDescription(descriptionInterest);
                        interestDto.setUserId(connectedUser.getId());
                        interestDto.setPositionX(lat.floatValue());
                        interestDto.setPositionY(lon.floatValue());
                        interestDto.setImage(imageKey);
                        interestDto = interestAPI.add(interestDto);

                    for (String tag : tagsList.split(",")){
                        TagDto tagDto = new TagDto();
                        tagDto.setNom(tag.trim());
                        tagDto.setType(TypeLocation.AREA);
                        interestAPI.addTag(interestDto.getId(),tagDto);
                    }

                        Intent intent = new Intent(InterestEditorActivity.this, ChooseTagActivity.class);
                        error = false;

                        intent.putExtra("connectedUser", connectedUser);
                        List<TagDto> listT = (List<TagDto>) getIntent().getSerializableExtra("tagList");
                        intent.putExtra("tagList", (Serializable) listT);
                        startActivity(intent);

                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    } catch (ExecutionException e) {
                        e.printStackTrace();

                    }

                    if (error){
                        AlertDialog alertDialog = new AlertDialog.Builder(InterestEditorActivity.this).create();
                        alertDialog.setTitle("Impossible d'uploader l'image!");
                        alertDialog.setMessage("ER_UPLOAD");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });
                        alertDialog.show();
                    }

                }


            }
        });

    }
}
