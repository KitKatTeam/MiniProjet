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

