package com.example.miniprojet.miniprojet;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniprojet.miniprojet.api.klicws.InterestAPI;
import com.example.miniprojet.miniprojet.api.klicws.dto.UserDto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by Tales of symphonia on 22/01/2016.
 */
public class PhotoEditorActivity extends AppCompatActivity {
    private ImageView image;
    private Bitmap mutableBitmap;
    private TextView textView;
    private UserDto connectedUser;
    Bitmap originalBitmap;
    private int heightScreen;
    private int widthScreen;
    private float coefMultWidth;
    private float coefMultHeight;
    private View.OnTouchListener onTouchListener;
    private Location bitmapLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoeditor_activity);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        widthScreen = size.x;
        heightScreen = size.y;
        int resource = getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resource > 0) {
            heightScreen -= getApplicationContext().getResources().getDimensionPixelSize(resource);
        }

        this.connectedUser = (UserDto) getIntent().getSerializableExtra("connectedUser");

        this.bitmapLocation = (Location) getIntent().getParcelableExtra("location");

        this.textView = (TextView) findViewById(R.id.textView);

        Intent myIntent = getIntent(); // gets the previously created intent

        this.image = (ImageView) findViewById(R.id.imageView);

        Uri selectedImage = (Uri) myIntent.getExtras().get("imageUri");

        try {
            ContentResolver cr = getContentResolver();

            originalBitmap = MediaStore.Images.Media
                    .getBitmap(cr, Uri.parse(selectedImage.toString()));

            mutableBitmap = originalBitmap.copy(originalBitmap.getConfig(), true);

            this.coefMultWidth = (float) mutableBitmap.getWidth() / (float) widthScreen;
            this.coefMultHeight = (float) mutableBitmap.getHeight() / (float) heightScreen;

            this.image.setImageBitmap(mutableBitmap);
            //Affichage de l'infobulle
            Toast.makeText(this, selectedImage.toString(),
                    Toast.LENGTH_LONG).show();

            // Envoi de la photo à l'éditeur

            onTouchListener = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    float effectiveXPosEvent = motionEvent.getX() * coefMultWidth;
                    float effectiveYPosEvent = motionEvent.getY() * coefMultHeight;
                    if (effectiveXPosEvent >= 0 && effectiveYPosEvent >= 0 && effectiveXPosEvent <= mutableBitmap.getWidth() && effectiveYPosEvent <= mutableBitmap.getHeight() && (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE)) {

//                        for (int i = 0; i < mutableBitmap.getWidth(); i++) {
//                            for (int j = 0; j < mutableBitmap.getHeight(); j++) {
                        setGroupPixel(mutableBitmap, effectiveXPosEvent , effectiveYPosEvent, Color.BLUE);

//                            }
//                        }
//                        textView.setText("(" + (motionEvent.getX() - 180) + "," + motionEvent.getY() + ")");
//                        Bitmap tmp = ((BitmapDrawable) image.getDrawable()).getBitmap();
//                        textView.append("\n AVANT SET" + mutableBitmap.getPixel((int) motionEvent.getX() - 180, (int)motionEvent.getY()));
//                        mutableBitmap.setPixel((int) motionEvent.getX() - 180, (int) motionEvent.getY(), Color.BLACK);
//                        textView.append("\n APRES SET" + mutableBitmap.getPixel((int) motionEvent.getX() - 180, (int)motionEvent.getY()));
//                        image.invalidate();
//                        image.setImageBitmap(null);

                        image.invalidate();
                    }

                    return true;
                }
            };
            this.image.setOnTouchListener(onTouchListener);




        } catch (Exception e) {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                    .show();
            Log.e("Camera", e.toString());
        }


        Button clearButton = (Button) findViewById(R.id.boutonAnnuler);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setImageBitmap(originalBitmap);
                mutableBitmap = originalBitmap.copy(originalBitmap.getConfig(), true);
                image.setOnTouchListener(onTouchListener);
            }
        });
        Button validerButton = (Button) findViewById(R.id.boutonValider);

        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String title = "Interest" + connectedUser.getId() + new Date().toString() + ".jpg";


                File bitmapPhoto = new File(Environment.getExternalStorageDirectory(), title);
                String uriImage = MediaStore.Images.Media.insertImage(getContentResolver(), mutableBitmap, title, null);


                OutputStream fOut = null;
                try {
                    fOut = new FileOutputStream(bitmapPhoto);
                    mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                    fOut.flush();
                    fOut.close();

                    String res = MediaStore.Images.Media.insertImage(getContentResolver(), bitmapPhoto.getAbsolutePath(), bitmapPhoto.getName(), bitmapPhoto.getName());
                    Uri newUriFile = Uri.parse(res);

                    Intent intent = new Intent(PhotoEditorActivity.this, InterestEditorActivity.class);
                    intent.putExtra("imageUri", newUriFile);
                    intent.putExtra("connectedUser", connectedUser);
                    intent.putExtra("location", bitmapLocation);
                    intent.putExtra("title", title);
                    startActivity(intent);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        });
        Toast.makeText(this, image.getWidth() + " VS " + mutableBitmap.getWidth() + " et " + image.getHeight() + " VS " + mutableBitmap.getHeight(), Toast.LENGTH_SHORT)
                .show();
        Log.e("TAILLEIMAGE", image.getWidth() + " VS " + mutableBitmap.getWidth() + " et " + image.getHeight() + " VS " + mutableBitmap.getHeight());
    }

    private void setGroupPixel(Bitmap mutableBitmap, float i, float j, int blue) {
        for (int x = (int) i - 5; x < i + 5; x++) {
            for (int y = (int) j - 5; y < j + 5; y++) {
                if (x >= 0 && y >= 0) {
                    mutableBitmap.setPixel(x, y, blue);
                    Log.d("PHOTOEDITORACTIVITY", "(" + i + "," + j + ")");

                }
            }
        }
    }

}
