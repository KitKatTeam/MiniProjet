package com.example.miniprojet.miniprojet.restservice.util;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by steve on 17/01/16.
 */

public class RestService  {



    private URLActivity uRLActivity = new URLActivity();

    protected String source;

    public void setSource(String source){
        this.source = source;
    }


    public String getService(String url, HashMap<String,String> params)  {
        String output = "";

        String parmasS = "?";

        for (Map.Entry<String,String> p : params.entrySet() ) {
            parmasS += p.getKey() + "=" + p.getValue() + "&";
        }

        Log.d("RestService","load "+url+parmasS+"...");
        uRLActivity.setUrl(source+url+parmasS);
        uRLActivity.execute();

        try {
            output = uRLActivity.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return output;

    }


}
