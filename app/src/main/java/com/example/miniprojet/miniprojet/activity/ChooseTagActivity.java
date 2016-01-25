package com.example.miniprojet.miniprojet.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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
//import com.google.maps.android.clustering.ClusterManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChooseTagActivity extends FragmentActivity {

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
// TODO à supprimer après
        this.connectedUser.setUsername("Michel");
        TextView bienvenuTV = (TextView) findViewById(R.id.bienvenuTextView);
        bienvenuTV.append(this.connectedUser.getUsername());
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
//        adapter = new CustomAdapter(getApplicationContext(), tagList);

        liste.setChoiceMode(liste.CHOICE_MODE_MULTIPLE);
        liste.setAdapter(adapter);
        liste.setItemsCanFocus(true);
        for (int i = 0; i < tagList.size(); i++) {
            liste.setItemChecked(i, true);
        }
        liste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                liste.setItemChecked(i, !liste.isItemChecked(i));
            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        chooseTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
