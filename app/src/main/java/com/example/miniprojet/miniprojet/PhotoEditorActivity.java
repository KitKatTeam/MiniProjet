package com.example.miniprojet.miniprojet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniprojet.miniprojet.api.klicws.UserAPI;
import com.example.miniprojet.miniprojet.api.klicws.dto.UserDto;

import java.io.File;

/**
 * Created by Tales of symphonia on 22/01/2016.
 */
public class PhotoEditorActivity extends AppCompatActivity {
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoeditor_activity);

        Intent myIntent = getIntent(); // gets the previously created intent
        String firstKeyName = myIntent.getStringExtra("firstKeyName"); // will return "FirstKeyValue"
        String secondKeyName= myIntent.getStringExtra("secondKeyName"); // will return "SecondKeyValue"

        this.image = (ImageView) findViewById(R.id.imageView);

        Object selectedImage = myIntent.getExtras().get("imageUri");

        try {
            ContentResolver cr = getContentResolver();
            final Bitmap bitmap;
            bitmap = MediaStore.Images.Media
                    .getBitmap(cr, Uri.parse(selectedImage.toString()));

            this.image.setImageBitmap(bitmap);
            //Affichage de l'infobulle
            Toast.makeText(this, selectedImage.toString(),
                    Toast.LENGTH_LONG).show();

            // Envoi de la photo à l'éditeur

            this.image.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                        Bitmap tmp = ((BitmapDrawable) image.getDrawable()).getBitmap();
                        tmp.setPixel((int) motionEvent.getX(), (int) motionEvent.getY(), Color.BLACK);
                    }

                    return true;
                }
            });




        } catch (Exception e) {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                    .show();
            Log.e("Camera", e.toString());
        }
    }
}
