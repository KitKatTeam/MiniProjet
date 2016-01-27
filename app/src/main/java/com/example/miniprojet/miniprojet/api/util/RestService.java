package com.example.miniprojet.miniprojet.api.util;

import android.util.Log;

import org.apache.commons.collections.MultiHashMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

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


public class RestService  {



    protected String source;

    public void setSource(String source){
        this.source = source;
    }


    public String getService(String url, MultiHashMap params)  {
        String output = "";

        String pa = "?";

        Set set = params.entrySet();
        Iterator i = set.iterator();


        while (i.hasNext()) {
            Map.Entry<String, List<String>> me = (Map.Entry) i.next();

            for(int j = 0 ; j< me.getValue().size(); j++ )
            {
                pa = pa +me.getKey() + "=" + me.getValue().get(j) + "&";
            }

        }


        Log.d("RestService","url "+url +pa);

        URLActivity uRLActivity = new URLActivity();
        uRLActivity.setUrl(source+url);
        uRLActivity.setParams(params);
        uRLActivity.execute();

        try {
            output = uRLActivity.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        uRLActivity.cancel(true);

        return output;

    }


}
