package com.example.miniprojet.miniprojet.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.miniprojet.miniprojet.R;
import com.example.miniprojet.miniprojet.api.klicws.InterestAPI;
import com.example.miniprojet.miniprojet.api.klicws.dto.InterestDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.UserDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette activité permet de filtrer les tag qui seront à afficher sur la carte de l'activité suivante
 *  * Created by Julien on 25/01/2016.
 */
public class ChooseTagActivity extends AppCompatActivity {

    UserDto connectedUser;
    private ListView liste;
    private Button chooseTagButton;
    private ArrayAdapter<TagDto> adapter;
    List<TagDto> tagList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_tag_activity_layout);

        this.connectedUser = (UserDto) getIntent().getSerializableExtra("connectedUser");

        TextView bienvenuTV = (TextView) findViewById(R.id.bienvenuTextView);
        bienvenuTV.setTypeface(null, Typeface.BOLD);

        liste = (ListView) findViewById(R.id.listView);
        chooseTagButton = (Button) findViewById(R.id.choose_tag_button);

        for (InterestDto interestDto:
                InterestAPI.getInstance().getAll()) {
            for (TagDto tag:
                    interestDto.getTags()) {
                if(!tagList.contains(tag))
                {
                    tagList.add(tag);

                }
            }
        }

        adapter = new ArrayAdapter<TagDto>(this, android.R.layout.simple_list_item_multiple_choice, tagList);

        liste.setChoiceMode(liste.CHOICE_MODE_MULTIPLE);
        liste.setAdapter(adapter);
        liste.setItemsCanFocus(true);
        for (int i = 0; i < tagList.size(); i++) {
            liste.setItemChecked(i, true);
        }

        chooseTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // On récupère
                SparseBooleanArray choice = liste.getCheckedItemPositions();
                List<TagDto> choosenTags = new ArrayList<TagDto>();
                for(int i = 0; i < liste.getCount(); i++)
                {

                    if(choice.get(i) == true)
                    {
                        choosenTags.add((TagDto) liste.getItemAtPosition(i));
                    }

                }
                Intent intent = new Intent(ChooseTagActivity.this, MapsActivity.class);
                intent.putExtra("connectedUser", connectedUser);
                intent.putExtra("tagList", (Serializable) choosenTags);
                startActivity(intent);
            }
        });

    }
}
