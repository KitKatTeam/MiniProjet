package com.example.miniprojet.miniprojet.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.miniprojet.miniprojet.MainActivity;
import com.example.miniprojet.miniprojet.R;
import com.example.miniprojet.miniprojet.api.klicws.KlicAPI;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        int secondsDelayed = 5;
        new Handler().postDelayed(new Runnable() {
            public void run() {

                KlicAPI klicAPI = KlicAPI.getInstance();

                Integer tryCount = 0;
                Boolean isConnected = false;

                TextView loadingText = (TextView) findViewById(R.id.loadingText);
                String state = "";
                while (!isConnected && tryCount <= 8){
                    isConnected = klicAPI.init();
                    tryCount++;
                    if (tryCount % 3 == 1){
                        state = ".";
                    }
                    if (tryCount % 3 == 2){
                        state = "..";
                    }
                    if (tryCount % 3 == 0){
                        state = "...";
                    }
                    loadingText.setText("Connexion à l'API"+state);
                }

                if (!isConnected){
                    AlertDialog alertDialog = new AlertDialog.Builder(LoadActivity.this).create();
                    alertDialog.setTitle("Impossible de joindre l'API");
                    alertDialog.setMessage("Regarder l'erreur ER_API du manuel.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                    alertDialog.show();
                }else{
                    startActivity(new Intent(LoadActivity.this, MainActivity.class));
                    finish();

                }




            }
        }, secondsDelayed * 1000);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



}
