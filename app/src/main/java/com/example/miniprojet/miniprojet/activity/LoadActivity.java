package com.example.miniprojet.miniprojet.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.miniprojet.miniprojet.R;
import com.example.miniprojet.miniprojet.api.klicws.KlicAPI;

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
                    alertDialog.setMessage("Regarder l'erreur ER_API du manuel ou relancez l'application dans quelques instants.");
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
