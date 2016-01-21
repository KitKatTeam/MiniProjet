package com.example.miniprojet.miniprojet.api.klicws.util;

import android.util.Log;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by steve on 17/01/16.
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
