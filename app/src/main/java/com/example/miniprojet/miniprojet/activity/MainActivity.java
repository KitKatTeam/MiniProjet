package com.example.miniprojet.miniprojet.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.miniprojet.miniprojet.R;
import com.example.miniprojet.miniprojet.api.klicws.UserAPI;
import com.example.miniprojet.miniprojet.api.klicws.dto.UserDto;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Julien on
 */

public class MainActivity extends AppCompatActivity {

    private Button connexionButton;
    private Button inscriptionButton;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText pseudoTextField = (EditText) findViewById(R.id.pseudoTextField);
        pseudoTextField.setText("test@gmail.com", TextView.BufferType.EDITABLE);
        EditText motDePasseTextField = (EditText) findViewById(R.id.motDePasseTextField);
        motDePasseTextField.setText("test", TextView.BufferType.EDITABLE);

        this.inscriptionButton = (Button) findViewById(R.id.inscriptionButton);
        this.inscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubscriptionActivity.class);
                startActivity(intent);
            }
        });

        this.connexionButton = (Button) findViewById(R.id.connexionButton);
        this.connexionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get fields values
                EditText pseudoTextField = (EditText) findViewById(R.id.pseudoTextField);
                EditText motDePasseTextField = (EditText) findViewById(R.id.motDePasseTextField);
                String userName = pseudoTextField.getText().toString();
                String userPassword = motDePasseTextField.getText().toString();

                // use service rest to check login
                UserAPI userAPI = UserAPI.getInstance();
                UserDto user = userAPI.login(userName, userPassword);

                // pass or not
                if (user != null && !userName.equals("")) {
                    Intent intent = new Intent(MainActivity.this, ChooseTagActivity.class);
                    intent.putExtra("connectedUser", user);
                    startActivity(intent);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Oups!");
                    alertDialog.setMessage("Impossible de se connecter!");
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
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.miniprojet.miniprojet/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.miniprojet.miniprojet/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
