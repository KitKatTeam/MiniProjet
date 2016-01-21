package com.example.miniprojet.miniprojet.api.klicws.util;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by steve on 17/01/16.
 */

public class RestService  {



    protected String source;

    public void setSource(String source){
        this.source = source;
    }


    public String getService(String url, HashMap<String,String> params)  {
        String output = "";

        Log.d("RestService","url "+url+"...");
        for (Map.Entry<String,String> p : params.entrySet() ) {
            Log.d("RestService","param " +p.getKey() + "=" + p.getValue());
        }

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
