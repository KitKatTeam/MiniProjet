package com.example.miniprojet.miniprojet.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.miniprojet.miniprojet.MainActivity;
import com.example.miniprojet.miniprojet.R;
import com.example.miniprojet.miniprojet.api.klicws.UserAPI;
import com.example.miniprojet.miniprojet.api.klicws.dto.UserDto;

public class SubscriptionActivity extends AppCompatActivity {

    private Button sub_btsub;
    private Button sub_btcancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        this.sub_btsub = (Button) findViewById(R.id.sub_btsub);
        this.sub_btsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText sub_username = (EditText) findViewById(R.id.sub_username);
                EditText sub_email = (EditText) findViewById(R.id.sub_email);
                EditText sub_password = (EditText) findViewById(R.id.sub_password);

                UserAPI userAPI = UserAPI.getInstance();
                UserDto userDto = userAPI.subscribe(sub_username.getText().toString(),sub_email.getText().toString(),sub_password.getText().toString());

                if (userDto != null){

                    AlertDialog alertDialog = new AlertDialog.Builder(SubscriptionActivity.this).create();
                    alertDialog.setTitle("Bienvenue sur clic!");
                    alertDialog.setMessage("Votre compte a été créé.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    alertDialog.show();

                    Intent intent = new Intent(SubscriptionActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(SubscriptionActivity.this).create();
                    alertDialog.setTitle("Impossible d'ajouter un nouvel utilisateur!");
                    alertDialog.setMessage("Regarder l'erreur ER_API du manuel.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    alertDialog.show();
                }


            }
        });

        this.sub_btcancel = (Button) findViewById(R.id.sub_btcancel);
        this.sub_btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubscriptionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
