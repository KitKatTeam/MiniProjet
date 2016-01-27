package com.example.miniprojet.miniprojet.rendu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.miniprojet.miniprojet.R;
import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;

import java.util.List;

/**
 * Permet d'afficher la liste de tag sur l'activité ChooseTagActivity
 * Created by Julien on 24/01/2016.
 */
public class CustomAdapter extends ArrayAdapter<TagDto> {
    List<TagDto> tags = null;
    Context context;

    public CustomAdapter(Context context, List<TagDto> tags) {
        super(context, R.layout.listview_chooseactivity_row, tags);
        this.context = context;
        this.tags = tags;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.listview_chooseactivity_row, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.textViewRow);
        name.setTypeface(null, Typeface.BOLD);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBoxRow);
        name.setText(tags.get(position).getNom());
        if (tags.get(position).getId() == 1)
            cb.setChecked(true);
        else
            cb.setChecked(false);
        return convertView;
    }
}

