package com.example.miniprojet.miniprojet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.miniprojet.miniprojet.api.klicws.InterestAPI;
import com.example.miniprojet.miniprojet.api.klicws.dto.InterestDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.UserDto;
import com.example.miniprojet.miniprojet.db.pojo.User;
import com.example.miniprojet.miniprojet.api.klicws.UserAPI;

public class MainActivity extends AppCompatActivity {

    private Button connexionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText pseudoTextField = (EditText) findViewById(R.id.pseudoTextField);
        pseudoTextField.setText("test@gmail.com", TextView.BufferType.EDITABLE);
        EditText motDePasseTextField = (EditText) findViewById(R.id.motDePasseTextField);
        motDePasseTextField.setText("test", TextView.BufferType.EDITABLE);

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
                UserDto user = userAPI.login(userName,userPassword);

                // pass or not
                if (user != null){
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(intent);
                }
                else{
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Impossible de se connecter!");
                    alertDialog.setMessage("user: test@gmail.com, pass: test");
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
